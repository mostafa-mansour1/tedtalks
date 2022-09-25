package com.io.tedtalks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = { "com.io.tedtalks" })
@RestController
@EnableCaching
public class TedtalksApplication {

    @RequestMapping("/")
    public String home() {
        return "full documentation:  https://documenter.getpostman.com/view/7033532/2s83RwjvSg#5435ff8b-f667-43f8-952f-a9876ff2e7f1";
    }

    public static void main(String[] args) {
        SpringApplication.run(TedtalksApplication.class, args);
    }

}
