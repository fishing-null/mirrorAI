package com.example.aidemo.service;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Ernie")
@Component
public class QianfanService {
    @Value("${app.accessKey}")
    private String ACCESS_KEY;
    @Value("${app.secretKey}")
    private String SECRET_KEY;


    public void createConversation(String text) {
        System.out.println(ACCESS_KEY);
        // 假设你有一个方法来处理聊天请求
        ChatResponse response = new Qianfan(ACCESS_KEY, SECRET_KEY).chatCompletion()
                .model("ERINE-Lite-8K")
                .addMessage("user", text)
                .temperature(0.7)
                .execute();
        System.out.println(response.getResult());
    }
}
