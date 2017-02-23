package org.sprintdragon.centralized.shared.model.builder;


import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.enums.NodeStatus;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;

/**
 * Created by wangdi on 17-2-21.
 */
public class NodeBuilder {

    private Unit unit;
    private MsType msType;
    private NodeStatus status;
    private ServerInfo serverInfo;

    public NodeBuilder unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public NodeBuilder msType(MsType msType) {
        this.msType = msType;
        return this;
    }

    public NodeBuilder status(NodeStatus status) {
        this.status = status;
        return this;
    }

    public NodeBuilder serverInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        return this;
    }

    public Node build() {
        Node node = new Node();
        node.setUnit(unit);
        node.setMsType(msType);
        node.setStatus(status);
        node.setServerInfo(serverInfo);
        return node;
    }

}
