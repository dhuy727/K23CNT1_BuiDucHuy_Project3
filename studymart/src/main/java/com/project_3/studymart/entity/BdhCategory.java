package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "categories")
@Data
public class BdhCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;
}

