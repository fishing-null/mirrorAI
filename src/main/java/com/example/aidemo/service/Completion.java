package com.example.aidemo.service;


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
    @Autowired
    private OpenAiChatModel chatModel;

    //为ai指定一个角色
    private final static String SYSTEM_TEXT = "请你以"+"{language}"+"回答我的问题";
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(SYSTEM_TEXT);
    //为ai指定提示词
    private final static String PROMPT = "请告诉我关于歌手{name}的信息";
    PromptTemplate promptTemplate = new PromptTemplate(PROMPT);
    //记录上下文存储消息的最大数量
    private final static Integer MAX_MESSAGE_SIZE = 10;
    //这个类记录了所有的对话记录

    private String completion;
    private List<Message> messages = new ArrayList<Message>();


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
    public String chatWithPrompt(String message){
        Prompt prompt = promptTemplate.create(Map.of("name",message));
        return chatModel.call(prompt).getResult().toString();
    }
    //通过这个方法进行一次有角色的对话
    public String chatWithRoles(String argument,String message){
        UserMessage userMessage = new UserMessage(message);
        //指定角色模板
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(SYSTEM_TEXT);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("language",argument));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        List<Generation> response = chatModel.call(prompt).getResults();
        return response.toString();
    }

//    //通过这个方法进行一次有上下文的对话
//    public String chat(String message){
//        addUserMessage(message);
//        //记录前几次的对话内容
//        String result = aiClient.generate(new Prompt(messages).getContents().toString());
//        //添加本次的对话内容
//        addAssistantMessage(result);
//        //更新对话长度
//        update();
//        return result;
//    }



//    //更新对话长度
//    private void update() {
//
//        if(messages.size() > MAX_MESSAGE_SIZE){
//            messages = messages.subList(messages.size() - MAX_MESSAGE_SIZE, messages.size() );
//            messages.add(0,systemMessage);
//        }
//    }
}
