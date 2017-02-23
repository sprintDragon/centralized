package org.sprintdragon.centralized.node.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigImpl;
import org.sprintdragon.centralized.shared.arbitrate.event.NodeOperateEvent;
import org.sprintdragon.centralized.shared.arbitrate.event.UnitOperateEvent;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.builder.NodeBuilder;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.enums.NodeStatus;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;
import org.sprintdragon.centralized.shared.utils.SystemIpUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by wangdi on 17-2-21.
 */
@Configuration
@Slf4j
public class NodeSourceInitConfig {

    @Resource
    UnitOperateEvent unitOperateEvent;
    @Resource
    NodeOperateEvent nodeOperateEvent;

    @PostConstruct
    public void init() throws Exception {
        Unit unit = unitOperateEvent.pickUnit();
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName("本机名1");
        serverInfo.setDescription("描述1");
        serverInfo.setIp(SystemIpUtil.getRealIp());
        Node node = new NodeBuilder().unit(unit).msType(MsType.SLAVE).status(NodeStatus.INIT).serverInfo(serverInfo).build();
        //初始化
        nodeOperateEvent.upsert(node);
        new ArbitrateConfigImpl(unit, node);
    }

}
