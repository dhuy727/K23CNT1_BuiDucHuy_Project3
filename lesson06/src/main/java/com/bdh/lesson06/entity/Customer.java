package com.bdh.lesson06.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String fullName;
    String address;
    String phone;
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDay;

    Boolean active;
}
