package com.biblioteca.bibliotetca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.biblioteca.bibliotetca", "util"})
@EnableAutoConfiguration
public class BibliotetcaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotetcaApplication.class, args);
    }

}
