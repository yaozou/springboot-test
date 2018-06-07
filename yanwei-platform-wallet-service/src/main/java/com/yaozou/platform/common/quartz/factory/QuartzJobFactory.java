package com.yaozou.platform.common.quartz.factory;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yaozou.platform.common.domain.ScheduleJob;
import com.yaozou.platform.common.quartz.utils.TaskUtils;

 
/**
 *  计划任务执行处 无状态
 * @author luojianhong
 * @version $Id: QuartzJobFactory.java, v 0.1 2017年11月8日 下午3:49:24 luojianhong Exp $
 */
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);
	}
}