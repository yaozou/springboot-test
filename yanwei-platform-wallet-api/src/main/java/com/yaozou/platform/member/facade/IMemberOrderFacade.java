package com.yanwei.platform.member.facade;

import com.alibaba.fastjson.JSONObject;

 

/**
 * 订单外部接口调用
 * @author luojh
 */
public interface IMemberOrderFacade {
	
	/**
	 * 订单详细信息查询接口
	 * @param sId
	 * @return
	 */
	public JSONObject getOrderDetail(String sId);
}
