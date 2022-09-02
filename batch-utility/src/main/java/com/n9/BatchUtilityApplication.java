package com.n9;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchUtilityApplication {
    public static void main(String[] args) {

        SpringApplication.run(BatchUtilityApplication.class);
    }
}