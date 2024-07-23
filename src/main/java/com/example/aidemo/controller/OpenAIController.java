package com.example.aidemo.controller;

import com.example.aidemo.config.SystemTemplateConfig;
import com.example.aidemo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OpenAIController {
    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "Hi") String message) {

        return openAIService.chat(message);
    }

    @GetMapping("/chatWithPrompt")
    public String chatWithPrompt(@RequestParam(value = "message", defaultValue = "Hi") String message) {

        return openAIService.chatWithPrompt(message);
    }

    //通过前端上传SystemTemplateConfig来配置ai角色
    @GetMapping("/chatWithRole")
    public String chatWithRole(@RequestParam(value = "message", defaultValue = "Hi") String message,
                               @RequestBody SystemTemplateConfig systemTemplateConfig) {
        //指定模板和角色
        openAIService.setPromptAndSystem(systemTemplateConfig,null);
        return openAIService.chatWithRoles(message);
    }
}
