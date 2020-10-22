package com.example.demo.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @GetMapping()
    public String getStaff(@RequestParam String id){
        System.out.println(Thread.currentThread().getName() +" "+  System.currentTimeMillis());
        return staffService.getStaff(id).toString();
    }
}
