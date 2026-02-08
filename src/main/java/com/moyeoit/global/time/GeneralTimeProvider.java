package com.moyeoit.global.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GeneralTimeProvider implements TimeProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
