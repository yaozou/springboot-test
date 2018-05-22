package com.yanwei.platform.member.facade;

import com.alibaba.fastjson.JSONObject;

public interface IMemberUseFacade {

	// 返回用户信息
	public JSONObject getUserDetail(String userId);
	
	// 根据token返回用户信息
	public JSONObject getUserToken(String token);

}
