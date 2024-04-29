package com.example.demo.config.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfig{

    private static final int MAX_PAYLOAD_LENGTH = 10000; 

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH); 
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST : ");
        return filter;
    }
}
