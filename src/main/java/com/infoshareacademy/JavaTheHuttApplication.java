package com.infoshareacademy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JavaTheHuttApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(JavaTheHuttApplication.class, args);


    }


    @Override
    public void run(String... args) {

    }
}
