package com.yaozou.platform.spring;/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.member.domain.ApiOut;
import com.yaozou.platform.spring.service.TestService;
import com.yaozou.platform.spring.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TestService testService;

    @PostMapping(path = "/test")
    public ApiOut<Void> test(@RequestParam(name = "type", required = false, defaultValue = "4") Integer type){
        System.out.println("---type="+type);
        //transactionService.insert(type);
        testService.insert(type);
        return ApiOut.success();
    }

}
