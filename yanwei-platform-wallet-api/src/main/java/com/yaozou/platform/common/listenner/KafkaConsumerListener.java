package com.yaozou.platform.common.listenner;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @Description:
 * @Auther: yaozou
 * @Date: 2018/8/22 16:47
 */
@Slf4j
public class KafkaConsumerListener {

    @KafkaListener(topics={"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key: " + record.key());
        log.info("kafka的value: " + record.value().toString());
    }
}
