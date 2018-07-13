package com.yaozou.platform.common.quartz.factory;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.yaozou.platform.common.domain.ScheduleJob;
import com.yaozou.platform.common.quartz.utils.TaskUtils;


 
/**
 *  @description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @author luojianhong
 * @version $Id: QuartzJobFactoryDisallowConcurrentExecution.java, v 0.1 2017年11月8日 下午3:49:41 luojianhong Exp $
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);

	}
}