package com.zhoufa.mq.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhoufangan. Founded on 2019/10/13 15:39.
 * @version V1.0
 */
@Component
public class Producer {

    @Value("${RocketMQ.namesrvAddr}")
    private String namesrvAddr;

    public void produce() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName("producer");
        producer.start();
        try {
            for (int i = 0; i < 10; i++) {
                // Thread.sleep(1000); // 每秒发送一次MQ
                Message msg = new Message("producer-topic", // topic 主题名称
                        "msg", // pull 临时值 在消费者消费的时候 可以根据msg类型进行消费
                        ("pushmsg-" + i).getBytes()// body 内容
                );
                SendResult sendResult = producer.send(msg);
                System.out.println("消息发送结果：" + sendResult.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
