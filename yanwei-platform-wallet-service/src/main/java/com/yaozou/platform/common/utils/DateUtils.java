package com.yaozou.platform.common.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 日期处理
 * 
 * @author luojianhong
 * @version $Id: DateUtils.java, v 0.1 2017年10月10日 上午11:12:43 luojianhong Exp $
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}
	
	public static String formatTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }
    
	public static Date parseDateTime(String dateString) throws Exception{
		return parse(dateString, new SimpleDateFormat(DATE_TIME_PATTERN));
	}
	
	public static Date parse(String dateStr, SimpleDateFormat simpleDateFormat) throws Exception {
		return simpleDateFormat.parse(dateStr);
	}

	/**
	 * 获取当前日期
	 * 
	 * @return "yyyy-MM-dd"
	 */
	public static String getNow() {
		return format(new Date(), DATE_PATTERN);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getNowTime() {
		return format(new Date(), DATE_TIME_PATTERN);
	}

	public static Date formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
		String dateString = formatter.format(date);
		ParsePosition pos = new ParsePosition(8);
		Date RDate = formatter.parse(dateString, pos);
		return RDate;
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 得到当前日期推后1个月的日期
	 * 
	 * @author luojianhong
	 * @return String
	 */
	public static Date addMonth(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		date = (Date) c.getTime();
		return date;
	}
	
	   public static Date addMonth(String dateStr, int months) {
	        @SuppressWarnings("deprecation")
            Date date = new Date(dateStr);
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.MONTH, months);
	        date = (Date) c.getTime();
	        return date;
	    }
	   
	   public static Date addHours(Date date, int hours) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR_OF_DAY, hours);
			date = (Date) c.getTime();
			return date;
		}
	 /**
     * 得到当前日期 加，域者减天数
     * @author luojianhong
     * @return String
     */
    public static Date addDays( int days) {
        Date date=new Date();
        return addDays(date,days);
    }

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		date = (Date) c.getTime();
		return date;
	}
    
    /**
     * 当前日期，加域者减 日期时间
     * @param days
     * @return
     */
    public static String getNowAddDay(int days) {
        return format(addDays(days), DATE_PATTERN);
    }
    

	public static Date addNowMonth(int months) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		date = (Date) c.getTime();
		return date;
	}

	/**
	 * 得到当前日期推后1个月的日期
	 * 
	 * @author luojianhong
	 * @return String
	 */
	public static Date addYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		date = (Date) c.getTime();
		return date;
	}

	public static Date addNowYear(int year) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		date = (Date) c.getTime();
		return date;
	}

	/**
	 * 永久
	 * 
	 * @return
	 */
	public static Date addNeverYear() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 100);
		date = (Date) c.getTime();
		return date;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}
     /*
      * 当前年
      */
	public static int getNowYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.YEAR);
	}
	/**
	 * 明年
	 * @return
	 */
	public static int getNextYear() {
		Calendar d = Calendar.getInstance();
		return d.get(Calendar.YEAR)+1;
	}

	/**
	 * 过期时间
	 * 明年的最后一天
	 * @return
	 */
	public static Date addYearLast() {
		return getYearLast(getNextYear());
	}
	
	
	/**
	 * 返回两个日期这间的数据
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> getBetweenDates(Date start, Date end) {
	    List<String> result = new ArrayList<String>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(DateUtils.format(tempStart.getTime()));
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}



}
