package com.yanwei.platform.member.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yanwei.platform.common.config.BootMemebersConfig;
import com.yanwei.platform.member.facade.IMemberShopFacade;

/**
 * 
 * 店铺信息查询
 * @author luojh
 *
 */
@Component
public class MemberShopFacade implements  IMemberShopFacade{
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	BootMemebersConfig bootMemebersConfig;

	@Override
	public JSONObject getShopDetail(String sId) {
		String url = "/merchant/biz/shop/{sid}";
		String jsonStr = restTemplate.getForEntity(bootMemebersConfig.getYanweiPath() + url, String.class,sId).getBody();
		return (JSONObject) JSONObject.parse(jsonStr);
	}
	
	@Override
	public JSONObject getMerchantDetail(String token){
	    String url = "/merchant/biz/shop/detail";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("token", token);
        requestHeaders.add("api-ver", "1.0.0");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(bootMemebersConfig.getYanweiApiPath()+url, HttpMethod.GET, requestEntity, String.class);
        JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
        if(json!=null)
          return (JSONObject) json.get("data");
        else 
         return null;
	}
	
 /*  public static JSONObject test(String token) {
	         String url = "http://api.yanwei365.net:9000/merchant/biz/shop/detail";
	         HttpHeaders requestHeaders = new HttpHeaders();
	         requestHeaders.add("token", token);
	         requestHeaders.add("api-ver", "1.0.0");
	         HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
	         RestTemplate restTemplate=new RestTemplate();
	         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
	         JSONObject json = (JSONObject) JSONObject.parse(response.getBody());
	         System.out.println(json);
	         return (JSONObject) json.get("data");
	    }*/
	  
	  public static void main(String args[]) {
	       //String[] str= {"ooEWMC", "ooBWMJ", "oovWMt", "ooXWMa", "oocWMY", "oo8WMu", "ooRWMn", "ooKWM2", "oocWM9", "ooEWMc"};
	        
	       /*JSONObject json = test("d8f1188b576e3170b207588b9e321850");
	        System.out.println(json);
	        JSONObject jsonData=(JSONObject) json.get("data");*/
	        
	    }

}
