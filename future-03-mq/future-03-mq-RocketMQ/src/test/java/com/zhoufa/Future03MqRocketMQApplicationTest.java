package com.zhoufa;

import com.zhoufa.mq.rocketmq.Consumer;
import com.zhoufa.mq.rocketmq.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple Future03MqRocketMQApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Future03MqRocketMQApplicationTest {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @Test
    public void testProducer() {
        try {
            producer.produce();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        // junit 调试时@test函数中的多线程如果没有特殊处理会立即结束
        // 这里需要线程等待
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try {
            // 30秒自动结束
            pool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsumer() {
        try {
            consumer.consume();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        // junit 调试时@test函数中的多线程如果没有特殊处理会立即结束
        // 这里需要线程等待
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try {
            // 30秒自动结束
            pool.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
