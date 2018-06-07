package com.yaozou.platform.common.utils;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiPost {
    private static String posturl;
    //默认请求数量
    private static int pagesize = 10;
    //默认页码
    private static int pagenum = 1;
    
    public static String token = "562523c504a5cb4bf5faafb6fd0ed3f2";
    //app 58faaab51548987905a37c1b1dcbb04a ios
    //40e93b13f69b699f540ee6e2741e1659
    //88e3162c586eaab0544d2600d0350866 商户 allinpay
    
    //创建RestTemplate模板
    protected static RestTemplate bulidRestTemplate(){
        
        RestTemplate restTemplate = new RestTemplate();
        //json
        MappingJackson2HttpMessageConverter json = new MappingJackson2HttpMessageConverter();
        //xml
        //......
        
        restTemplate.getMessageConverters().add(json);
        
        return restTemplate;
    }

    
    /**
     * 发送Post请求
     * @param url
     * @param s
     * @return
     * @throws HttpExcetpion
     */
    public static <S> Map sendPost(String url,S s,String apiVer) {
        return sendPost(url, s, apiVer, token);
    }   
    public static <S> Map sendPost(String url,S s,String apiVer,String token) {
        
        log.info("服务调用,请求地址:"+url);
        
        posturl = url;
        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-ver", apiVer);
        requestHeaders.add("token", token);
        requestHeaders.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        requestHeaders.add("channel", "ooo_PJ");
        requestHeaders.add("merchant", "ooo_PJ");
        
        HttpEntity<S> entity = new HttpEntity<S>(s, requestHeaders);

        ResponseEntity<Map> responseEntity = null;
        
        try {
            
            responseEntity =  bulidRestTemplate().exchange(url, HttpMethod.POST, entity, Map.class);
        
        }catch(Exception e){
            
            log.error("sendPost error", e);
        }
        
        reset();
        
        return responseEntity.getBody();
    }
    
    //发送Post请求
    public static <S> Map sendPost(String url,String apiVer,PageRequest pageRequest,S s) {
        
        log.info("服务调用,请求地址:"+url);
        
        posturl = url;
        
        pagesize = pageRequest.getPageSize();
        
        pagenum = pageRequest.getPageNumber();
        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-ver", apiVer);
        requestHeaders.add("token", token);
        requestHeaders.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        
        JSONObject json = (JSONObject) JSONObject.toJSON(s);
        json.put("pageIndex", pagenum);
        json.put("pageSize", pagesize);
        
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(json, requestHeaders);
        
        ResponseEntity<Map> responseEntity  = null;
        
        try{
        
             responseEntity =  bulidRestTemplate().exchange(url, HttpMethod.POST, entity, Map.class);
        
        }catch(Exception e){
            
            log.info("sendPost error", e);
        }
        
        reset();
        
        return responseEntity.getBody();
    }
    
    private static void reset() {
        //默认请求数量
        pagesize = 10;
        //默认页码
        pagenum = 1;
    }
}
