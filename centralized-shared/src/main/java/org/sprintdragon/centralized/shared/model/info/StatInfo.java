package org.sprintdragon.centralized.shared.model.info;

import lombok.Data;
import lombok.ToString;

/**
 * Created by wangdi on 17-2-22.
 */
@Data
@ToString
public class StatInfo {

    private int bufferSize;
    //环形缓冲剩余空间
    private long remainingCapacity;

    private long activeCount;
    private long taskCount;

    //有序队列长度
    private long redisZsetSize;
    //每分钟生产数量
    private long publishOpm;
    //每分钟处理数量 opm
    private long executeOpm;
    private long executeError;

}