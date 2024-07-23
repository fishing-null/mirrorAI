package com.example.aidemo.service;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.example.aidemo.model.baidu.valobj.Message;
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
    //配置人工智能类型
    @Value("${app.qianfan.model}")
    private String MODEL;
    @Value("${app.qianfan.url}")
    private String API_URL;
    public List<String> createConversation(List<Message> messages) {

        List<String> answers = new ArrayList<>();
        try {
            Qianfan qianfan = new Qianfan(ACCESS_KEY, SECRET_KEY);
            ChatResponse chatResponse = null;
            for(Message message:messages){
                ChatBuilder model = qianfan.chatCompletion().model(MODEL);
                //遍历列表添加对话内容
                chatResponse = model.addMessage("user",message.getContent()).execute();
                //将对话内容添加到响应中返回
                answers.add(chatResponse.getResult());
            }
        }catch (Exception e){
            log.error("============调用"+MODEL+"失败============");
            e.printStackTrace();

        }

        return answers;
    }

}
