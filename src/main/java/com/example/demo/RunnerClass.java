package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerClass implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("系统开始缓存产能统计信息");
        //获取业务数据信息
        //开启定时任务
        System.out.println("系统完成缓存产能统计信息");
    }
}
