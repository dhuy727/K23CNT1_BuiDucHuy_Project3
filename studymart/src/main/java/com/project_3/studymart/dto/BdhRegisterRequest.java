package com.project_3.studymart.dto;

import lombok.Data;

@Data
public class BdhRegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
