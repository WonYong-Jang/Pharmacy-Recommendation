package com.example.demo.pharmacy.service;

import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public List<Pharmacy> saveAll(List<Pharmacy> pharmacyList) {
        if(CollectionUtils.isEmpty(pharmacyList)) return Collections.emptyList();
        return pharmacyRepository.saveAll(pharmacyList);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
