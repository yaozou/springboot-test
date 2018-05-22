package com.yanwei.platform.common.spring;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * 字符串取消空串
 * @author luojianhong
 * @version $Id: StringTrimmerEditor.java, v 0.1 2017年7月26日 下午2:44:24 luojianhong Exp $
 */
public class StringConverterTrimmerEditor implements Converter<String, String> {

    @Override
    public String convert(String text) {
        if (StringUtils.hasText(text)) {
            return text.trim();
        }
        return null;
    }

}
