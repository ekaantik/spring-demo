package com.example.demo.pojos.response;

import com.example.demo.constants.ImageCategories;
import com.example.demo.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImageUploadResponse {

    private UUID storeId;
    private UUID imageId;
    private String path;
    private ImageCategories category;
    private String message;
}
