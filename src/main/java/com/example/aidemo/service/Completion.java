package com.example.aidemo.service;


import com.example.aidemo.model.Template;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service

public class Completion {
    private OpenAiChatModel chatModel;

    private Template template;
    //为ai指定一个角色
    SystemPromptTemplate systemPromptTemplate = null;

    //为ai创建一个模板
    PromptTemplate promptTemplate = null;
    //记录上下文存储消息的最大数量
    private final static Integer MAX_MESSAGE_SIZE = 20;
    //这个类记录了所有的对话记录

    private List<Message> messages = new ArrayList<Message>();

    @Autowired
    public Completion(Template template,OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
        this.systemPromptTemplate = template.getSystemPromptTemplate();
        this.promptTemplate = template.getPromptTemplate();
    }

    //通过这个方法向对话中增加一条用户消息
    private Completion addUserMessage(String message){
        Message userMessage = new UserMessage(message);
        messages.add(userMessage);
        return this;
    }

    //通过这个方法向对话中添加一条ai信息
    private Completion addAssistantMessage(String message){
        Message assistantMessage = new AssistantMessage(message);
        messages.add(assistantMessage);
        return this;
    }

    //通过这个方法指定模板进行对话
    public String chatWithPrompt(String message,String argument){
        UserMessage userMessage = new UserMessage(message);
        Message promptMessage = promptTemplate.createMessage(Map.of("name",argument));
        Prompt prompt = new Prompt(List.of(promptMessage,userMessage));
        //记录返回的结果
        String result = chatModel.call(prompt.getContents().toString());
        return result;
    }
    //通过这个方法进行一次有角色的对话
    public String chatWithRoles(String argument,String message){
        UserMessage userMessage = new UserMessage(message);
        //指定角色模板
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("language",argument));
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String result = chatModel.call(prompt.getContents().toString());
        return result;
    }

    //通过这个方法进行一次有上下文的对话
    public String chat(String message){
        addUserMessage(message);
        //记录前几次的对话内容
        String result = chatModel.call(new Prompt(messages).getContents().toString());
        //添加本次的对话内容
        addAssistantMessage(result);
        //更新对话长度
        update();
        return result;
    }

    //更新对话长度
    private void update() {
        if(messages.size() > MAX_MESSAGE_SIZE){
            messages = messages.subList(messages.size() - MAX_MESSAGE_SIZE, messages.size() );
        }
    }
}
