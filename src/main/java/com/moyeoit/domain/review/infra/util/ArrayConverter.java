package com.moyeoit.domain.review.infra.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayConverter {

    public static List<String> toStringArray(String value, String delimeter) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(value.split(delimeter));
    }

    public static List<Integer> toIntegerArray(String value, String delimeter) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(delimeter))
                .map(Integer::valueOf)
                .toList();
    }

    public static String toTextFromStringArray(List<String> values, String delimeter) {
        return values.stream()
                .collect(Collectors.joining(delimeter));
    }

    public static String toTextFromIntegerArray(List<Integer> values, String delimeter) {
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(delimeter));
    }

}
