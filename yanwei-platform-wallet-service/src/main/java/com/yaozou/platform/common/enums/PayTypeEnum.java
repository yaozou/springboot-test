package com.yaozou.platform.common.enums;

public enum PayTypeEnum implements IEnumType{
	Aplipay(1,"支付宝支付"),
	Wetchpay(2,"微信支付"),
	Realnamepay(3,"实名付"),
	walletpay(4,"钱包支付");
	
	private final int code;
	private final String desc;
	PayTypeEnum(int code , String desc){
		this.code = code;
		this.desc = desc;
	}
	@Override
	public int code() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String desc() {
		// TODO Auto-generated method stub
		return desc;
	}
}
