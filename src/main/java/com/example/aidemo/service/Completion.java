package com.example.aidemo.service;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.messages.AssistantMessage;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class Completion {
    //这个类记录了所有的对话记录
    private final AiClient aiClient;
    private final static Integer MAX_MESSAGE_SIZE = 10;
    private String completion;
    private List<Message> messages = new ArrayList<Message>();
    public Completion(AiClient aiClient){
        this.aiClient = aiClient;
    }
    //通过这个方法向对话中增加一条用户消息
    private Completion addUserMessage(String message){
        Message userMessage = new UserMessage(message);
        messages.add(userMessage);
        return this;
    }

    //通过这个方法向对话中添加一条AI信息
    private Completion addAssistantMessage(String message){
        Message assistantMessage = new AssistantMessage(message);
        messages.add(assistantMessage);
        return this;
    }

    //通过这个方法进行一次有上下文的对话
    public String chat(String message){
        addUserMessage(message);
        String result = aiClient.generate(new Prompt(messages).getContents().toString());
        addAssistantMessage(result);
        //更新对话长度
        update();
        return result;
    }

    private void update() {
        if(messages.size() > MAX_MESSAGE_SIZE){
            messages = messages.subList(messages.size() - MAX_MESSAGE_SIZE, messages.size() );
        }
    }
}
