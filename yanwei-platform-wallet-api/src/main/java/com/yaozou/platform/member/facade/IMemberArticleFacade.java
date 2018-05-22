package com.yanwei.platform.member.facade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


//文章详细信息查询接口
public interface IMemberArticleFacade {
	
	public JSONObject getArticleDetail(String sId);
	
	/**
	 * 批量查询接口
	 * @param ids
	 * @return 返回一组
	 */
	public JSONArray getArticleDetailIds(String ids);
	
	
	/**
	 * @param ids
	 * @return 返回单个的
	 */
	public JSONObject getArticleDetailId(String ids);

}
