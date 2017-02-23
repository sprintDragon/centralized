package org.sprintdragon.centralized.node.zookeeper;

import org.apache.curator.framework.CuratorFramework;

/**
 * Created by wangdi on 17-2-22.
 */
public abstract class ZKBase {

    protected CuratorFramework curatorFramework;

    public ZKBase(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public void init() {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void call() throws Exception;
}
