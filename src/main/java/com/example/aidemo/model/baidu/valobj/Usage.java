package com.example.aidemo.model.baidu.valobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录对话所用的tokens数量
 */

@Data
public class Usage implements Serializable {

    /**
     * 问题 tokens 数
     */
    @JsonProperty("prompt_tokens")
    private long promptTokens;
    /**
     * 回答 tokens 数
     */
    @JsonProperty("completion_tokens")
    private long completionTokens;
    /**
     * tokens 总数
     */
    @JsonProperty("total_tokens")
    private long totalTokens;
}