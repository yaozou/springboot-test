package com.yaozou.platform.common.utils;

import java.math.BigDecimal;

/**
 * 金额计算转化类
 * 
 * @author luojianhong
 * @version $Id: NumbersUtils.java, v 0.1 2017年11月29日 下午3:18:44 luojianhong Exp
 *          $
 */
public class NumbersUtils {

    /**
     * 系统计录的是分 返回为分单位
     * 
     * @param benefitId
     *            惠币数
     * @param scoreToRmb
     * @return
     */
    public static Integer processScorePrice(String benefitId, Integer scoreToRmb) {
        Double dfs = Double.parseDouble(benefitId) / scoreToRmb;
        BigDecimal b = new BigDecimal(dfs);
        double f = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        String s1 = String.valueOf(f * 100);
        String s2 = s1.substring(0, s1.indexOf("."));
        return Integer.parseInt(s2);
    }

    /**
     * 返回Double类型 的记录
     * @param benefitId
     * @param scoreToRmb
     * @return
     */
    public static Double processScorePriceDouble(String benefitId, Integer scoreToRmb) {
        Double dfs = Double.parseDouble(benefitId) / scoreToRmb;
        BigDecimal b = new BigDecimal(dfs);
        double f = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    /**
     * 计算积分
     * @param userNum
     * @param score
     * @return Double
     */
    public static Double processScore(Integer userNum, Integer score) {
        if(userNum==null||score==null)
        return 0.0;
        BigDecimal userNumBigDec = new BigDecimal(Double.toString(userNum));
        BigDecimal userScore = new BigDecimal(Double.toString(score));
        return userScore.divide(userNumBigDec, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
    public static Double processScore(Long num1, Long num2) {
        if(num1==null||num2==null)
        return 0.0;
        BigDecimal userNumBigDec = new BigDecimal(Double.toString(num1));
        BigDecimal userScore = new BigDecimal(Double.toString(num2));
        return userScore.divide(userNumBigDec, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /***
     * 计算两个Double数保留，两位小数
     */
    /**
     * 计算积分
     * @param userNum
     * @param score
     * @return Double
     */
    public static Double processDouble(Double num1, Integer num2) {
        BigDecimal num1BigDecimal = new BigDecimal(Double.toString(num1));
        BigDecimal num2bigDecimal = new BigDecimal(Double.toString(num2));
        return num1BigDecimal.divide(num2bigDecimal, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
    /**
     * 计算百分比
     * @param userNum
     * @param score
     * @return Double
     */
    public static Double processDouble(Long num1, Double num2) {
        BigDecimal num1BigDecimal = new BigDecimal(Double.toString(num1));
        BigDecimal num2bigDecimal = new BigDecimal(num2);
        return num1BigDecimal.divide(num2bigDecimal, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /***
     * 计算两个Double数保留，两位小数
     */
    /**
     * 计算积分
     * @param userNum
     * @param score
     * @return Double
     */
    public static Double processDouble(Integer num1, Integer num2) {
        BigDecimal num1BigDecimal = new BigDecimal(Double.toString(num1));
        BigDecimal num2bigDecimal = new BigDecimal(Double.toString(num2));
        return num1BigDecimal.divide(num2bigDecimal, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 计算占比
     * @param num1
     * @param num2
     * @return
     */
    public static Double processDouble(Double num1, Double num2) {
        BigDecimal num1BigDecimal = new BigDecimal(Double.toString(num1));
        BigDecimal num2bigDecimal = new BigDecimal(Double.toString(num2));
        return num1BigDecimal.divide(num2bigDecimal, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    

    /**
     * 商品价格范围
     * 根据商品价格范围取等级送相应的惠币数
     * @param productPrice
     * 
     * @return 1-49 5 10 15 25 
     *         50-199 10 25 30 50 
     *         200-999 20 30 50 100 
     *         1000以上 30 50 100 150
     */
    public static Integer getProductPriceScore(Double productPrice, Integer gradeId) {
        int i = 0;

        if (productPrice > 1 && productPrice < 50) {
            i = 0;
        } else if (productPrice > 50 && productPrice < 200) {
            i = 1;
        } else if (productPrice > 200 && productPrice < 1000) {
            i = 2;
        } else if (productPrice > 1000) {
            i = 3;
        }

        int m[][] = { { 1, 5, 10, 15, 25 }, { 2, 10, 25, 30, 50 }, { 3, 20, 30, 50, 100 },
                { 4, 30, 50, 100, 150 } };

        return m[i][gradeId];
    }

}
