package com.example.aidemo.model;

import lombok.Data;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class Template {
    //为ai指定一个角色
    private final static String SYSTEM_TEXT = "请你以"+"{language}"+"回答我的问题";
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(SYSTEM_TEXT);
    //为ai指定提示词
    private final static String PROMPT = "请告诉我关于歌手{name}的信息";
    PromptTemplate promptTemplate = new PromptTemplate(PROMPT);
    //记录上下文存储消息的最大数量

}
