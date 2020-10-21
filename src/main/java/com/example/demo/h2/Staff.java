package com.example.demo.h2;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;

@Data//可以省略get set方法。
@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    @Column(name="age")
    private int age;
}
