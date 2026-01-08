package com.moyeoit.context.club.infra.query;

import java.util.stream.Stream;
import lombok.Getter;


@Getter
public enum ClubActivityType {
    ONLINE("온라인"),
    OFFLINE("오프라인");

    private final String description;

    ClubActivityType(String description) {
        this.description = description;
    }

    public static ClubActivityType fromString(String text) {
        if (text == null) {
            return null;
        }

        return Stream.of(ClubActivityType.values())
                .filter(clubActivityType -> clubActivityType.description.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid way: " + text));
    }
}