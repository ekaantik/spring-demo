package com.example.demo.repository;


import com.example.demo.entity.ApiUsage;
import com.example.demo.security.utils.JwtTokenService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Slf4j
public class ApiUsageRepoService {

    private final ApiUsageRepo apiUsageRepo;
    private final JwtTokenService jwtTokenService;


    @Autowired
    ApiUsageRepoService(ApiUsageRepo apiUsageRepo, JwtTokenService jwtTokenService){
        this.apiUsageRepo = apiUsageRepo;
        this.jwtTokenService = jwtTokenService;
    }

    @Async("apiUsageSave")
    public ApiUsage save(HttpServletRequest request, HttpServletResponse response, String requestBody, String responseBody, String jwtToken, long startTime) {
        try {
            String userId = null;
            try{
                userId = jwtTokenService.extractClaimsId(jwtToken).toString();
            } catch (Exception e){
                log.info("Failed to extract token setting userId default to null");
            }

            long endTime = System.currentTimeMillis();
            long timeTaken =  endTime - startTime;

            String queryString = null ;
            if (Objects.isNull(request.getQueryString())){
                queryString = request.getRequestURI();
            } else {
                queryString = request.getRequestURI()+ "?" + request.getQueryString();
            }

            if (queryString.contains("/download/")){
                responseBody = null;
            }

            ApiUsage apiUsage = ApiUsage.builder()
                    .userId(userId)
                    .requestMethod(request.getMethod())
                    .requestUrl(queryString)
                    .requestBody(requestBody)
                    .responseBody(responseBody)
                    .responseStatus(response.getStatus())
                    .startTime(startTime)
                    .endTime(endTime)
                    .execTime(timeTaken)
                    .build();
            ApiUsage savedAssetDetails = apiUsageRepo.save(apiUsage);
            return savedAssetDetails;
        }
        catch (Exception ex) {
            log.error("Failed to create AssetDetails, Exception : " + ex.getMessage(), ex);
            throw new PersistenceException("Failed To create record into database!", ex);
        }
    }

}
