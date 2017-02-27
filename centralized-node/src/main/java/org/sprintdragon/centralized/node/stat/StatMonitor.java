package org.sprintdragon.centralized.node.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;
import org.sprintdragon.centralized.shared.model.info.StatInfo;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputType;

import javax.management.timer.Timer;
import java.util.Date;
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

    public StatInfo getStatInfo() {
        StatInfo statInfo = new StatInfo();
//        monitor.setExecuteOpm(executeOpm);
//        monitor.setExecuteError(executeErrorCount.get());
//        monitor.setMqOpm(mqOpm);
        ThroughputStat mqStat = reportStat(ThroughputType.MQ);
        ThroughputStat memStat = reportStat(ThroughputType.MEM);
        ThroughputStat rowStat = reportStat(ThroughputType.ROW);
        statInfo.setMemThroughputStat(memStat);
        statInfo.setMqThroughputStat(mqStat);
        statInfo.setRowThroughputStat(rowStat);
        return statInfo;
    }

    private ThroughputStat reportStat(ThroughputType throughputType) {
        ThroughputStat stat = new ThroughputStat();
        stat.setUnitId(ArbitrateConfigRegistry.getConfig().currentUnit().getUnitId());
        stat.setTypeEnum(throughputType);
        stat.setCreate(new Date());
        stat.setModified(new Date());
        stat.setSize(1l);
        Date from = new Date(System.currentTimeMillis() - 60 * Timer.ONE_SECOND);
        stat.setStartTime(from);
        stat.setEndTime(new Date());
        switch (throughputType) {
            case MEM:
                stat.setNumber((long) new Random().nextInt(100));
                break;
            case ROW:
                stat.setNumber((long) new Random().nextInt(100));
                break;
            case MQ:
                stat.setNumber((long) new Random().nextInt(100));
                break;
            default:
        }
        return stat;
    }


}
