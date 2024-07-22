package com.example.aidemo.response;

import lombok.Data;

import java.util.List;
@Data
public class ChatResponse {
    private List<String> answers;
    private String errorInfo;
}
