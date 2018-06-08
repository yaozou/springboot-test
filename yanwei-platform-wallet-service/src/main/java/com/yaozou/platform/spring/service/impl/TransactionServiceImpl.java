package com.yaozou.platform.spring.service.impl;/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.spring.repository.basedata.BdBankCodeMapper;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yaozou
 * @create 2018-06-07 16:30
 **/
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private BdBankCodeMapper bdBankCodeMapper;

    @Override
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
        throw new RuntimeException();
    }

    @Override
    public void updateAToBForPrivate(String bankCode,int status){
        updatePrivate(bankCode,status);
    }

    @Override
    public void updateAToBForPublic(String bankCode,int status){
        updatePublic(bankCode,status);
    }

    @Override
    public void updateNotThrowException(String bankCode,int status){
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode",bankCode);
        map.put("status",status);
        bdBankCodeMapper.update(map);
    }
}
