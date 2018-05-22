package com.yanwei.platform.member.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.yanwei.platform.common.annotation.Log;
import com.yanwei.platform.common.config.BootMemebersConfig;
import com.yanwei.platform.common.constant.Constants;
import com.yanwei.platform.member.facade.IMemberUseFacade;

/**
 * 
 * 会员详细信息查询接口
 *
 */
@Component
public class MemberUseFacade implements IMemberUseFacade {

    @Autowired
    RestTemplate       restTemplate;

    @Autowired
    BootMemebersConfig bootMemebersConfig;

    @Override
    @Log("外部接口会员详细信息查询接口")
    public JSONObject getUserDetail(String userId) {
        String url = "/user/simple/{userId}";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-ver", Constants.API_VERSION);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
            bootMemebersConfig.getYanweiApiPath() + url, HttpMethod.GET, requestEntity,
            String.class, userId);
        JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
        if(json==null)
            return null;
        
        return (JSONObject) json.get("data");
    }

    @Override
    @Log("外部接口根据token获取会员详细信息查询接口")
    public JSONObject getUserToken(String token) {
        String url = bootMemebersConfig.getYanweiApiPath()+"/user/simple";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token", token);
        requestHeaders.add("api-ver", Constants.API_VERSION);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return (JSONObject) JSONObject.parse(response.getBody());
    }

 /* public static JSONObject test(String token) {
         String url = "http://api.yanwei365.net:9000/user/simple";
         HttpHeaders requestHeaders = new HttpHeaders();
         requestHeaders.add("token", token);
         requestHeaders.add("api-ver", "1.0.0");
         HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
         RestTemplate restTemplate=new RestTemplate();
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
         JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
         System.out.println(json);
         return (JSONObject) json.get("data");
    }
    
    public static void main(String args[]) {
       //String[] str= {"ooEWMC", "ooBWMJ", "oovWMt", "ooXWMa", "oocWMY", "oo8WMu", "ooRWMn", "ooKWM2", "oocWM9", "ooEWMc"};
        
        JSONObject json = test("eba57b65b7b42dd18c97be3e7094f27b");
        System.out.println(json);
        JSONObject jsonData=(JSONObject) json.get("data");
        
    }*/

}
