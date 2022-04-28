package com.example.demo.pharmacy.cache;

import com.example.demo.pharmacy.dto.PharmacyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class PharmacyRedisTemplateService {

    private static final String CACHE_KEY = "PHARMACY";

    private final HashOperations hashOperations;

    @Autowired
    public PharmacyRedisTemplateService(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(PharmacyDto pharmacyDto) {
        if(Objects.isNull(pharmacyDto)) {
            log.error("PharmacyDto must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, pharmacyDto.getId(), pharmacyDto);
        } catch (Exception e) {
            log.error("PharmacyDto save error ", e);
        }
    }
}
