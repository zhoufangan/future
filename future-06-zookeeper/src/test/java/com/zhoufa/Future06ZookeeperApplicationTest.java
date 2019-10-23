package com.zhoufa;

import com.zhoufa.zookeeper.ZooKeeperHelloWorld;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Unit test for simple Future06ZookeeperApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Future06ZookeeperApplicationTest {

    @Autowired
    private ZooKeeperHelloWorld zooKeeperHelloWorld;

    @Test
    public void helloWorldTest() {
        try {
            zooKeeperHelloWorld.helloWorld();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
