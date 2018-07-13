package com.yaozou.platform.common.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaozou.platform.common.page.Pagination;
import com.yaozou.platform.common.repository.CustomRepository;
import com.yaozou.platform.common.repository.jdbc.Finder;
import com.yaozou.platform.common.repository.jdbc.JdbcDao;

/**
 * 通用实体操作类
 * @author luojianhong
 * @version $Id: CommonService.java, v 0.1 2017年7月13日 下午4:20:59 luojianhong Exp $
 */
@Service
@Transactional
public class CommonService {

    @Resource
    public JdbcDao          jdbcDao;

    @Resource
    public CustomRepository customRepository;

    /**
     * 转为sql操作
     * @param bean
     * @return
     */
    public <T> T saveSql(T bean) {
        try {
            jdbcDao.save(bean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bean;
    }

    /**
     * 转为sql处理
     * @param bean
     * @return
     */
    public <T> T updateSql(T bean) {
        try {
            jdbcDao.update(bean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bean;
    }

    /**
     *  转为sql处理
     * @param bean
     * @return
     */
    public <T> T deleteSql(T bean) {
        try {
            jdbcDao.delete(bean);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bean;
    }

   
 
    
    /**
     * 标准hibernate 处理
     * @param bean
     * @return
     */
    public <T> T update(T bean) {
        customRepository.update(bean);
        return bean;
    }

    /**
     * 标准hibernate 处理
     * @param bean
     * @return
     */
    public <T> T delete(T bean) {
        customRepository.delete(bean);
        return bean;
    }

    /**
     * 标准hibernate 处理
     * @param finder
     * @param pageNo
     * @param pageSize
     * @return
     */
    public <T> Pagination<T> find(Finder finder, int pageNo, int pageSize) {
        return customRepository.findBigData(finder, pageNo, pageSize);
    }

    /**
     *  标准hibernate 处理
     * @param finder
     * @return
     */
    public <T> List<T> findAll(Finder finder) {
        return customRepository.find(finder);
    }

    /**
     * 标准的ＳＱＬ分页查询
     * @param sql
     * @param pageNo
     * @param pageSize
     * @param clazz
     * @return
     */
    public <T> Pagination<T> findSql(String sql, int pageNo, int pageSize, Class<T> clazz) {
        return jdbcDao.getPage(sql, pageNo, pageSize, clazz);
    }

    /**
     * 带参数标准的ＳＱＬ查询
     * @param sql
     * @param parameters
     * @param clazz
     * @return
     */
    public <T> List<T> find(final String sql, Map<String, Object> parameters, Class<T> clazz) {
        return jdbcDao.find(sql, clazz, parameters);
    }

    /**
     * 带参数标准的ＳＱＬ分页查询
     * @param sql
     * @param pageNo
     * @param pageSize
     * @param parameters
     * @param clazz
     * @return
     */
    public <T> Pagination<T> findSql(String sql, int pageNo, int pageSize,
                                     Map<String, Object> parameters, Class<T> clazz) {

        return jdbcDao.getPage(sql, pageNo, pageSize, clazz);
    }

}