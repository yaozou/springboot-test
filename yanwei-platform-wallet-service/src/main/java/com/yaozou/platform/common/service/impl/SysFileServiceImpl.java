package com.yaozou.platform.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yaozou.platform.common.dao.SysFileMapper;
import com.yaozou.platform.common.domain.SysFileDO;
import com.yaozou.platform.common.service.SysFileService;



@Service
public class SysFileServiceImpl implements SysFileService {
	@Autowired
	private SysFileMapper sysFileMapper;
	
	@Override
	public SysFileDO get(Long id){
		return sysFileMapper.get(id);
	}
	
	@Override
    public SysFileDO getUrl(String url){
        return sysFileMapper.getUrl(url);
    }
	
	@Override
	public List<SysFileDO> list(Map<String, Object> map){
		return sysFileMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sysFileMapper.count(map);
	}
	
	@Override
	public int save(SysFileDO sysFile){
		return sysFileMapper.save(sysFile);
	}
	
	@Override
	public int update(SysFileDO sysFile){
		return sysFileMapper.update(sysFile);
	}
	
	@Override
	public int remove(Long id){
		return sysFileMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sysFileMapper.batchRemove(ids);
	}
	
}
