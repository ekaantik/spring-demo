package com.example.demo.controller;

import com.example.demo.service.s3.S3FileRenameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/s3")
public class S3FileRenameController {

    private final S3FileRenameService s3FileRenameService;

    @Autowired
    public S3FileRenameController(S3FileRenameService s3FileRenameService) {
        this.s3FileRenameService = s3FileRenameService;
    }

    @GetMapping("/renameFiles")
    public ResponseEntity<String> renameFiles() {
        System.out.println("rename files request was called");
        try {
            s3FileRenameService.renameFiles();
            return ResponseEntity.ok("File renaming operation completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during file renaming: " + e.getMessage());
        }
    }
}