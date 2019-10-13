package com.zhoufa.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoufangan. Founded on 2019/10/13 15:40.
 * @version V1.0
 */
@Component
public class Consumer {

    @Value("${RocketMQ.namesrvAddr}")
    private String namesrvAddr;

    public void consume() throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");

        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setInstanceName("consumer");

        consumer.subscribe("producer-topic", "msg");//此处是根据Message对象的参数来获取

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("消息id:" + msg.getMsgId() + "---" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
