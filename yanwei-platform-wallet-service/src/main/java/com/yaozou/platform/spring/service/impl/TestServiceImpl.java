package com.yaozou.platform.spring.service.impl;/**
 * created by yaozou on 2018/6/8
 */

import com.yaozou.platform.spring.service.TestService;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yaozou
 * @create 2018-06-08 16:11
 **/
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TransactionService transactionService;

    @Override
    public void insert(int type) {
        switch (type){
            case 1 : transactionService.updatePublic("alipay",1); break;
            case 2 : transactionService.updatePublic("alipay",1); break;
            case 3 : transactionService.updateAToBForPrivate("alipay",1); break;
            case 4 : transactionService.updateAToBForPublic("alipay",1); break;
            case 5 : transactionService.updateNotThrowException("alipay",2); break;
        }

    }
}
