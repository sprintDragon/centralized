package org.sprintdragon.centralized.shared.arbitrate.event;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.sprintdragon.centralized.shared.arbitrate.helper.ManagePathUtils;
import org.sprintdragon.centralized.shared.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 17-2-22.
 */
public class NodeOperateEvent implements OperateEvent<Node> {

    CuratorFramework client;

    @Override
    public void upsert(Node node) throws Exception {
        //添加info节点
        String path = ManagePathUtils.getUnitNodePath(node.getUnit().getUnitId(), node.getServerInfo().getIp());
        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, JSON.toJSONString(node).getBytes());
        } else {
            client.setData().forPath(path, JSON.toJSONString(node).getBytes());
        }
    }

    public Node find(String unitId, String ip) throws Exception {
        String path = ManagePathUtils.getUnitNodePath(unitId, ip);
        return JSON.parseObject(new String(client.getData().forPath(path)), Node.class);
    }

    @Override
    public List<Node> list() throws Exception {
        String path = ManagePathUtils.getUnitInfoRootPath();
        List<Node> us = new ArrayList<>();
        List<String> ls = client.getChildren().forPath(path);
        for (String s : ls) {
            List<String> cls = client.getChildren().forPath(ManagePathUtils.getUnitInfoPath(s));
            for (String cl : cls) {
                Node node = JSON.parseObject(new String(client.getData().forPath(ManagePathUtils.getUnitNodePath(s, cl))), Node.class);
                us.add(node);
            }
        }
        return us;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
