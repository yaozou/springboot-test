package com.yaozou.platform.spring;

import com.yaozou.platform.member.domain.ApiOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: kafka发送消息
 * @Auther: yaozou
 * @Date: 2018/8/22 16:52
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KakaController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public ApiOut<Void> sendKafka(HttpServletRequest request, HttpServletResponse response) {
        try {
            String message = request.getParameter("message");
            log.info("kafka的消息={}",message);
            kafkaTemplate.send("test", "key", message);
            log.info("发送kafka成功.");
        } catch (Exception e) {
            log.error("发送kafka失败", e);
        }
        return ApiOut.success();
    }
}
