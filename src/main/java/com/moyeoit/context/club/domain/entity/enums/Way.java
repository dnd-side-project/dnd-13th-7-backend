package com.moyeoit.context.club.domain.entity.enums;

import java.util.stream.Stream;
import lombok.Getter;


@Getter
public enum Way {
    ONLINE("온라인"),
    OFFLINE("오프라인");

    private final String description;

    Way(String description) {
        this.description = description;
    }

    public static Way fromString(String text) {
        if (text == null) {
            return null;
        }

        return Stream.of(Way.values())
                .filter(way->way.description.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid way: " + text));
    }
}