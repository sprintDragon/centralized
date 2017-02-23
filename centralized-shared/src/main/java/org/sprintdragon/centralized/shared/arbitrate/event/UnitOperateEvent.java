package org.sprintdragon.centralized.shared.arbitrate.event;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.enums.NodeStatus;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;
import org.sprintdragon.centralized.shared.utils.SystemIpUtil;
import org.sprintdragon.centralized.shared.arbitrate.helper.ManagePathUtils;
import org.sprintdragon.centralized.shared.model.builder.NodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 17-2-22.
 */
public class UnitOperateEvent implements OperateEvent<Unit> {

    NodeOperateEvent nodeOperateEvent;

    CuratorFramework client;

    public void start() throws Exception {
        Unit unit = pickUnit();
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName("本机名1");
        serverInfo.setDescription("描述1");
        serverInfo.setIp(SystemIpUtil.getRealIp());
        Node node = new NodeBuilder().unit(unit).status(NodeStatus.INIT).serverInfo(serverInfo).build();
        //初始化
        nodeOperateEvent.upsert(node);
    }

    @Override
    public void upsert(Unit unit) throws Exception {
        //添加info节点
        String path = ManagePathUtils.getUnitInfoPath(unit.getUnitId());
        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, JSON.toJSONString(unit).getBytes());
        } else {
            client.setData().forPath(path, JSON.toJSONString(unit).getBytes());
        }
    }

    public Unit find(String id) throws Exception {
        String path = ManagePathUtils.getUnitInfoPath(id);
        return JSON.parseObject(new String(client.getData().forPath(path)), Unit.class);
    }

    @Override
    public List<Unit> list() throws Exception {
        String path = ManagePathUtils.getUnitInfoRootPath();
        List<Unit> us = new ArrayList<>();
        List<String> ls = client.getChildren().forPath(path);
        for (String s : ls) {
            Unit unit = JSON.parseObject(new String(client.getData().forPath(ManagePathUtils.getUnitInfoPath(s))), Unit.class);
            us.add(unit);
        }
        return us;
    }

    public Unit pickUnit() throws Exception {
//        String path = ManagePathUtils.getUnitInfoRootPath();
//        List<String> ls = client.getChildren().forPath(path);
//        int min = Integer.MAX_VALUE;
//        String unitId = "";
//        for (String s : ls) {
//            int num = client.getChildren().forPath(ManagePathUtils.getUnitInfoPath(s)).size();
//            if (num < min) {
//                unitId = s;
//            }
//        }
//        if ("".equals(unitId)) {
        return list().get(0);
//        } else {
//            return find(unitId);
//        }
    }

    public void setNodeOperateEvent(NodeOperateEvent nodeOperateEvent) {
        this.nodeOperateEvent = nodeOperateEvent;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
