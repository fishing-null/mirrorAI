package com.example.aidemo.controller;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.example.aidemo.service.ErnieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ErnieController {
    @Autowired
    private ErnieService ernieService;

    /**
     * 通过这个接口发起HTTP请求来和Ernie对话
     *
     */
    @RequestMapping("/ernieController")
    public void createConversation(@RequestParam(value = "message",defaultValue = "hi") String message){

        ernieService.createConversation(message);

    }
}
