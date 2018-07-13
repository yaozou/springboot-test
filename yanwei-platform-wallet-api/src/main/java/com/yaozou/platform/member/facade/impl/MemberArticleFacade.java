package com.yaozou.platform.member.facade.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yaozou.platform.common.annotation.Log;
import com.yaozou.platform.common.config.BootMemebersConfig;
import com.yaozou.platform.member.facade.IMemberArticleFacade;


/**
 *  文章调用外部接口实现
 * @author luojh
 * @version $Id: MemberArticleFacade.java, v 0.1 2017年12月6日 下午11:25:18 luojh Exp $
 */
@Component
public class MemberArticleFacade implements IMemberArticleFacade {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	BootMemebersConfig bootMemebersConfig;

	@Override
	@Log("外部接口文章详细信息查询接口")
    //article_id:文章ID content:文章内容
	public JSONObject getArticleDetail(String sId) {
		String url = "/articleControl/articleContent?article_id={article_id}";
		String jsonStr = restTemplate.getForEntity(
				bootMemebersConfig.getYanweiPath() + url, String.class, sId)
				.getBody();
		return (JSONObject) JSONObject.parse(jsonStr);
	}

	@Override
	@Log("外部接口批量查询文章详细信息查询接口")
	// ids=”119230,20119231” 
	//返回字段title: 文章标题source: 文章来源consumer_name：作家名称
	public JSONArray getArticleDetailIds(String ids) {
		String url = "/articleControl/findByIds?ids={ids}";
		String jsonStr = restTemplate.getForEntity(bootMemebersConfig.getYanweiPath() + url, String.class, ids).getBody();
		JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
		if (jsonObject != null)
			return (JSONArray) jsonObject.get("data");
		else
			return null;
	}
	
	
	@Override
	@Log("外部接口查询文章详细信息查询接口")
	// ids=”119230,20119231” 
	//返回字段title: 文章标题source: 文章来源consumer_name：作家名称
	public JSONObject getArticleDetailId(String ids) {
		String url = "/articleControl/findByIds?ids={ids}";
		String jsonStr = restTemplate.getForEntity(bootMemebersConfig.getYanweiPath() + url, String.class, ids).getBody();
		JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
		if (jsonObject != null)
		{
			JSONArray jsonArray=(JSONArray)jsonObject.get("data");
		    if(jsonArray!=null&&jsonArray.size()>0)
			return (JSONObject) jsonArray.get(0);
		}
		return null;
	}
	
	   public static JSONObject testfindByUserIdScore(String ids) {
	        String url = "http://www.yanwei365.com:7011/articleControl/findByIds?ids={ids}";
	        RestTemplate restTemplate=new RestTemplate();
	        String jsonStr = restTemplate.getForEntity( url, String.class, ids).getBody();
	        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
	        if (jsonObject != null)
	        {
	            JSONArray jsonArray=(JSONArray)jsonObject.get("data");
	            if(jsonArray!=null&&jsonArray.size()>0)
	            return (JSONObject) jsonArray.get(0);
	        }
	        return null;
	    }
 
 
 public static void main(String args[]) {
	 System.out.println(testfindByUserIdScore("224606"));;
 }


}
