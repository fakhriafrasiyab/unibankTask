package com.example.unitech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UnitechApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitechApplication.class, args);
    }

}
