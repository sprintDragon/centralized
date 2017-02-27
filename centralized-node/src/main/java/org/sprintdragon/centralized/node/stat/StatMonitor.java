package org.sprintdragon.centralized.node.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sprintdragon.centralized.shared.model.info.StatInfo;

import javax.management.timer.Timer;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wangdi on 16-12-27.
 */
@Service
@Slf4j
public class StatMonitor {

    private static long mqOpm;//MQ接收
    public static AtomicLong mqOpmCount = new AtomicLong();

    private static long publishOpm;
    private static long executeOpm;
    public static AtomicLong publishCount = new AtomicLong();
    public static AtomicLong executeCount = new AtomicLong();
    public static AtomicLong executeErrorCount = new AtomicLong();

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long start = System.currentTimeMillis();
                    executeOpm = executeCount.getAndSet(0);
                    publishOpm = publishCount.getAndSet(0);
                    mqOpm = mqOpmCount.getAndSet(0);
                    log.info("StatMonitor running  每分钟 MQ接收={},生产数量={},每分钟处理数量={}", mqOpm, publishOpm, executeOpm);
                    try {
                        Thread.sleep(60 * Timer.ONE_SECOND - (System.currentTimeMillis() - start));
                    } catch (InterruptedException e) {
                        log.error("StatMonitor thread error", e);
                    }
                }
            }
        }).start();
    }

    public StatInfo getMonitor() {
        StatInfo monitor = new StatInfo();
//        monitor.setExecuteOpm(executeOpm);
//        monitor.setExecuteError(executeErrorCount.get());
//        monitor.setMqOpm(mqOpm);
        monitor.setExecuteOpm(new Random().nextInt(100));
        monitor.setExecuteError(new Random().nextInt(100));
        monitor.setMqOpm(new Random().nextInt(100));
        return monitor;
    }

}
