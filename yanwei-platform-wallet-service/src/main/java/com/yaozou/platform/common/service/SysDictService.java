package com.yanwei.platform.common.service;

import com.yanwei.platform.common.domain.SysDictDO;
import java.util.List;
import java.util.Map;

 
/**
 * 字典表
 * @author luojianhong
 * @version $Id: SysDictService.java, v 0.1 2017年10月10日 上午10:53:43 luojianhong Exp $
 */
public interface SysDictService {
	
	SysDictDO get(Long id);
	
	List<SysDictDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysDictDO sysDict);
	
	int update(SysDictDO sysDict);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<SysDictDO> listType();
}
