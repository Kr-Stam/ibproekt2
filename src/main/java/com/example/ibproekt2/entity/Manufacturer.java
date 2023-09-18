package com.example.ibproekt2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "manufacturers")
@Data
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "manufacturer_name")
    private String name;

    @Column(name = "manufacturer_email")
    private String email;


}
