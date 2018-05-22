package com.yanwei.platform.member.facade;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 店铺信息查询接口
 * @author luojh
 */
public interface IMemberShopFacade {
	
	public JSONObject getShopDetail(String sId);
	
	public JSONObject getMerchantDetail(String token);
	
}
