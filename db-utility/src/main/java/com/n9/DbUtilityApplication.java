package com.n9;

import com.n9.model.TinyUrlData;
import com.n9.service.BitylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DbUtilityApplication implements CommandLineRunner {

    @Autowired
    private BitylService bitylService;

    public static void main(String[] args) {

        SpringApplication.run(DbUtilityApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        List<TinyUrlData> tinyUrlDataList =  bitylService.getTinyUrlInfo();
        System.out.println("tinyUrlDataList = "+tinyUrlDataList);

    }
}