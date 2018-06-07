package com.yaozou.platform.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

import com.yaozou.platform.common.enums.Env;

/**
 * 前三位数字规则
 * 第一位：envId,测试环境3开头，生产环境1开头
 * 第二位：3为订单相关，5为支付相关
 * 第三位：业务序号
 *
 * @author zhengrj
 * @since 2017-10-09
 */
public class GenerateCodeUtils {
    //第一位：环境标识
    private static String envId;

    //钱包相关
    private static String depositAgentCollectId = "61";
    private static String depositAgentPayId = "62";
    
    private static String rechargeOrderNo = "63";
    private static String withdrawalOrderNo = "64";
    
    private static String merchantTramsferOrderNo = "65";
    
    @Value("${spring.profiles.active}")
    private static String  env;
    static {
        envId = Env.Prod.name().equalsIgnoreCase(env) ? "1" : "3";
    }

    /**
     * 生成钱包充值代收单号.
     *
     * @return
     */
    public static Long generateDepositAgentCollectId() {
        return Long.valueOf(envId + depositAgentCollectId + getDateTime() + generateRandom());
    }

    /**
     * 生成钱包充值代付单号.
     *
     * @return
     */
    public static Long generateDepositAgentPayId() {
        return Long.valueOf(envId + depositAgentPayId + getDateTime() + generateRandom());
    }
    
    /**
     * 充值订单号
     * rechargeOrderNo
     */
    public static String generateRechargeOrderNo() {
        return envId + rechargeOrderNo + getDateTime() + generateRandom();
    }
    
    /**
     * 提现订单号
     * withdrawalOrderNo
     */
    public static String generateWithdrawalOrderNo() {
        return envId + withdrawalOrderNo + getDateTime() + generateRandom();
    }
    
    
    /**
     * 商户转账订单号
     */
    public static String generateMerchantTramsferOrderNo() {
        return envId + merchantTramsferOrderNo + getDateTime() + generateRandom();
    }

    /**
     * 生成当前时间的字符串格式
     *
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        return df.format(new Date());

    }

    /**
     * 生成4位随机数
     *
     * @return
     */
    public static String generateRandom() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 4; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 生成8位随机数
     *
     * @return
     */
    public static String generateRandom8() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 8; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

   

}
