package com.kraftwerking.collibrademo.config;

import com.kraftwerking.collibrademo.model.MyObject;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Autowired
    RedisConnectionFactory factory;

    @Bean
    public ReactiveRedisTemplate<String, MyObject> reactiveRedisMyObjectTemplate(
            ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<MyObject> serializer =
                new Jackson2JsonRedisSerializer<>(MyObject.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, MyObject> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, MyObject> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveKeyCommands keyCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
                .keyCommands();
    }

    @Bean
    public ReactiveStringCommands stringCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
                .stringCommands();
    }

    @PreDestroy
    public void cleanRedis() {
        factory.getConnection()
                .flushDb();
    }

}
