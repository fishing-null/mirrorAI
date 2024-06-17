package com.example.aidemo.service;


import com.example.aidemo.model.PromptTemplateConfig;
import com.example.aidemo.model.SystemTemplateConfig;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class Completion {
    @Autowired
    private OpenAiChatModel chatModel;
    @Autowired
    private PromptTemplateConfig promptTemplateConfig;
    @Autowired
    private SystemTemplateConfig systemTemplateConfig;
    //为ai指定一个角色
    SystemPromptTemplate systemPromptTemplate = null;

    //为ai创建一个模板
    PromptTemplate promptTemplate = null;
    //记录上下文存储消息的最大数量
    private final static Integer MAX_MESSAGE_SIZE = 20;
    //这个类记录了所有的对话记录

    private List<Message> messages = new ArrayList<Message>();
    @Autowired
    public Completion(OpenAiChatModel chatModel, PromptTemplateConfig promptTemplateConfig, SystemTemplateConfig systemTemplateConfig) {
        this.chatModel = chatModel;
        this.promptTemplateConfig = promptTemplateConfig;
        this.systemTemplateConfig = systemTemplateConfig;
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
    public String chatWithPrompt(String message){
        UserMessage userMessage = new UserMessage(message);
        Message promptMessage = promptTemplate.createMessage();
        Prompt prompt = new Prompt(List.of(promptMessage,userMessage));
        //记录返回的结果
        String result = chatModel.call(prompt.getContents().toString());
        return result;
    }
    //通过这个方法进行一次有角色的对话
    public String chatWithRoles(String message){
        UserMessage userMessage = new UserMessage(message);
        //指定角色模板
        Message systemMessage = systemPromptTemplate.createMessage();
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
    //通过这个方法为ai设置模板和角色
    public void setPromptAndSystem(SystemTemplateConfig systemTemplateConfig,PromptTemplateConfig promptTemplateConfig){
        //选择不设置模板
        if(promptTemplateConfig == null){
            systemPromptTemplate = new SystemPromptTemplate(systemTemplateConfig.getSystemText());
            return;
        }else {
            //全都设置
            systemPromptTemplate = new SystemPromptTemplate(systemTemplateConfig.getSystemText());
            promptTemplate = new PromptTemplate(promptTemplateConfig.getPromptText());
        }

    }
    //更新对话长度
    private void update() {
        if(messages.size() > MAX_MESSAGE_SIZE){
            messages = messages.subList(messages.size() - MAX_MESSAGE_SIZE, messages.size() );
        }
    }
}
