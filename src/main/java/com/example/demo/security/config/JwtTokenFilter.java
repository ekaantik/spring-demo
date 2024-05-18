package com.example.demo.security.config;

import com.example.demo.constants.UserType;
import com.example.demo.repository.ApiUsageRepoService;
import com.example.demo.security.entity.User;
import com.example.demo.security.utils.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Autowired
    private ApiUsageRepoService apiUsageRepoService;

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        String requestBody = null;
        String responseBody = null;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            log.info("return from first");
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.split(" ")[1].trim();
        if (!jwtTokenService.validateToken(jwtToken)) {
            log.info("return from second");
            filterChain.doFilter(request, response);
            return;
        }

        setAuthenticationContext(jwtToken, request);
        log.info("return from third");
//        filterChain.doFilter(request, response);
        filterChain.doFilter(requestWrapper, responseWrapper);

        requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();


        apiUsageRepoService.save(request, response, requestBody,responseBody,jwtToken, startTime);

//        log.info("responseBody : {}  ",responseBody);

    }


    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
//        log.info("########### JwtTokenFilter setAuthenticationContext userDetails authorities : {}  Username : {}  Password : {}",
//                userDetails.getAuthorities(), userDetails.getUsername(), userDetails.getPassword());
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        log.info("########### Authorities granted : " + authorities);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        User userDetails = new User();
        Claims claims = jwtTokenService.extractAllClaims(token);
        String roles = (String) claims.get("roles");
        String phoneNumber = (String) claims.get("userPn");

        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.replaceAll("\\s", "").split(",");
        log.info("get uer roleNames : {}",roleNames);
        for (String aRoleName : roleNames) {
//            userDetails.setUserType(UserType.valueOf(aRoleName));
            userDetails.setUserType(UserType.valueOf(aRoleName));
        }
        userDetails.setPhoneNumber(phoneNumber);
        log.info("get uer details : {}",userDetails);
        return userDetails;
    }

}
