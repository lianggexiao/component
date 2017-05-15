//package com.qing.simpleframe.core;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.qing.simpleframe.domain.entity.User;
//
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, User> template = new RedisTemplate<String, User>();
//        template.setConnectionFactory(factory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
////        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        return template;
//    }
//
//}
