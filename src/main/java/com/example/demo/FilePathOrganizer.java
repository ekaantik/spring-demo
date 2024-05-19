package com.example.demo;

import java.util.*;

public class FilePathOrganizer {
    public static void main(String[] args) {
        // Sample input list of paths
        List<String> paths = Arrays.asList(
                "output/",
                "output/20241505/",
                "output/20241505/01/",
                "output/20241505/01/20241405_01_part_00000_00000.csv.gz",
                "output/20241505/01/20241405_01_part_00000_00001.csv.gz",
                "output/20241505/01/20241405_01_part_00000_00002.csv.gz",
                "output/20241505/01/20241405_01_part_00000_00003.csv.gz",
                "output/20241505/01/20241405_01_part_00000_00004.csv.gz",
                "output/20241505/02/",
                "output/20241505/02/20241405_02_part_00000_00000.csv.gz",
                "output/20241505/02/20241405_02_part_00000_00001.csv.gz",
                "output/20241505/02/20241405_02_part_00000_00002.csv.gz",
                "output/20241505/02/20241405_02_part_00000_00003.csv.gz",
                "output/20241505/03/",
                "output/20241505/03/20241405_03_part_00000_00000.csv.gz",
                "output/20241505/03/20241405_03_part_00000_00001.csv.gz",
                "output/20241505/03/20241405_03_part_00000_00002.csv.gz"
        );

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
            }


            System.out.println();
        }
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

//        prefix = ;
//        //        if (folderPath.isEmpty() || folderPath.equals(" ")) {
////            System.out.println("Empty folderPath.");
////            return folderPath;
////        }
////        if (folderPath.equals("output/")) {
////            System.out.println("path is output");
////            // Handle the case where the folderPath is "output/"
////            prefix = "output";
////        } else {
////            System.out.println("path is not output");
////            System.out.println("now getting substring of the path");
////            System.out.println("filePath = " + filePath);
////            System.out.println("prefix = " + filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("_")));
////
////
//            prefix = folderPath.substring(19, filePath.lastIndexOf("_", 36));
////
////        }

        return folderPath + components[1] + "_" + components[2] + "_part_" + String.format("%02d", partIndex) + suffix;
    }
}