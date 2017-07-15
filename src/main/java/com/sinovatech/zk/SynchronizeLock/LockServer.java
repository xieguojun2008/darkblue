package com.sinovatech.zk.SynchronizeLock;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Xieguojun on 2017/6/19.
 *
 */
public class LockServer implements Runnable{
    private static Logger logger = LoggerFactory.getLogger(ZkLockClient.class);

    public void run() {
        ZkLockClient client = new ZkLockClient();
        try {
            client.createServerNode();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.getLock();
        client.close();
        logger.info(Thread.currentThread().getName()+" exit");
        logger.info(Thread.currentThread().getName() + " awake "+client.awakeCount +" times");
    }
}
