package ru.netology.TransferMoneyApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TransferMoneyApiApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(TransferMoneyApiApplication.class, args);
    }
}