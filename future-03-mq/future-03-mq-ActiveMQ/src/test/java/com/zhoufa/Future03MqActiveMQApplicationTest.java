package com.zhoufa;

import com.zhoufa.mq.activemq.MessageConsumer;
import com.zhoufa.mq.activemq.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple Future03MqActiveMQApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Future03MqActiveMQApplicationTest {
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private MessageConsumer messageConsumer;

    @Test
    public void testProduce() {
        try {
            messageProducer.produce();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsume() {
        try {
            messageConsumer.consume();
        } catch (JMSException e) {
            e.printStackTrace();
        }


        // junit 调试时@test函数中的多线程如果没有特殊处理会立即结束
        // 这里需要线程等待
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try {
            // 30秒自动结束
            pool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
