package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public String sayHello(){
        this.runAsync();
        HelloController bean = applicationContext.getBean(HelloController.class);
//        bean.runAsync();
        return "hello world";
    }

    @GetMapping("/exception")
    public String getException(){
        throw new RuntimeException();
    }

    @GetMapping("/dateFormat")
    public String dateFormat(@RequestParam Date date,@RequestParam String name){
        System.out.println(date);
        return "";
    }

    @GetMapping("/model")
    public String getModel(@ModelAttribute("author") String author,@RequestParam String name){
        System.out.println(author);
        return "";
    }
    @Async
    public void runAsync(){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("async end");
    }
}
