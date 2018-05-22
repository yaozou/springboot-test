package com.yanwei.platform.common.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Consumer;
import org.zbus.mq.MqConfig;
import org.zbus.mq.Producer;
import org.zbus.mq.Consumer.ConsumerHandler;
import org.zbus.mq.server.MqServer;
import org.zbus.mq.server.MqServerConfig;
import com.yanwei.platform.common.zbus.ZbusConsumerLogHandler;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class ZbusConfig {
	
	private MqServer mqServer;
	
	
	@PostConstruct
    public  void startZbusServer() {
        MqServerConfig config = new MqServerConfig();
        config.serverPort = 15555;
        config.storePath = "./store";
        config.serverName = "bootmemebers";
      
       try {
    	   mqServer=new MqServer(config);
    	   mqServer.start();
            System.out.println("启动zbus服务＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@PreDestroy
	public void stopZbusServer(){
		try {
			if(mqServer!=null)
			mqServer.close();
			 System.out.println("关闭zbus服务＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    
    //上传路径
    @Autowired
    private BootMemebersConfig bootMemebersConfig;

    //日志类
    @Bean(name = "zbusConsumerLogHandler")
    public ConsumerHandler zbusConsumerLogHandler() {
    	ZbusConsumerLogHandler zbusConsumerHandler = new ZbusConsumerLogHandler();
        return zbusConsumerHandler;
    }
    

    /**
     * 注册zbus Broker中间消息服务器
     * @return
     * @throws IOException
     */
    @Bean
    public Broker broker() {
        BrokerConfig brokerConfig = new BrokerConfig();
        brokerConfig.setBrokerAddress(bootMemebersConfig.getBrokerAddress());
        brokerConfig.setMaxTotal(40);
        Broker broker = null;
        try {
            broker = new SingleBroker(brokerConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return broker;
    }
    
    
    /**
     * 注册zbus MQ Log生产者
     * @return
     */
    @Bean(name="producerLog")
    public Producer producerLog() {
        Producer producer = null;
        try {
            producer = new Producer(broker(), "memebers-Log-MQ");
            producer.createMQ();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return producer;
    }
    

    /**
     * 注册zbus MQ 成长值生产者
     * @return
     */
    @Bean(name="producerGrowth")
    public Producer producerGrowth() {
        Producer producer = null;
        try {
            producer = new Producer(broker(), "memebers-growth-MQ");
            producer.createMQ();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return producer;
    }
    
    
    /**
     * 注册zbus MQ 日志消费者
     * @return
     */
    @Bean(name="consumerLog")
    public Consumer consumerLog() {
        MqConfig mqConfig = new MqConfig();
        mqConfig.setBroker(broker());
        mqConfig.setMq("memebers-Log-MQ");
        Consumer consumer = new Consumer(mqConfig);
        try {
            consumer.start(zbusConsumerLogHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consumer;
    }
    
    

}
