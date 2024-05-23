package com.example.demo.config;

import com.example.demo.pojos.response.ManagerResponse;
import com.example.demo.pojos.response.TechnicianResponse;
import com.example.demo.pojos.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Utils;
import com.example.demo.pojos.response.ShiftResponse;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableTransactionManagement
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<String, ShiftResponse> shiftTemplate(RedisConnectionFactory connectionFactory) {

        // Initialize RedisTemplate
        RedisTemplate<String, ShiftResponse> template = new RedisTemplate<>();

        // Set Serializer
        Jackson2JsonRedisSerializer<ShiftResponse> serializer = new Jackson2JsonRedisSerializer<>(ShiftResponse.class);
        serializer.setObjectMapper(Utils.getObjectMapper());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ShiftResponse.class));

        // Set Connection Factory
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, UserResponse> userTemplate(RedisConnectionFactory connectionFactory) {

        // Initialize RedisTemplate
        RedisTemplate<String, UserResponse> template = new RedisTemplate<>();

        // Set Serializer
        Jackson2JsonRedisSerializer<UserResponse> serializer = new Jackson2JsonRedisSerializer<>(UserResponse.class);
        serializer.setObjectMapper(Utils.getObjectMapper());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(UserResponse.class));

        // Set Connection Factory
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, ManagerResponse> managerTemplate(RedisConnectionFactory connectionFactory) {

        // Initialize RedisTemplate
        RedisTemplate<String, ManagerResponse> template = new RedisTemplate<>();

        // Set Serializer
        Jackson2JsonRedisSerializer<ManagerResponse> serializer = new Jackson2JsonRedisSerializer<>(ManagerResponse.class);
        serializer.setObjectMapper(Utils.getObjectMapper());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ManagerResponse.class));

        // Set Connection Factory
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, TechnicianResponse> technicianTemplate(RedisConnectionFactory connectionFactory) {

        // Initialize RedisTemplate
        RedisTemplate<String, TechnicianResponse> template = new RedisTemplate<>();

        // Set Serializer
        Jackson2JsonRedisSerializer<TechnicianResponse> serializer = new Jackson2JsonRedisSerializer<>(TechnicianResponse.class);
        serializer.setObjectMapper(Utils.getObjectMapper());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(TechnicianResponse.class));

        // Set Connection Factory
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
