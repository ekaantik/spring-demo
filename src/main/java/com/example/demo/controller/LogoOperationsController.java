package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@RequestMapping("api/v1/images")
public class LogoOperationsController{


    @PostMapping("/upload-logo")
    public ResponseEntity<String> uploadLogo(@RequestHeader(name = "Authorization") String token, @RequestParam("image") MultipartFile file) throws Exception {
//        String str = awsS3Operations.uploadImage(token,file);
        return ResponseEntity.ok("str");
    }

//    @GetMapping("/getobject")
//    public List<S3ObjectSummary> getlogo()  {
//        List<S3ObjectSummary> str = amazonS3Operations.getObject();
//        return str;
//    }

    @GetMapping("/get-logo-url")
    public String  getLogoUrl(@RequestHeader(name = "Authorization") String token)  {
//        return awsS3Operations.retrieveUrl(token);
        return null;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadlogo(@RequestHeader(name = "Authorization") String token, @RequestParam("image") MultipartFile file) throws Exception {
//        String str = amazonS3Operations.uploadImage(token,file);
        return ResponseEntity.ok("str");
    }

//    @GetMapping("/getobject")
//    public List<S3ObjectSummary> getlogo()  {
//        List<S3ObjectSummary> str = amazonS3Operations.getObject();
//        return str;
//    }

    @GetMapping("/getimage")
    public ResponseEntity<byte[]> downloadimage(@RequestHeader(name = "Authorization") String token) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", MediaType.ALL_VALUE);
        headers.add("Content-Disposition","attchment; filename="+token);
        byte[] bytes = null; //amazonS3Operations.downloadFile(token);
        return ResponseEntity.status(HTTP_OK).headers(headers).body(bytes);
    }

}
