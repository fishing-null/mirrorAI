package com.example.aidemo.controller;

import com.example.aidemo.model.SystemTemplateConfig;
import com.example.aidemo.service.Completion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AiController {
    @Autowired
    private Completion completion;

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "Hi") String message) {

        return completion.chat(message);
    }

    @GetMapping("/chatWithPrompt")
    public String chatWithPrompt(@RequestParam(value = "message", defaultValue = "Hi") String message) {

        return completion.chatWithPrompt(message);
    }

    //通过前端上传SystemTemplateConfig来配置ai角色
    @GetMapping("/chatWithRole")
    public String chatWithRole(@RequestParam(value = "message", defaultValue = "Hi") String message,
                               @RequestBody SystemTemplateConfig systemTemplateConfig) {
        //指定模板和角色
        completion.setPromptAndSystem(systemTemplateConfig,null);
        return completion.chatWithRoles(message);
    }
}
