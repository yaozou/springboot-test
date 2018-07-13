package com.yaozou.platform.spring.service;/**
 * created by yaozou on 2018/6/7
 */

import java.sql.SQLException;
import java.util.List;

/**
 * 事务
 * @author yaozou
 * @create 2018-06-07 16:36
 **/
public interface TransactionService {
    List query(String bankCode);

    void updatePublic(String bankCode,int status);

    void updateAToBForPrivate(String bankCode,int status);

    void updateAToBForPublic(String bankCode,int status);

    void updateNotThrowException(String bankCode,int status);

    void updateAToBForSQLException(String bankCode,int status) throws SQLException;

    void updateAToBForRuntimeException(String bankCode,int status);
}
