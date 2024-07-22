package com.example.aidemo.controller;

import com.baidubce.qianfan.Qianfan;
import com.example.aidemo.request.AuthenticationRequest;
import com.example.aidemo.response.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/api/common")
public class AuthenticationController {

    /**
     * 前端请求格式{String accessKey,String secretKey}
     * 后端响应格式{String message,Boolean isSuccess}
     * @param request
     * @return
     */
    @RequestMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        //后端返回给前端的响应
        AuthenticationResponse response = new AuthenticationResponse();
        //从请求中取出apiKey以及secretKey
        String accessKey = request.getAccessKey();  
        //判断是否存在该apiKey以及secretKey
        String secretKey = request.getSecretKey();
        try{
            if(accessKey == null || secretKey == null || accessKey == "" || secretKey == ""){
                //accessKey或者secretKey为空的情况 代表验证失败
                response.setMessage("Invalid accessKey or secretKey");
                response.setIsSuccess(false);
            }else {
                response.setMessage("Authentication successful");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setIsSuccess(false);
            response.setMessage("Authentication failed due to an error");
            //返回状态码500 表示服务器内部错误
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    //验证
    private boolean validateCredentials(String accessKey,String secretKey){
        //TODO 现在没有验证的逻辑 不管什么情况都返回true

        return true;
    }
}
