package com.yaozou.platform.spring.service.impl;/**
 * created by yaozou on 2018/6/8
 */

import com.yaozou.platform.spring.service.TestService;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

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
    public void insert(int type) throws Exception {
        switch (type){
            case 1 : transactionService.updatePublic("alipay",1); break;
            case 2 : transactionService.updatePublic("alipay",1); break;
            case 3 : transactionService.updateAToBForPrivate("alipay",1); break;
            case 4 : transactionService.updateAToBForPublic("alipay",1); break;
            case 5 : transactionService.updateNotThrowException("alipay",2); break;
            case 6 : transactionService.updateAToBForRuntimeException("alipay",1); break;
            case 7 : transactionService.updateAToBForSQLException("alipay",1); break;
        }

    }

    public void applicationContext(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(".xml");
        Object obj = applicationContext.getBean("");
    }
}
