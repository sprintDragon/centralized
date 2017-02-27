package org.sprintdragon.centralized.shared.model.info;

import lombok.Data;
import lombok.ToString;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;

/**
 * Created by wangdi on 17-2-22.
 */
@Data
@ToString
public class StatInfo {

    private ThroughputStat mqThroughputStat;
    private ThroughputStat memThroughputStat;
    private ThroughputStat rowThroughputStat;

    public void sub(StatInfo statInfo) {
        mqThroughputStat.sub(statInfo.getMqThroughputStat());
        memThroughputStat.sub(statInfo.getMemThroughputStat());
        rowThroughputStat.sub(statInfo.getRowThroughputStat());
    }

}
