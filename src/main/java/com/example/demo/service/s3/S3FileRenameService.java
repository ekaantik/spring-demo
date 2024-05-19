package com.example.demo.service.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class S3FileRenameService {

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3FileRenameService(@Value("${app.s3.bucket-name}") String bucketName,
                               @Value("${app.credentials.accessKey}") String accessKey,
                               @Value("${app.credentials.secretKey}") String secretKey,
                               @Value("${app.region.static}") String region) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        this.bucketName = bucketName;
    }

    public void renameFiles() {
        System.out.println("Rename file service function got called");

        List<String> paths = getListOfs3Object();

        Map<String, List<String>> folderToFileMap = groupFilesByFolder(paths);

        // Rename the files in each folder
        for (Map.Entry<String, List<String>> entry : folderToFileMap.entrySet()) {
            String folderPath = entry.getKey();
            List<String> filePaths = entry.getValue();

            System.out.println("Folder: " + folderPath);
            System.out.println("Renamed file paths:");


            int partIndex = 1;
            for (String filePath : filePaths) {
//                System.out.println("this is the file path"+filePath);
                String renamedFilePath = renameFilePath(filePath, folderPath, partIndex++);

                System.out.println("\t" + renamedFilePath);
                CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, filePath, bucketName, renamedFilePath);

                s3Client.copyObject(copyObjectRequest);
                System.out.println("\t" + "object " + filePath + " was renamed to " + renamedFilePath + " successfully");
                // Delete the original object
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, filePath);
                System.out.println("\t" + filePath + " deleted success fully");
                s3Client.deleteObject(deleteObjectRequest);
            }

            System.out.println();

        }

    }

    private List<String> getListOfs3Object(){
        List<String> listOfObjects = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix("output/");

        ObjectListing objectListing;
        do {
            objectListing = s3Client.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                String objectKey = objectSummary.getKey();
                listOfObjects.add(objectKey);
//                System.out.println(objectKey);
                // Perform renaming logic here
                // ...
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());


        return listOfObjects;
    }

    public static Map<String, List<String>> groupFilesByFolder(List<String> paths) {
        Map<String, List<String>> folderToFileMap = new HashMap<>();
        List<String> filePaths = new ArrayList<>();

        // 1. Filter out folder paths
        for (String path : paths) {
            if (isFilePath(path)) {
                filePaths.add(path);
            }
        }

        // 2. Create a Set of unique folder paths
        Set<String> folderPaths = new HashSet<>();
        for (String filePath : filePaths) {
            String folderPath = extractFolderPath(filePath);
            folderPaths.add(folderPath);
        }

        // 3. Create a Map with folder paths as keys and lists of file paths as values
        for (String folderPath : folderPaths) {
            folderToFileMap.put(folderPath, new ArrayList<>());
        }

        for (String filePath : filePaths) {
            String folderPath = extractFolderPath(filePath);
            folderToFileMap.get(folderPath).add(filePath);
        }

        return folderToFileMap;
    }

    private static boolean isFilePath(String path) {
        // Check if the path ends with a file extension (e.g., .gz, .csv)
        return path.endsWith(".gz") || path.endsWith(".csv");
    }

    private static String extractFolderPath(String filePath) {
        // Extract the folder path from the file path
        int lastSlashIndex = filePath.lastIndexOf("/");
        return filePath.substring(0, lastSlashIndex + 1);
    }

    private static String renameFilePath(String filePath, String folderPath, int partIndex) {
        String prefix;
        String suffix = filePath.substring(filePath.lastIndexOf("."));

        String[] components =  filePath.split("/");

        return folderPath + components[1] + "_" + components[2] + "_part_" + String.format("%02d", partIndex) + suffix;
    }
}