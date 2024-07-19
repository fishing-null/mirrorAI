package com.example.aidemo.controller;

import com.example.aidemo.service.QianfanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ErnieController {
    @Autowired
    private QianfanService ernieService;

    /**
     * 通过这个接口发起HTTP请求来和Ernie对话
     *
     */
    @RequestMapping("/ernieController")
    public void createConversation(@RequestParam(value = "message",defaultValue = "hi") String message){

        ernieService.createConversation(message);

    }
}
