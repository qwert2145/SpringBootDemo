package com.example.demo.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public Staff getStaff(String id){
        System.out.println("get staff");
        return staffRepository.getOne(id);
    }
}
