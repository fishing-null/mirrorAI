package com.example.aidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiDemoApplication {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost","127.0.0.1");
        System.setProperty("http.proxyPort","10810");
        System.setProperty("https.proxyHost","127.0.0.1");
        System.setProperty("https.proxyPort","10810");
        SpringApplication.run(AiDemoApplication.class, args);
    }

}
