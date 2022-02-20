package com.example.unitech.service;

import com.example.unitech.request.LoginRequest;
import com.example.unitech.request.SignUpRequest;
import com.example.unitech.response.JwtResponse;

public interface AuthService {
    void registerUser(SignUpRequest signUpRequest);
    JwtResponse authenticate(LoginRequest loginRequest);
}
