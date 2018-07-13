package com.yaozou.platform.spring.repository.basedata;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * 银行卡信息
 * 
 * @author yaozou
 * @version $Id: BdBankCodeMapper.java, v 0.1 2017年12月28日 下午1:55:30 yaozou Exp $
 */
public interface BdBankCodeMapper {
	/**
	 * 列表
	 * @param map
	 * @return
	 */
	List<Map> list(Map<String, Object> map);

	/**
	 * 修改
	 * @param map
	 */
	void update(Map<String, Object> map);
}
