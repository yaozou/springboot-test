package com.yanwei.platform.common.manager.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.yanwei.platform.common.constant.Constants;

import com.yanwei.platform.common.domain.TokenModel;
import com.yanwei.platform.common.interceptor.AuthorizationInterceptor;
import com.yanwei.platform.common.manager.TokenManager;
import com.yanwei.platform.member.facade.IMemberShopFacade;
import com.yanwei.platform.member.facade.IMemberUseFacade;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 存储和验证token的实现类
 * 
 * @author luojianhong
 * @version $Id: CacheTokenManager.java, v 0.1 2017年11月15日 下午3:35:17 luojianhong
 *          Exp $
 */
@Component
@Slf4j
public class CacheTokenManager implements TokenManager {

	public static final String       MEMBER_USERS_NAME = "users";

	@Autowired
	public IMemberUseFacade          memberUseFacade;

	@Autowired
	public IMemberShopFacade         memberShopFacade;



	/**
	 * @see com.yanwei.platform.common.manager.TokenManager#createToken(long)
	 */
	public TokenModel createToken(String userId, String userName) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		TokenModel model = new TokenModel(userId, userName, token,
				String.valueOf(Constants.TOKEN_EXPIRES_SECONDS));
		return model;
	}

	/**
	 * 通过token获取用户信息
	 * 
	 * @see com.yanwei.platform.common.manager.TokenManager#getToken(java.lang.String)
	 */
	@Cacheable(value = MEMBER_USERS_NAME, key = "#token + 'getToken'")
	public TokenModel getToken(String token) {
		try{
			JSONObject memberUserExtend = initMemberUserExtend(token);
			log.info("根据token获取的信息:"+memberUserExtend.toString());
			if (memberUserExtend != null) {
				TokenModel tokenModel = new TokenModel(memberUserExtend.getString("sid"),
						memberUserExtend.getString("loginName"), token);

				tokenModel.setUserType(memberUserExtend.getString("userType"));
				//通过token取出用户的 商家ID 跟店铺ID
				JSONObject jsonObject = memberShopFacade.getMerchantDetail(token);
				log.info("根据token获取商家ID的信息:"+jsonObject);
				if (jsonObject != null) {
					if (StringUtils.isNotBlank(jsonObject.getString("shopSid")))
						tokenModel.setShopSid(jsonObject.getString("shopSid"));
					if (StringUtils.isNotBlank(jsonObject.getString("merchantSid")))
						tokenModel.setMerchantSid(jsonObject.getString("merchantSid"));
				}
				return tokenModel;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		return null;
	}

	/**
	 * 较验token有效性
	 * 
	 * @see com.yanwei.platform.common.manager.TokenManager#checkToken(com.yanwei.platform.common.domain.TokenModel)
	 */
	public boolean checkToken(String token) {
		TokenModel tokenModel = getToken(token);
		if (tokenModel.getUserId().equals("1")) {
			// 系统测试用户占充许访问
			return false;
		}
		JSONObject jsonObject = memberUseFacade.getUserToken(token);

		if (StringUtils.isBlank(token) || jsonObject == null) {
			return false;
		}

		if (jsonObject.getString("state") == null
				|| !jsonObject.getString("state").equals("SUCCESS"))
			return false;

		return true;
	}

	/**
	 * @see com.yanwei.platform.common.manager.TokenManager#deleteToken(long)
	 */
	@CacheEvict(value = MEMBER_USERS_NAME, key = "#token + 'getToken'")
	public void deleteToken(String token) {
		// 清除没有用的token信息
	}

	// 查不到的用户信息，根据用户ID，查询并初始化到数据库中
	private JSONObject initMemberUserExtend(String token) {
		// 根据用户ID查找相应的用户信息，增加到用户表中
		JSONObject jsonObject = memberUseFacade.getUserToken(token);
		if (jsonObject == null || jsonObject.getString("state") == null
				|| !jsonObject.getString("state").equals("SUCCESS"))
			return null;
		JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
		if (jsonObjectData == null || jsonObjectData.getString("sid") == null)
			return null;
		JSONObject jsonObjectUserInfo = memberUseFacade.getUserDetail(jsonObjectData
				.getString("sid"));
		return jsonObjectUserInfo;
	}

}
