package com.yanwei.platform.common.zbus;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.zbus.mq.Consumer;
import org.zbus.mq.Consumer.ConsumerHandler;
import org.zbus.net.http.Message;
import com.alibaba.fastjson.JSON;
import com.yanwei.platform.common.domain.SysLogDO;
import com.yanwei.platform.common.service.LogService;
/**
 * 日志消费类
 * @author luojianhong
 * @version $Id: ZbusProducerServiceImpl.java, v 0.1 2017年3月8日 下午1:52:51 luojianhong Exp $
 */
public class ZbusConsumerLogHandler implements ConsumerHandler {
	@Autowired
	private LogService logService;
	
	@Override
	public void handle(Message msg, Consumer consumer) throws IOException {
		SysLogDO sysLogDO = JSON.parseObject(msg.getBodyString(),SysLogDO.class);
		logService.save(sysLogDO);
	}

}
