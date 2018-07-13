package com.yaozou.platform.common.service;

import com.yaozou.platform.common.domain.TaskScheduleJobDO;

import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;

 
/**
 * 
 * @author luojianhong
 * @version $Id: TaskScheduleJobService.java, v 0.1 2017年10月10日 上午10:55:30 luojianhong Exp $
 */
public interface TaskScheduleJobService {
	
	TaskScheduleJobDO get(Long id);
	
	List<TaskScheduleJobDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TaskScheduleJobDO taskScheduleJob);
	
	int update(TaskScheduleJobDO taskScheduleJob);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;
}
