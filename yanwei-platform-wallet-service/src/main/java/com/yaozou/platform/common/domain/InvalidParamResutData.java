package com.yanwei.platform.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

 
/**
 * controller调用异常信息描述模型
 * @author luojianhong
 * @version $Id: InvalidParamResutData.java, v 0.1 2017年7月10日 下午5:22:50 luojianhong Exp $
 */
@Data
@AllArgsConstructor
public class InvalidParamResutData {

	private String paramName;

	private String errorMessage;

}
