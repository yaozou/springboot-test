package com.yaozou.platform.common.zbus;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zbus.mq.Producer;
import org.zbus.net.Sync.ResultCallback;
import org.zbus.net.http.Message;

/**
 * 日志生成类
 * @author luojianhong
 * @version $Id: ZbusProducerLogService.java, v 0.1 2017年11月17日 上午9:18:06 luojianhong Exp $
 */
@Service
public class ZbusProducerLogService {

	
	@Autowired(required = false)
	@Qualifier("producerLog")
	private Producer producer;

	public void sendAsync(Message msg, ResultCallback<Message> callback)
			throws IOException {
		producer.sendAsync(msg, callback);
	}

	public void sendAsync(Message msg) throws IOException {
		producer.sendAsync(msg);
	}

	public Message sendSync(Message msg, int timeout) throws IOException,
			InterruptedException {
		return producer.sendSync(msg, timeout);
	}

	public Message sendSync(Message msg) throws IOException,
			InterruptedException {
		return producer.sendSync(msg);
	}

}
