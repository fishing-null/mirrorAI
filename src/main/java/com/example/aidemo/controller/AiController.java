package com.example.aidemo.controller;

import org.springframework.ai.client.AiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AiController {
    private final AiClient aiClient;
    public AiController(AiClient aiClient){
        this.aiClient = aiClient;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "Hi") String message){
        return aiClient.generate(message);
    }
}
