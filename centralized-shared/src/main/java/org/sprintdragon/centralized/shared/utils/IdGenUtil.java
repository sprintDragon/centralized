package org.sprintdragon.centralized.shared.utils;

/**
 * Created by wangdi on 16-12-14.
 */
public class IdGenUtil {

    private volatile static IdGenUtil instance = null;
    private IdWorker idWorker;

    private IdGenUtil(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    public static IdGenUtil getInstance() {
        if (instance == null) {
            synchronized (IdGenUtil.class) {
                if (instance == null) {
                    String appId = System.getProperty("deploy.app.id");
                    long datacenterId = appId != null ? appId.hashCode() % 30 : 0;
                    long workerId = SystemIpUtil.getIpPostfix() % 30;
                    instance = new IdGenUtil(new IdWorker(workerId, datacenterId));
                }
            }
        }
        return instance;
    }

    public static Long genId() {
        return getInstance().idWorker.nextId();
    }

}
