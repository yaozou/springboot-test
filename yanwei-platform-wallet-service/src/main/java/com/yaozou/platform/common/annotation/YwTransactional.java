package com.yaozou.platform.common.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;
import java.sql.SQLException;

/**
 * created by yaozou on 2018/6/22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
public @interface YwTransactional {

}
