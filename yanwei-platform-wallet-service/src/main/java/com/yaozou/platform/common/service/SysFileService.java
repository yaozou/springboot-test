package com.yanwei.platform.common.service;

import com.yanwei.platform.common.domain.SysFileDO;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * @author luojianhong
 * @version $Id: SysFileService.java, v 0.1 2017年10月10日 上午10:54:04 luojianhong Exp $
 */
public interface SysFileService {
	
	SysFileDO get(Long id);
	
	 public SysFileDO getUrl(String url);
	
	List<SysFileDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysFileDO sysFile);
	
	int update(SysFileDO sysFile);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
