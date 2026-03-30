package com.moyeoit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MoyeoitApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoyeoitApiApplication.class, args);
    }

}
