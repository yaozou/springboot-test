package com.yanwei.platform.common.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 日志记录表
 * 
 * @author luojianhong
 * @version $Id: SysLogDO.java, v 0.1 2017年11月13日 下午12:28:53 luojianhong Exp $
 */
@Data
@Entity
@Table(name = "acc_sys_log")
public class SysLogDO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 调用者ID
	private String userId;
	// 调用者名称
	private String userName;
	//执行时间
	private Long time;
    // 接口名称
	private String operation;
	// 调用时间
	private Date createTime;
	// 调用方法
	private String method;
	// 传入参数
	private String params;
	// 返回结果
	private String result;
    //请求者的IP
	private String ip;
}