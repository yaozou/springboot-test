package com.yaozou.platform.common.domain;

import java.io.Serializable;
import lombok.Data;


/**
 * 错误字段记录
 * @author luojianhong
 * @version $Id: FieldError.java, v 0.1 2017年11月23日 上午8:58:13 luojianhong Exp $
 */
@Data
public class FieldError implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4678722122072909604L;
	//字段名称
	private String name;
	//错误原因
    private String message;
}