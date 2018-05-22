package com.yanwei.platform.common.domain;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.Data;

/**
 * 
 * @author luojianhong
 * @version $Id: ScheduleJob.java, v 0.1 2017年10月10日 下午1:58:45 luojianhong Exp $
 */
@SuppressWarnings("serial")
@Data
public class ScheduleJob implements Serializable ,Job {

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";

	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 任务分组
	 */
	private String jobGroup;
	/**
	 * 任务状态 是否启动任务
	 */
	private String jobStatus;
	/**
	 * cron表达式
	 */
	private String cronExpression;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;
	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;

	/**
	 * Spring bean
	 */
	private String springBean;

	/**
	 * 任务调用的方法名
	 */
	private String methodName;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}

}