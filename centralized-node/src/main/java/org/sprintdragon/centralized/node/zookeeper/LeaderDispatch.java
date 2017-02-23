package org.sprintdragon.centralized.node.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.sprintdragon.centralized.shared.arbitrate.event.NodeOperateEvent;
import org.sprintdragon.centralized.shared.arbitrate.helper.ManagePathUtils;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;

import javax.annotation.Resource;

/**
 * Created by wangdi on 17-2-22.
 */
@Slf4j
public class LeaderDispatch extends ZKBase {

    @Resource
    NodeOperateEvent nodeOperateEvent;

    public LeaderDispatch(CuratorFramework curatorFramework) {
        super(curatorFramework);
    }

    /**
     * 异步监控，存在初始化过快，无法确定Leader，如必须在初始化时Leader执行，就在方法中直接调用
     */
    public void leaderSelector() {
        String path = ManagePathUtils.getUnitLeaderPath(ArbitrateConfigRegistry.getConfig().currentUnit().getUnitId()) + "/leader";
        LeaderSelector selector = new LeaderSelector(curatorFramework, path,
                new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework)
                            throws Exception {
                        log.info(" leader 选择成功！！！成为Master");
                        Node node = ArbitrateConfigRegistry.getConfig().currentNode();
                        node.setMsType(MsType.MASTER);
                        nodeOperateEvent.upsert(node);
                        while (true) {
                            Thread.sleep(Integer.MAX_VALUE);
                        }
                    }
                });
        selector.autoRequeue();
        selector.start();
    }

    @Override
    public void call() {
        leaderSelector();
    }

}
