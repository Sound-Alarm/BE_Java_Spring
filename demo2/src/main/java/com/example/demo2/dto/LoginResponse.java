package com.example.demo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginResponse {
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String role;
    private String code;

    public LoginResponse() {
    }

    public LoginResponse(String username, String email, String accessToken, String refreshToken, String role) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public LoginResponse(String username, String email, String accessToken, String role) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
        this.role = role;

    }

    public LoginResponse(String username, String email, String accessToken, String refreshToken, String role, String code) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
        this.code = code;
    }
}