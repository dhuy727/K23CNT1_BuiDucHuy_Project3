package com.project_3.studymart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transport_method")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BdhTransportMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 50)
    String code;

    @Column(nullable = false, length = 100)
    String name;

    Double price;

    Integer estimatedDays;

    Boolean active = true;
}
