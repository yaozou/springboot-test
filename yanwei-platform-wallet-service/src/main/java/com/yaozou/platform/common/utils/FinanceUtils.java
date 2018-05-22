package com.yanwei.platform.common.utils;

import java.math.BigDecimal;

/**
 * 金额转化
 * 
 * @author yaozou
 * @version $Id: FinanceUtils.java, v 0.1 2017年12月28日 下午4:19:53 yaozou Exp $
 */
public class FinanceUtils {
    
    /**
     * 分转元，保留两位小数
     * 
     * @param amount
     * @return
     * @description 
     * @author yaozou
     * @time 2017年12月28日 下午4:28:07
     */
    public static BigDecimal fenToYuan(Long amount) {
        BigDecimal yuan = new BigDecimal(amount).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        return yuan;
    }
}
