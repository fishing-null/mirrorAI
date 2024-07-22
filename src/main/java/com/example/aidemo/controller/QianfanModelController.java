package com.example.aidemo.controller;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.image.Image2TextResponse;
import com.example.aidemo.request.ChatRequest;
import com.example.aidemo.response.ChatResponse;
import com.example.aidemo.service.QianfanService;
import com.example.aidemo.utils.formatUtils.PhotoFormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qianfan")
public class QianfanModelController {
    @Autowired
    private QianfanService qianfanService;
    //通过这个接口,调用百度千帆的人工智能
    @RequestMapping(value = "/chat", produces = "application/json")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request){
        log.info("调用百度千帆模型");
        //读取请求
        List<String> messageList = request.getMessages();
        //构造响应
        ChatResponse response = new ChatResponse();
        //调用service层的方法
        try {
            List<String> answers = qianfanService.createConversation(request.getMessages());
            response.setAnswers(answers);
            if (answers == null || answers.isEmpty()) {
                response.setErrorInfo("没有可用的回答请稍后再试");
            }else {
                response.setErrorInfo("");
            }
        }catch (Exception e){
            log.error("=================QianfanService内部错误================");
            //服务器内部错误,返回500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

}
