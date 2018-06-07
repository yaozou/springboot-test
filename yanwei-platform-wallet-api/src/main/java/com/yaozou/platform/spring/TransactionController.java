package com.yaozou.platform.spring;/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.member.domain.ApiOut;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务
 * @author yaozou
 * @create 2018-06-07 16:15
 **/
@RestController
@RequestMapping("/spring/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(path = "/test")
    public void test(){
        transactionService.insert();
    }

}
