package com.yaozou.platform.spring.service.impl;/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.common.annotation.YwTransactional;
import com.yaozou.platform.spring.repository.basedata.BdBankCodeMapper;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yaozou
 * @create 2018-06-07 16:30
 **/
@Service
@YwTransactional
@Transactional
@CacheConfig(cacheNames = "bd_bank_code")
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private BdBankCodeMapper bdBankCodeMapper;

    @Override
    @Cacheable(key ="#p0")
    public List query(String bankCode){
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode",bankCode);
        return bdBankCodeMapper.list(map);
    }

    @Transactional
    void updatePrivate(String bankCode,int status){
        updateNotThrowException(bankCode,status);
        throw new RuntimeException();
    }

    @Override
    public void updatePublic(String bankCode,int status){
        updateNotThrowException(bankCode,status);
    }

    @Override
    public void updateAToBForPrivate(String bankCode,int status){
        updatePrivate(bankCode,status);
    }

    @Override
    public void updateAToBForPublic(String bankCode,int status){
        updatePublic(bankCode,status);
        throw new RuntimeException();
    }

    @Override
    public void updateAToBForSQLException(String bankCode,int status) throws SQLException {
        updateNotThrowException(bankCode,status);
        throw new SQLException();
    }

    @Override
    public void updateAToBForRuntimeException(String bankCode,int status){
        updateNotThrowException(bankCode,status);
        throw new RuntimeException();
    }

    @Override
    @CachePut(key = "#p0")
    public void updateNotThrowException(String bankCode,int status){
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode",bankCode);
        map.put("status",status);
        bdBankCodeMapper.update(map);
    }

    @CacheEvict(key = "#p0")
    public void delete(String bankCode){

    }
}
