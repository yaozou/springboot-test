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
import com.yanwei.platform.member.facade.IMemberProductFacade;

/**
 * 商品查询接口
 * 
 * @author luojh
 *
 */
@Component
public class MemberProductFacade implements IMemberProductFacade {

    @Autowired
    RestTemplate       restTemplate;

    @Autowired
    BootMemebersConfig bootMemebersConfig;

    @Override
    @Log("外部接口商品信息查询接口")
    public JSONObject getDetail(String sId) {
        String url = "/spu/coin/merchant/{sid}";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-ver", Constants.API_VERSION);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(bootMemebersConfig.getYanweiApiPath() +url, HttpMethod.GET, requestEntity, String.class, sId);
        JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
         if(json==null){
             return null;
         }
        return (JSONObject) json.get("data");
    }
    
     
   public static JSONObject test(String sId) {
        String url = "http://api.yanwei365.net:9000/spu/coin/merchant/{sid}";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-ver", Constants.API_VERSION);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, sId);
        JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
         
        return (JSONObject) json.get("data");
    }

    public static void main(String args[]) {

        JSONObject json = test("ooWs_7");
        System.out.println(json);
        //JSONObject jsonData = (JSONObject) json.get("data");

    }

}
