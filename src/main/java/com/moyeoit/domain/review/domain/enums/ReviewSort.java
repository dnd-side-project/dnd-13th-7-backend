package com.moyeoit.domain.review.domain.enums;

import java.util.stream.Stream;

public enum ReviewSort {
    인기순,
    최신순,
    모집중;

    public static ReviewSort fromString(String text){
        if (text == null) {
            return null;
        }
        return Stream.of(ReviewSort.values())
                .filter(reviewSort -> reviewSort.name().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}
