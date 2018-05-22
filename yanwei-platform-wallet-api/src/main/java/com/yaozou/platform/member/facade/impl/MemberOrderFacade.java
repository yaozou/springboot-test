package com.yanwei.platform.member.facade.impl;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yanwei.platform.common.config.BootMemebersConfig;
import com.yanwei.platform.member.facade.IMemberOrderFacade;

/**
 * 订单信息查询接口
 * @author luojh
 * @version $Id: MemberOrderFacade.java, v 0.1 2017年12月9日 下午9:50:44 luojh Exp $
 */
@Component
public class MemberOrderFacade implements IMemberOrderFacade {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	BootMemebersConfig bootMemebersConfig;

	@Override
	public JSONObject getOrderDetail(String sId) {
		String url = "/product/cloud/order/detail/{sid}";
		String jsonStr = restTemplate.getForEntity(bootMemebersConfig.getYanweiPath() + url, String.class,	sId).getBody();
		return (JSONObject) JSONObject.parse(jsonStr);
	}

}
