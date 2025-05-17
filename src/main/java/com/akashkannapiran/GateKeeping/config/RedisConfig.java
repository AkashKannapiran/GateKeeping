package com.akashkannapiran.GateKeeping.config;

import com.akashkannapiran.GateKeeping.dto.VisitorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, VisitorDto> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, VisitorDto> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<VisitorDto>(VisitorDto.class));

        return redisTemplate;
    }
}
