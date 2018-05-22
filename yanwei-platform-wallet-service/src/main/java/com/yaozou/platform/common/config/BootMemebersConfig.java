package com.yanwei.platform.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "boot")
@Data
public class BootMemebersConfig {
	// 上传路径
	private String uploadPath;
	// zbus
	private String brokerAddress;
	// 调用接口路径
	private String yanweiPath;
	// 接口调用
	private String yanweiApiPath;
	// 接口调用版本
	private String yanweiApiVersion;
	// app
	private String appDomain;
	// 商户转账前置通知
	private String merchantTramsferFrontBackUrl;
}
