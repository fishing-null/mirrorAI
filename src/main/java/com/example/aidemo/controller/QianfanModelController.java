package com.example.aidemo.controller;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.image.Image2TextResponse;
import com.example.aidemo.utils.formatUtils.PhotoFormatUtils;

public class QianfanModelController {
    public static void main(String[] args) {
        Qianfan qianfan = new Qianfan("you accessKey", "you secretKey");
        String filePath = PhotoFormatUtils.encodeImageToBase64Binary("E:\\photos\\R-C.jpg");
        Image2TextResponse response = qianfan.image2Text().model("Fuyu-8B")
                .image(filePath)
                .prompt("说一下图片的内容")
                .execute();
        System.out.println(response.getResult());
    }
}
