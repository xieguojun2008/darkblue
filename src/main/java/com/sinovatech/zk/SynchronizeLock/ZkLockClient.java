package com.sinovatech.zk.SynchronizeLock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Xieguojun on 2017/6/16.
 * ZooKeeper Client
 */
public class ZkLockClient implements Watcher {
    private static Logger logger = LoggerFactory.getLogger(ZkLockClient.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zooKeeper = null;
    private String rootPath = "/lock";
    private String serverNode = "";
    public static Object mutex = new Object();
    public int awakeCount = 0;


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

    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent watchedEvent) {
        logger.info(watchedEvent.getType().toString());
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            logger.info("watcher countdown");
            latch.countDown();
        }
    }

    public void createZNode(String path, String data) {
        try {
            String cPath = getConnector().create(path, data.getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("创建节点成功：" + cPath);
        } catch (KeeperException e) {
            logger.info("建立zk连接异常", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getZNodeData(String path) {
        try {
            byte[] data = getConnector().getData(path, this, null);
            return new String(data);
        } catch (KeeperException e) {
            logger.info("zk读取数据异常", e);
        } catch (InterruptedException e) {
            logger.info("zk读取数据异常", e);
        }
        return null;
    }

    public void createLockRoot() throws KeeperException, InterruptedException {
        rootPath = getConnector().create(rootPath, "i am lock root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void createServerNode() throws KeeperException, InterruptedException {
        serverNode = getConnector().create(rootPath + "/lock", "i am sub node".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public void getLock() {
        try {
            List<String> subNodes = getConnector().getChildren(rootPath, false);
            Collections.sort(subNodes);
            if (serverNode.equals(rootPath + "/" + subNodes.get(0))) {
                synchronized (mutex) {
                    logger.info(Thread.currentThread().getName() + " got lock,do some things");
                    mutex.notifyAll();
                }
                logger.info(Thread.currentThread().getName() + " " + serverNode + " release lock");
                /*getConnector().delete(serverNode,-1);*/
                /*Thread.sleep(3*1000);*/
            } else {
                waitForLock(subNodes.get(0));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void waitForLock(String smallestNode) throws KeeperException, InterruptedException {
        Stat stat = getConnector().exists(rootPath + "/" + smallestNode, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                    logger.info("watcher notify: " + Thread.currentThread().getName() + " " + watchedEvent.getPath() + " deleted");
                    synchronized (ZkLockClient.mutex) {
                        ZkLockClient.mutex.notifyAll();
                    }
                }
            }
        });
        logger.info(Thread.currentThread().getName() + " operate exists()");
        if (stat != null) {
            synchronized (mutex) {
                logger.info(Thread.currentThread().getName() + " got lock,and is about to wait");
                mutex.wait();
            }
            logger.info(Thread.currentThread().getName() + " awake");
            awakeCount++;
        }
        getLock();
    }

    public static void main(String[] args) {
        ZkLockClient client = new ZkLockClient();

        for (int i = 0; i < 100; i++) {
            LockServer server = new LockServer();
            new Thread(server).start();
        }

        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
