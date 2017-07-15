package com.sinovatech.zk.Queue;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Xieguojun on 2017/6/23.
 * 模拟多线程访问zk服务，实现同步队列模型
 */
public class ZkQueueMockClient implements Watcher{
    private static Logger logger = LoggerFactory.getLogger(ZkQueueMockClient.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zooKeeper;
    private String synchronizeNode = "/synchronizeNode";
    private final int queueSize = 100;
    public final static Object lock = new Object();

    public ZooKeeper getConnector() {
        if (zooKeeper != null) {
            return zooKeeper;
        }
        try {
            logger.info(Thread.currentThread().getName() + " creating zk");
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 10000, this);
            latch.await();
        } catch (IOException e) {
            logger.info("建立zk连接异常", e);
        } catch (InterruptedException e) {
            logger.info("建立zk连接异常", e);
        }

        return zooKeeper;
    }

    public void process(WatchedEvent watchedEvent) {
        logger.info(watchedEvent.getType().toString());
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            logger.info("watcher countdown");
            latch.countDown();
        }
    }

    public void createSynchronizeNode() throws KeeperException, InterruptedException {
        getConnector().create(synchronizeNode, synchronizeNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void createQueueNode() throws KeeperException, InterruptedException {
        getConnector().create(synchronizeNode + "/member_", "i am a queue member".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public void createStartFlagNode() throws KeeperException, InterruptedException {
        getConnector().create(synchronizeNode + "/start", "start".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public boolean checkQueueFull() throws KeeperException, InterruptedException {
        List<String> children = getConnector().getChildren(synchronizeNode, false);
        if (children != null && children.size() >= queueSize) {
            return true;
        }
        return false;
    }

    public void setWatcherOnStart() throws KeeperException, InterruptedException {
        getConnector().exists(synchronizeNode + "/start", new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                    synchronized (ZkQueueMockClient.lock) {
                        ZkQueueMockClient.lock.notifyAll();
                    }
                }
            }
        });
    }

    public boolean startExists() throws KeeperException, InterruptedException {
        Stat exists = getConnector().exists(synchronizeNode + "/start", false);
        if (exists!=null) {
            return true;
        }
        return false;
    }
}
