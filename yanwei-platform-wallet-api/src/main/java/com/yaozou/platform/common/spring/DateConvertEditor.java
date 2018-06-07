package com.yaozou.platform.common.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 日期转化全局设置
 * @author luojianhong
 * @version $Id: DateConvertEditor.java, v 0.1 2017年7月26日 上午9:28:30 luojianhong Exp $
 */
public class DateConvertEditor implements Converter<String, Date> {

    private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat     = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String text) {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    return this.dateFormat.parse(text);
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    return (this.datetimeFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 21) {
                    text = text.replace(".0", "");
                    return (this.datetimeFormat.parse(text));
                } else {
                    throw new IllegalArgumentException(
                        "Could not parse date, date format is error ");
                }
            } catch (ParseException ex) {
                IllegalArgumentException iae = new IllegalArgumentException(
                    "Could not parse date: " + ex.getMessage());
                iae.initCause(ex);
                throw iae;
            }
        }
        return null;
    }
}
