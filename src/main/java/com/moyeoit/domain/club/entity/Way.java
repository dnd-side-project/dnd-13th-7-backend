package com.moyeoit.domain.club.entity;

import java.util.stream.Stream;
import lombok.Getter;


@Getter
public enum Way {
    온라인,
    오프라인;

    public static Way fromString(String text) {
        if (text == null) {
            return null;
        }

        return Stream.of(Way.values())
                .filter(way -> way.name().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}