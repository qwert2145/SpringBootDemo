package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootDemoApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
//        new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE)
//                .sources(SpringBootDemoApplication.class)
//                .run(args);
    }
}
