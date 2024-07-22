package com.example.aidemo.service;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QianfanService {
    @Value("${app.qianfan.accessKey}")
    private String ACCESS_KEY;
    @Value("${app.qianfan.secretKey}")
    private String SECRET_KEY;
    @Value("${app.qianfan.model}")
    private String MODEL;
    @Value("${app.qianfan.url}")
    private String API_URL;
    public List<String> createConversation(List<String> messages) {

        List<String> answers = new ArrayList<>();
        try {
            Qianfan qianfan = new Qianfan(ACCESS_KEY, SECRET_KEY);
            ChatResponse chatResponse = null;
            for(String message:messages){
                ChatBuilder model = qianfan.chatCompletion().model(MODEL);
                //遍历列表添加对话内容
                chatResponse = model.addMessage("user",message).execute();
                //将对话内容添加到响应中返回
                answers.add(chatResponse.getResult());
            }
        }catch (Exception e){
            log.error("============调用"+MODEL+"失败============");
            e.printStackTrace();

        }

        return answers;
    }

    public static void main(String[] args) {
        Qianfan qianfan = new Qianfan("ALTAK59yqhccjmVKnZR02NOXE0","2cd5b37ce5904862b13792126dc048d2");
        // 选择要调用的模型
        List<String> answers = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        messages.add("现在几点了");
        messages.add("请你介绍一下西安");
        messages.add("再讲讲怎么做当地的特色美食");
        ChatResponse chatResponse = null;
        for(String message:messages){
            ChatBuilder model = qianfan.chatCompletion().model("ERNIE-Tiny-8K");
            //遍历列表添加对话内容
            chatResponse = model.addMessage("user", message).execute();
            //将对话内容添加到响应中返回
            answers.add(chatResponse.getResult());
        }
        System.out.println(answers);
    }
}
