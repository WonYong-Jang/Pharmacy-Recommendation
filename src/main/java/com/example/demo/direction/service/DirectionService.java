package com.example.demo.direction.service;

import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.repository.DirectionRepository;
import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectionService {

    private final DirectionRepository directionRepository;
    private static final Base62 base62Instance = Base62.createInstance();

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    @Transactional(readOnly = true)
    public Direction findById(String encodedId) {

        Long decodedId = decodeDirectionId(encodedId);
        return directionRepository.findById(decodedId).orElse(null);
    }


    public String encodeDirectionId(Long directionId) {
        return new String(base62Instance.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId) {

        String resultDirectionId = new String(base62Instance.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }
}
