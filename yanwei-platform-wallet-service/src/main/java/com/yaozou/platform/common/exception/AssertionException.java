package com.yanwei.platform.common.exception;

/**
 * 断言异常
 * 
 */
public class AssertionException extends SystemException {
	private static final long serialVersionUID = 1L;
	/** 默认错误码. */
	private static final String DEFAULT_ERROR_CODE = "ASSERTION_FAIL";

	public AssertionException(String errorMessage) {
		super(DEFAULT_ERROR_CODE, errorMessage);
	}
}