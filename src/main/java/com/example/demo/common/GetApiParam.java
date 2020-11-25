package com.example.demo.common;

import com.inspur.cloud.apig.sdk.util.AppSign;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by liulei03 on 2020/9/28.
 */
@Component("getApiParam")
public class GetApiParam {

    public void getParam(){
        Map<String,String> headers = AppSign.appSign("160125995737833","6616a90ca918064830a38f21c8260cc9755bada4");
        String auth_time = headers.get("x-auth-time");
        String auth_app_key = headers.get("x-auth-app-key");
        String auth_sign= headers.get("x-auth-sign");

        System.out.println("auth_time: "+auth_time+"; auth_app_key: "+auth_app_key+"; auth_sign: "+auth_sign);
    }
}
