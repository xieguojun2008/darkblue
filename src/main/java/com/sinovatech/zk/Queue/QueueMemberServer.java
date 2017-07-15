package com.sinovatech.zk.Queue;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Xieguojun on 2017/6/23.
 * 该类模拟队列成员
 */
public class QueueMemberServer implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(ZkQueueMockClient.class);
    private ZkQueueMockClient client;

    public void run() {
        client = new ZkQueueMockClient();
        try {
            client.setWatcherOnStart();
            client.createQueueNode();
            if (client.checkQueueFull()) {
                logger.info(">>");
                synchronized (ZkQueueMockClient.lock) {
                    logger.info("<<");
                    if (!client.startExists()) {
                        client.createStartFlagNode();
                    }
                }
                doAction("nowait");
            } else {
                synchronized (ZkQueueMockClient.lock) {
                    ZkQueueMockClient.lock.wait();
                }
                doAction("wait");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAction(String wait) {
        logger.info(Thread.currentThread().getName() +" do Action "+wait);
    }

    public static void main(String[] args) {
        ZkQueueMockClient client = new ZkQueueMockClient();
        try {
            client.createSynchronizeNode();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            QueueMemberServer server = new QueueMemberServer();
            new Thread(server).start();
        }

        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
