package com.example.demo.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public Staff getStaff(String id){
        return staffRepository.getOne(id);
    }
}
