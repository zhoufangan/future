package com.zhoufa.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author zhoufangan. Founded on 2019/10/12 15:52.
 * @version V1.0
 */
@Component
public class MessageProducer {

    // 定义ActivMQ的连接地址
    @Value("${activemq.url}")
    private String ACTIVEMQ_URL;
    // 定义发送消息的队列名称
    private static final String QUEUE_NAME = "MyMessage";

    public void produce() throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //打开连接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标
        Destination destination = session.createQueue(QUEUE_NAME);
        //创建一个生产者
        javax.jms.MessageProducer producer = session.createProducer(destination);
        //创建模拟100个消息
        for (int i = 1; i <= 100; i++) {
            TextMessage message = session.createTextMessage("我发送message:" + i);
            //发送消息
            producer.send(message);
            //在本地打印消息
            System.out.println("我现在发的消息是：" + message.getText());
        }
        //关闭连接
        connection.close();
    }

}
