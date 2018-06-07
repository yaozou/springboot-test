package com.yaozou.platform.spring.service.impl;/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.spring.repository.basedata.BdBankCodeMapper;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private BdBankCodeMapper bdBankCodeMapper;

    @Override
    public void insert(int type) {
        switch (type){
            case 1 : updatePrivate("alipay",1); break;
            case 2 : updatePublic("alipay",1); break;
            case 3 : updateAToBForPrivate("alipay",1); break;
            case 4 : updateAToBForPublic("alipay",1); break;
            case 5 : updateAToBForPublic("alipay",2); break;
        }

    }

    private List query(String bankCode){
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode",bankCode);
        return bdBankCodeMapper.list(map);
    }

    @Transactional
    private void updatePrivate(String bankCode,int status){
        updateNotThrowException(bankCode,status);
        throw new RuntimeException();
    }

    @Transactional
    void updatePublic(String bankCode,int status){
        updateNotThrowException(bankCode,status);
        throw new RuntimeException();
    }

    private void updateAToBForPrivate(String bankCode,int status){
        updatePrivate(bankCode,status);
    }

    private void updateAToBForPublic(String bankCode,int status){
        updatePublic(bankCode,status);
    }

    private void updateNotThrowException(String bankCode,int status){
        Map<String, Object> map = new HashMap<>();
        map.put("bankCode",bankCode);
        map.put("status",status);
        bdBankCodeMapper.update(map);
    }
}
