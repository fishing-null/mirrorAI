package com.example.aidemo.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
//配置ai对话模板
public class PromptTemplateConfig {
    private String promptText;
}
