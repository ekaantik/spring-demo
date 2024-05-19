package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.MediaType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    /**
     * Builds a directory path based on the provided parameters.
     *
     * @param basePath The base path where the directory will be created.
     * @param storeId  The unique identifier of the store.
     * @param category The category of the video.
     * @param fileName The name of the file.
     * @return The constructed directory path as a string.
     */
    public String buildDirectoryPath(String basePath, UUID storeId, String type, String category, String fileName) {
        Path path = Paths.get(basePath, storeId.toString(), type, category, fileName);

        return path.toString();

    }

    /**
     * Extracts the file name without the extension from the provided file name.
     * 
     * @param fileName The name of the file.
     * @return
     */
    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, dotIndex);
        }
    }

    /**
     * Extracts the file extension from the provided file name.
     * 
     * @param fileName The name of the file.
     * @return
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }

    /**
     * Adds a suffix to the provided file path.
     * 
     * @param filePath The path of the file.
     * @param count    The count to be added as a suffix.
     * @return
     */
    public static String addSuffixToFilePath(String filePath, int count) {
        String fileNameWithoutExtension = Utils.getFileNameWithoutExtension(filePath);
        String extension = Utils.getFileExtension(filePath);
        return fileNameWithoutExtension + "_" + count + extension;
    }

    public static void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Determines the media type based on the provided file path.
     * 
     * @param filePath The path of the file.
     * @return
     */
    public static MediaType determineMediaType(String filePath) {
        String fileExtension = getFileExtension(filePath);

        switch (fileExtension) {
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "mp4":
                return MediaType.valueOf("video/mp4");
            case "avi":
                return MediaType.valueOf("video/x-msvideo");
            case "mov":
                return MediaType.valueOf("video/quicktime");
            case "wmv":
                return MediaType.valueOf("video/x-ms-wmv");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
