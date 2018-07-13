package com.yaozou.platform.common.utils;

import com.yaozou.platform.common.domain.ScheduleJob;
import com.yaozou.platform.common.domain.TaskScheduleJobDO;

/**
 * 定时任务转换类
 * @author luojianhong
 * @version $Id: ScheduleJobUtils.java, v 0.1 2017年10月10日 上午11:18:36 luojianhong Exp $
 */
public class ScheduleJobUtils {
	public static ScheduleJob entityToData(TaskScheduleJobDO scheduleJobEntity) {
		ScheduleJob scheduleJob = new ScheduleJob();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
		return scheduleJob;
	}
}