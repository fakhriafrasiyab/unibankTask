package com.example.unitech.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String pin;

    public JwtResponse(String token, Integer id, String pin) {
        this.token = token;
        this.id = id;
        this.pin = pin;
    }

}
