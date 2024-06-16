package com.example.aidemo.model;

import lombok.Data;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Data
@Component
public class Template {
    //为ai指定一个角色
    private final static String SYSTEM_TEXT = "请你以"+"{language}"+"回答我的问题";
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(SYSTEM_TEXT);
    //为ai指定提示词
    private final static String PROMPT = "请以{name}的口吻回答我的问题";
    PromptTemplate promptTemplate = new PromptTemplate(PROMPT);

}
