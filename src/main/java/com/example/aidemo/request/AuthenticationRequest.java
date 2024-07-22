package com.example.aidemo.request;


import lombok.Data;

@Data
public class AuthenticationRequest{
    private String accessKey;
    private String secretKey;
}
