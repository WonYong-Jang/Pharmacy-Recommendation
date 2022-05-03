package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//class RedisConfigTest {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Test
//    void testString() {
//        // given
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        String key = "stringKey";
//        String value = "hello";
//
//        // when
//        valueOperations.set(key, value);
//
//        // then
//        String resultValue = valueOperations.get(key);
//        assertThat(value).isEqualTo("hello");
//    }
//
//    @Test
//    void testHash() {
//        // given
//        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
//        String key = "hashKey";
//
//        // when
//        hashOperations.put(key, "hello", "world");
//
//        // then
//        Object value = hashOperations.get(key, "hello");
//        assertThat(value).isEqualTo("world");
//
//        Map<Object, Object> entries = hashOperations.entries(key);
//        assertThat(entries.keySet()).containsExactly("hello");
//        assertThat(entries.values()).containsExactly("world");
//
//        Long size = hashOperations.size(key);
//        assertThat(size).isEqualTo(entries.size());
//    }
//
//    @Test
//    void testSet() {
//        // given
//        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
//        String key = "setKey";
//
//        // when
//        setOperations.add(key, "h", "e", "l", "l", "o");
//
//        // then
//        Set<String> members = setOperations.members(key);
//        Long size = setOperations.size(key);
//
//        assertThat(members).containsOnly("h", "e", "l", "o");
//        assertThat(size).isEqualTo(4);
//    }
//}