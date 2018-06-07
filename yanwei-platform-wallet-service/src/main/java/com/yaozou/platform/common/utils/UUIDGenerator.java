package com.yaozou.platform.common.utils;

import java.util.UUID;

/**
 * uuid生成工具类
 * @author luojianhong
 * @version $Id: UUIDGenerator.java, v 0.1 2017年11月17日 下午1:52:39 luojianhong Exp $
 */

public class UUIDGenerator {

    /**
     * 工具类,不允许实例化
     */
    public UUIDGenerator() {

    }

    /**
     * 获得一个UUID,只包含数字与字母,不包含分隔符
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
               + s.substring(24);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number
     *            int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}