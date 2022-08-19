package com.example.demo.util;

import com.example.demo.pharmacy.dto.PharmacyDto;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CsvUtils {

    public static List<PharmacyDto> convertToPharmacyDtoList() {

        String file = "./pharmacy.csv";
        List<List<String>> csvList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                csvList.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            log.error("CsvUtils convertToPharmacyDtoList Fail: {}", e.getMessage());
        }

        return IntStream.range(1, csvList.size()).mapToObj(index -> {
            List<String> rowList = csvList.get(index);

            String[] split = rowList.get(1).split(",");

            return PharmacyDto.builder()
                    .pharmacyName(rowList.get(0))
                    .pharmacyAddress(split[0])
                    .latitude(Double.parseDouble(rowList.get(4)))
                    .longitude(Double.parseDouble(rowList.get(5)))
                    .build();
        }).collect(Collectors.toList());
    }
}
