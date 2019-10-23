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
public class ZooKeeperHelloWorld {

    @Value("${zookeeper.connectString}")
    private String connectString;
    @Value("${zookeeper.defaultSessionTimeout}")
    private Integer defaultSessionTimeout;

    public void helloWorld() throws IOException, InterruptedException, KeeperException {
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

        // 2.创建节点
        String node = "/HelloWorld";
        // 检测 /HelloWorld是否存在
        Stat stat = zk.exists(node, false);
        if (stat == null) {
            // 创建节点
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }

        // 3.修改节点的数据
        String data = "test2";
        zk.setData(node, data.getBytes(), -1);

        // 4.获取节点的值
        byte[] b = zk.getData(node, false, stat);
        System.out.println(new String(b));

        // 5.删除节点
        zk.delete(node, -1);
        System.out.println("========== 删除节点 ==========");

        System.out.println("节点状态：" + zk.exists(node, false));
        System.out.println("========== 查看节点是否被删除 ============");

        zk.close();
    }

}
