package com.example.aidemo.controller;

import com.baidubce.qianfan.model.chat.ChatResponse;
import com.example.aidemo.model.baidu.valobj.Message;
import com.example.aidemo.request.BaiduCompletionRequest;
import com.example.aidemo.response.BaiduCompletionResponse;
import com.example.aidemo.service.QianfanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qianfan")
public class QianfanModelController {
    @Autowired
    private QianfanService qianfanService;
    //通过这个接口,调用百度千帆的人工智能
    @RequestMapping(value = "/chat", produces = "application/json")
    public ResponseEntity<BaiduCompletionResponse> chat(@RequestBody BaiduCompletionRequest request){
        log.info("调用百度千帆模型");
        //读取请求
        List<Message> messageList = request.getMessages();
        //构造响应
        BaiduCompletionResponse response = new BaiduCompletionResponse();
        //调用service层的方法
        try {
            ChatResponse chatResponse = qianfanService.createConversation(request.getMessages(),request);
            if (chatResponse == null ) {
                response.setErrorInfo("没有可用的回答,请稍后再试");
            }else {
                //TODO 这部分逻辑到时候要重构成新的方法
                //封装chatResponse的内容到BaiduCompletionResponse
                response.setId(chatResponse.getId());
                response.setObject(chatResponse.getObject());
                response.setCreated(chatResponse.getCreated());
                response.setSentenceId(chatResponse.getSentenceId());
                response.setResult(chatResponse.getResult());
                response.setIsTruncated(chatResponse.getTruncated());
                response.setNeedClearHistory(chatResponse.getNeedClearHistory());
                response.setBanRound(chatResponse.getBanRound());
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
