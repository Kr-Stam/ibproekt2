package com.example.ibproekt2.entity.dto;

import lombok.Data;

@Data
public class ResetPasswordData {

    private String email;
    private String token;
    private String password;
    private String repeatPassword;
}