package com.example.aidemo.request;

import lombok.Data;

import java.util.List;
@Data
public class ChatRequest {
    private List<String> messages;
}
