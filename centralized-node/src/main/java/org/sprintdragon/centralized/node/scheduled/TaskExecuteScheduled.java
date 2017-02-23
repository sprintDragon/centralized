package org.sprintdragon.centralized.node.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;

/**
 * Created by wangdi on 16-12-19.
 */
@Component
@Slf4j
public class TaskExecuteScheduled {

    @Async
    @Scheduled(fixedRate = 10000, initialDelay = 2000)
    public void batchPop() {
        try {
            if (ArbitrateConfigRegistry.getConfig().currentMsType().isSlave()) {
                long startTime = System.currentTimeMillis();
                log.debug("batchPop begin thread={}", Thread.currentThread().getName());
                long fetchNum = 1;
                log.debug("batchPop end thread={},fetchNum={},cost={}", Thread.currentThread().getName(), fetchNum, System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            log.error("batchPop error", e);
        }
    }

    @Async
    @Scheduled(fixedRate = 60000, initialDelay = 2000)
    public void resumeTimeout() {
        try {
            if (ArbitrateConfigRegistry.getConfig().currentMsType().isMaster()) {
                long startTime = System.currentTimeMillis();
                log.debug("resumeTimeout begin thread={}", Thread.currentThread().getName());
                long fetchNum = 1;
                log.debug("resumeTimeout end thread={},fetchNum={},cost={}", Thread.currentThread().getName(), fetchNum, System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            log.error("resumeTimeout error", e);
        }
    }

}
