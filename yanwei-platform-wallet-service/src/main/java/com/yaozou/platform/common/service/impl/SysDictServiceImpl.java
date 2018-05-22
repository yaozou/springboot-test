package com.yanwei.platform.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yanwei.platform.common.dao.SysDictMapper;
import com.yanwei.platform.common.domain.SysDictDO;
import com.yanwei.platform.common.service.SysDictService;

/**
 * 字典管理服务类
 * @author luojianhong
 * @version $Id: SysDictServiceImpl.java, v 0.1 2017年10月11日 下午3:22:11 luojianhong Exp $
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    @Cacheable(value = "sysDict", key = "#id + 'get'")
    public SysDictDO get(Long id) {
        return sysDictMapper.get(id);
    }

    @Override
    public List<SysDictDO> list(Map<String, Object> map) {
        return sysDictMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDictMapper.count(map);
    }

    @Override
    @CacheEvict(value = "sysDict", allEntries = true)
    public int save(SysDictDO sysDict) {
        return sysDictMapper.save(sysDict);
    }

    @Override
    @CacheEvict(value = "sysDict", key = "#sysDict.id + 'get'")
    public int update(SysDictDO sysDict) {
        return sysDictMapper.update(sysDict);
    }

    @Override
    @CacheEvict(value = "sysDict", key = "#id + 'get'")
    public int remove(Long id) {
        return sysDictMapper.remove(id);
    }

    @Override
    @CacheEvict(value = "sysDict", allEntries = true)
    public int batchRemove(Long[] ids) {
        return sysDictMapper.batchRemove(ids);
    }

    @Override
    @Cacheable(value = "sysDict", key = "'listType'")
    public List<SysDictDO> listType() {
        return sysDictMapper.listType();
    }

}
