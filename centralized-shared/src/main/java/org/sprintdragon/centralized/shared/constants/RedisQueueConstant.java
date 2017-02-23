package org.sprintdragon.centralized.shared.constants;

import javax.management.timer.Timer;

/**
 * Created by wangdi on 17-2-22.
 */
public class RedisQueueConstant {

    public static String REDIS_PREFIX_KEY = "task_dispatch_";
    public static String REDIS_FULL_ZSET_NAME = REDIS_PREFIX_KEY + "wait_task_zset";
    //恢复表中数据时间线
    public static long REDIS_TIME_OUT_BEFORE = Timer.ONE_MINUTE;

}
