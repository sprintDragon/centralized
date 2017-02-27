package org.sprintdragon.centralized.manager.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.sprintdragon.centralized.manager.statistics.throughput.ThroughputStatService;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputType;

import javax.annotation.Resource;
import javax.management.timer.Timer;
import java.util.Date;
import java.util.Random;

/**
 * Created by wangdi on 16-12-19.
 */
@Component
@Slf4j
public class TaskExecuteScheduled {

    @Resource
    private ThroughputStatService throughputStatService;

    @Async
    @Scheduled(fixedRate = 5000, initialDelay = 2000)
    public void timerStat() {
        throughputStatService.createOrUpdateThroughput(reportStat(ThroughputType.MEM));

        throughputStatService.createOrUpdateThroughput(reportStat(ThroughputType.ROW));

        throughputStatService.createOrUpdateThroughput(reportStat(ThroughputType.MQ));
    }

    private ThroughputStat reportStat(ThroughputType throughputType) {
        ThroughputStat stat = new ThroughputStat();
        stat.setUnitId(101l);
        stat.setTypeEnum(throughputType);
        stat.setCreate(new Date());
        stat.setModified(new Date());
        stat.setSize(10l);
        Date from = new Date(System.currentTimeMillis() - 5 * Timer.ONE_SECOND);
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
