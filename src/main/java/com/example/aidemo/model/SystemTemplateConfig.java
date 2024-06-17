package com.example.aidemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
//配置ai角色模板
public class SystemTemplateConfig {
    private String systemText;
}
