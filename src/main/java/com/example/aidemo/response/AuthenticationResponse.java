package com.example.aidemo.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    //
    private String message;
    //成功则返回true 不成功则返回False
    private Boolean isSuccess;

}
