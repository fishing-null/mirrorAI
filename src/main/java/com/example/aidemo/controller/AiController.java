package com.example.aidemo.controller;

import com.example.aidemo.service.Completion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AiController {
    private final Completion completion;

    public AiController(Completion completion){
        this.completion = completion;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "Hi") String message){

        return completion.chat(message);
    }
    @GetMapping("/chatWithPrompt")
    public String chatWithPrompt(@RequestParam(value = "message",defaultValue = "Hi") String message){

        return completion.chatWithPrompt(message);
    }
    @GetMapping("/chatWithLanguage")
    public String chatWithLanguage(@RequestParam(value = "message",defaultValue = "Hi")String message,
                                   @RequestParam(value = "language",defaultValue = "中文") String language){
        return completion.chatWithRoles(language,message);
    }
}
