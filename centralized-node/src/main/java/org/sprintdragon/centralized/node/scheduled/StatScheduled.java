package org.sprintdragon.centralized.node.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.sprintdragon.centralized.node.stat.StatMonitor;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;
import org.sprintdragon.centralized.shared.arbitrate.event.NodeOperateEvent;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.info.StatInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi on 16-12-19.
 */
@Component
@Slf4j
public class StatScheduled {

    @Resource
    NodeOperateEvent nodeOperateEvent;
    @Resource
    StatMonitor statMonitor;

    @Async
    @Scheduled(fixedRate = 10000, initialDelay = 2000)
    public void reportStat() {
        try {
            Node node = ArbitrateConfigRegistry.getConfig().currentNode();
            if (node.getMsType() == MsType.SLAVE) {
                StatInfo statInfo = statMonitor.getMonitor();
                node.setStatInfo(statInfo);
            } else {
                List<Node> nodeList = nodeOperateEvent.list();
                StatInfo statInfo = new StatInfo();
                for (Node n : nodeList) {
                    if (n.getMsType() == MsType.SLAVE) {
                        statInfo.sub(n.getStatInfo());
                    }
                }
                node.setStatInfo(statInfo);
            }
            nodeOperateEvent.upsert(node);
        } catch (Exception e) {
            log.error("batchPop error", e);
        }
    }

}
