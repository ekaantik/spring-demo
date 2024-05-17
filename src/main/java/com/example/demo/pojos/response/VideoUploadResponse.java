package com.example.demo.pojos.response;

import com.example.demo.constants.VideoCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VideoUploadResponse {
    private UUID storeId;
    private UUID videoId;
    private String path;
    private VideoCategories category;
    private String message;

}
