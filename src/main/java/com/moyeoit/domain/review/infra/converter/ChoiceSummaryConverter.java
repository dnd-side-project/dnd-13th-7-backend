package com.moyeoit.domain.review.infra.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
@Slf4j
public class ChoiceSummaryConverter implements AttributeConverter<List<String>, String> {

    private static final String DELIMITER = "/v/";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        log.info("ATTRIBUTE_SIZE: {}", attribute.size());
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        String result = String.join(DELIMITER, attribute);
        return result;
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(dbData.split(DELIMITER)));
    }
}
