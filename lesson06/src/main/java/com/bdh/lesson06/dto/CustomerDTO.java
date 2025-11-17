package com.bdh.lesson06.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

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
