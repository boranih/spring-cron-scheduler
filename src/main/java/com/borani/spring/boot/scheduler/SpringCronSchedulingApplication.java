package com.borani.spring.boot.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCronSchedulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCronSchedulingApplication.class, args);
    }

}
