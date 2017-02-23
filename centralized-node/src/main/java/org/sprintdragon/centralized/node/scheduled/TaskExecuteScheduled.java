package org.sprintdragon.centralized.node.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.sprintdragon.centralized.node.dao.DynamicDataSourceManager;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;

import javax.annotation.Resource;

/**
 * Created by wangdi on 16-12-19.
 */
@Component
@Slf4j
public class TaskExecuteScheduled {

    @Resource
    DynamicDataSourceManager dynamicDataSourceManager;

    @Async
    @Scheduled(fixedRate = 10000, initialDelay = 2000)
    public void batchPop() {
        try {
            if (ArbitrateConfigRegistry.getConfig().currentMsType().isSlave()) {
                long startTime = System.currentTimeMillis();
                log.info("batchPop begin thread={}", Thread.currentThread().getName());
                long fetchNum = count();
                log.info("batchPop end thread={},fetchNum={},cost={}", Thread.currentThread().getName(), fetchNum, System.currentTimeMillis() - startTime);
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
                log.info("resumeTimeout begin thread={}", Thread.currentThread().getName());
                long fetchNum = count();
                log.info("resumeTimeout end thread={},fetchNum={},cost={}", Thread.currentThread().getName(), fetchNum, System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            log.error("resumeTimeout error", e);
        }
    }

    private int count() throws Exception {
        JdbcTemplate jdbcTemplate = dynamicDataSourceManager.getJdbcTemplateByUnitId(ArbitrateConfigRegistry.getConfig().currentUnit().getUnitId());
        return jdbcTemplate.queryForObject("select count(*) from order_trace_info", Integer.class);
    }
}
