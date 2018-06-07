package com.yaozou.platform.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/** 
 * @Title: fmtMicrometer 
 * @Description: 格式化数字为千分位 
 * @param text 
 * @return    设定文件 
 * @return String    返回类型 
 */  
public class FmtMicrometer {
	public static String fmtMicrometer(BigDecimal money) {  
		if (money == null) {
			money = BigDecimal.ZERO;
		}
		String text = money.toString();
        DecimalFormat df = new DecimalFormat("###,##0.00");         
        double number = 0.0;  
        try {  
            number = Double.parseDouble(text);  
        } catch (Exception e) {  
            number = 0.0;  
        }  
        return df.format(number); 
    } 
	
	public static String fmtMicrometer(String text) {  
        DecimalFormat df = new DecimalFormat("###,##0.00");         
        double number = 0.0;  
        try {  
            number = Double.parseDouble(text);  
        } catch (Exception e) {  
            number = 0.0;  
        }  
        return df.format(number); 
    }
	
	public static BigDecimal fmtAmt(BigDecimal money){
		DecimalFormat df = new DecimalFormat("#0.00"); 
		String text = money.toString();       
        double number = 0.0;  
        try {  
            number = Double.parseDouble(text);  
        } catch (Exception e) {  
            number = 0.0;  
        }  
        return new BigDecimal(df.format(number)); 
	}
	public static void main(String[] args) {
		System.out.println(FmtMicrometer.fmtMicrometer(new BigDecimal(10000000)));
		System.out.println(FmtMicrometer.fmtAmt(new BigDecimal(100.0)));
	}
}
