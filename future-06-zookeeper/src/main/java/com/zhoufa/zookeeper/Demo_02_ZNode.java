package com.zhoufa.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhoufangan. Founded on 2019/10/23 10:04.
 * @version V1.0
 */
@Component
public class Demo_02_ZNode {

    @Value("${zookeeper.connectString}")
    private String connectString;
    @Value("${zookeeper.defaultSessionTimeout}")
    private Integer defaultSessionTimeout;

    public void testThread1() throws IOException, InterruptedException, KeeperException {
        // 1.创建连接
        ZooKeeper zk = new ZooKeeper(connectString, defaultSessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("----------->");
                System.out.println("path:" + watchedEvent.getPath());
                System.out.println("type:" + watchedEvent.getType());
                System.out.println("stat:" + watchedEvent.getState());
                System.out.println("<-----------");
            }
        });

        // 2.创建基础节点：持久化的
        String baseNode = "/test02";
        // 检测 /HelloWorld是否存在
        Stat stat = zk.exists(baseNode, false);
        if (stat == null) {
            // 创建节点
            String createResult = zk.create(baseNode, "test02".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }

        // 2.1 创建持久节点：
        // 持久化目录节点, 会话结束存储数据不会丢失
        String node_persistent = baseNode + "/persistent";
        Stat stat_persistent = zk.exists(node_persistent, false);
        if (stat_persistent == null) {
            // 创建节点
            String createResult = zk.create(
                    node_persistent,
                    "persistent".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            System.out.println(createResult);
        }

        // 2.2 创建持久节点：
        // 顺序自动编号持久化目录节点, 存储数据不会丢失, 会根据当前已存在节点数自动加1, 然后返回给客户端已经创建成功的节点名
        String node_persistent_sequential = baseNode + "/persistent_sequential_";
        for (int i = 0; i < 10; i++) {
            String createResult = zk.create(
                    node_persistent_sequential,
                    "persistent_sequential".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT_SEQUENTIAL);
            System.out.println(createResult);
        }

        // 2.3 创建临时节点
        String node_ephemeral = baseNode + "/ephemeral";
        String createResult1 = zk.create(
                node_ephemeral,
                "ephemeral".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println(createResult1);

        // 2.4 创建临时节点
        String node_ephemeral_sequential = baseNode + "/ephemeral_sequential_";
        for (int i = 0; i < 10; i++) {
            String createResult = zk.create(
                    node_ephemeral_sequential,
                    "ephemeral_sequential".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(createResult);
        }
    }

}
