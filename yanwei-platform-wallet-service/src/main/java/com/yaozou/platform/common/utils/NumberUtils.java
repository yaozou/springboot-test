package com.yanwei.platform.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chuan.jiang
 * @since 2017-07-15
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 用于生成数字转换用到的字符，随机打乱不让随便猜出来
     * ===* 请不要随意修改下列字符列表 *===
     */
    private final static char[] SYMBOLS       = { 'b', 'P', 'x', '0', 's', 'X', 'd', 'E', 'q', 'W',
            'Z', 'G', 'I', 'g', 'B', 'w', 'i', '4', 'R', 'Q', 'y', '-', 'K', 'j', 'v', '8', '_',
            'e', 'H', 'c', '9', '2', 'n', 'u', 'Y', 'a', 't', 'J', 'C', '(', 'M', 'k', 'F', 'r',
            'A', 'p', '1', 'l', 'T', 'D', 'L', 'N', 'S', 'U', '7', 'z', 'V', '5', '6', 'f', 'm',
            'h', ')', '3'                    };

    // 不足指定位数，补齐字符
    private static final char   PAD_CHAR      = 'o';
    private static final int    DEFAULT_RADIX = 64;

    /**
     * Null == value
     *
     * @param value 需要检查的数字
     * @return 检查的数据为 NULL
     */
    public static boolean isNull(Number value) {
        return null == value;
    }

    /**
     * Null != value
     *
     * @param value 需要检查的数字
     * @return 检查的数据不是 NULL
     */
    public static boolean isNotNull(Number value) {
        return null != value;
    }

    /**
     * Null != value && value > 0
     *
     * @param value
     * @return
     */
    public static boolean isPositive(Number value) {
        if (isNotNull(value)) {
            if (value instanceof Integer) {
                return value.intValue() > 0;
            } else if (value instanceof Long) {
                return value.longValue() > 0;
            } else if (value instanceof Byte) {
                return value.byteValue() > 0;
            } else if (value instanceof Double) {
                return value.doubleValue() > 0;
            } else if (value instanceof Float) {
                return value.floatValue() > 0;
            } else if (value instanceof Short) {
                return value.shortValue() > 0;
            }
        }
        return false;
    }

    /**
     * Null == value || value < 1
     *
     * @param value
     * @return
     */
    public static boolean isNotPositive(Number value) {
        if (isNull(value)) {
            return true;
        }
        if (value instanceof Integer) {
            return value.intValue() < 1;
        } else if (value instanceof Long) {
            return value.longValue() < 1;
        } else if (value instanceof Byte) {
            return value.byteValue() < 1;
        } else if (value instanceof Double) {
            return value.doubleValue() < 1;
        } else if (value instanceof Float) {
            return value.floatValue() < 1;
        } else if (value instanceof Short) {
            return value.shortValue() < 1;
        }
        return true;
    }

    /**
     * 检查两个数字是不是完全不同，
     * 主要用于数据更新时，对两个字段进行检查
     *
     * @param num1 Number
     * @param num2 Number
     * @return true: 两个字符不同
     */
    public static boolean isNotSame(Number num1, Number num2) {
        if (null == num1 || null == num2) {
            return false;
        } else if (num1.equals(num2)) {
            return false;
        }
        return true;
    }

    /**
     * 将数字转换成64进制
     *
     * @param number 数字
     * @return 64进制数字
     */
    public static String compress(long number) {
        return compress(number, 6);
    }

    /**
     * 将数字转换成64进制
     *
     * @param number 数字
     * @return 64进制数字
     */
    public static String compress6(long number) {
        return compress(number, 6);
    }

    /**
     * 将数字转换成64进制
     *
     * @param number 数字
     * @param length 长度
     * @return 64进制数字
     */
    public static String compress(long number, int length) {
        char[] buf = new char[DEFAULT_RADIX];
        int charPos = DEFAULT_RADIX;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = SYMBOLS[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);

        String results = new String(buf, charPos, (DEFAULT_RADIX - charPos));
        results = getRedundancyCode(results) + results;

        if (0 == length || Integer.MAX_VALUE == length) {
            return results;
        }
        return results.length() < length ? StringUtils.leftPad(results, length, PAD_CHAR) : results;
    }

    /**
     * 将64进制数转换成正常数字
     *
     * @param compressed 64进制数
     * @return 正常数字
     */
    public static long uncompress(String compressed) {
        compressed = compressed.trim();
        int padIndex = compressed.lastIndexOf(PAD_CHAR);
        char checkCode;
        try {
            if (padIndex < 0) {
                checkCode = compressed.charAt(0);
                compressed = compressed.substring(1);
            } else {
                checkCode = compressed.charAt(padIndex + 1);
                compressed = compressed.substring(padIndex + 2);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("compressed number is not correct");
        }

        if (checkCode != getRedundancyCode(compressed)) {
            throw new NumberFormatException("check code does not match");
        }

        long result = 0;
        for (int i = compressed.length() - 1; i >= 0; i--) {
            for (int j = 0; j < SYMBOLS.length; j++) {
                if (compressed.charAt(i) == SYMBOLS[j]) {
                    result += ((long) j) << 6 * (compressed.length() - 1 - i);
                }
            }
        }
        return result;
    }

    /**
     * 获取相反数
     *
     * @param number
     * @return
     */
    public static int opposite(int number) {
        return -1 * number;
    }

    /**
     * 生成冗余验证码
     *
     * @param compressed 压缩过的数字
     * @return 验证码的值
     */
    private static char getRedundancyCode(String compressed) {
        compressed = compressed.toLowerCase();
        int sum = 0;
        for (int i = 0; i < compressed.length(); i++) {
            sum += compressed.charAt(i);
        }
        return SYMBOLS[sum % SYMBOLS.length];
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将加码后的数字字符串转化为非数字符串
     * @param str
     * @return
     */
    public static String getConvertNumStr(String str) {
        if (isNumeric(str))
            return compress(Long.parseLong(str));
        else
            return str;
    }
    
    
    /**
     * 将加码后的字符串转化为数字
     * @param str
     * @return
     */
    public static Long getConvertStrNum(String str) {
        if (!isNumeric(str))
            return uncompress(str);
        else
            return Long.parseLong(str);
    }

    /**
     * 转化为字符串后为数字符串
     * @param str
     * @return
     */
    public static String getConvertStrNumtoStr(String str) {
        if (str != null)
            return String.valueOf(getConvertStrNum(str));
        else
            return "";
    }
    

    public static void main(String args[]) {

        System.out.println(getConvertStrNum("oox(h2"));

    }
}
