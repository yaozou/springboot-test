package com.yanwei.platform.common.utils;

 
/**
 * 自定义异常
 * @author luojianhong
 * @version $Id: BDException.java, v 0.1 2017年10月10日 上午11:12:18 luojianhong Exp $
 */
public class BDException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public BDException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public BDException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public BDException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public BDException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
