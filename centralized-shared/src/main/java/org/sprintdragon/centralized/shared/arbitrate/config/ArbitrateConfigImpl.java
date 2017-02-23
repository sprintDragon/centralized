package org.sprintdragon.centralized.shared.arbitrate.config;

import org.springframework.beans.factory.InitializingBean;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.info.StatInfo;

/**
 * Created by wangdi on 17-2-22.
 */
public class ArbitrateConfigImpl implements ArbitrateConfig, InitializingBean {

    //todo cache
    Node currentNode;
    Unit currentUnit;

    public ArbitrateConfigImpl(Unit unit, Node node) {
        this.currentUnit = unit;
        this.currentNode = node;
        ArbitrateConfigRegistry.regist(this);
    }

    @Override
    public Node currentNode() {
        return currentNode;
    }

    @Override
    public MsType currentMsType() {
        return currentNode.getMsType();
    }

    @Override
    public StatInfo currentStatInfo() {
        return currentNode.getStatInfo();
    }

    @Override
    public Unit currentUnit() {
        return currentUnit;
    }

    @Override
    public void reportStatInfo(StatInfo statInfo) {

    }

    public void afterPropertiesSet() throws Exception {
    }

}
