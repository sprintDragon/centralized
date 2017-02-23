package org.sprintdragon.centralized.shared.model;

import lombok.Data;
import lombok.ToString;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.enums.NodeStatus;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;
import org.sprintdragon.centralized.shared.model.info.StatInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangdi on 17-2-16.
 */
@Data
@ToString
public class Node implements Serializable {

    private NodeStatus status;                                 // 对应状态
    private MsType msType;                              //主从
    private ServerInfo serverInfo;
    private StatInfo statInfo;
    private Unit unit;

    private Date gmtCreate;                              // 创建时间
    private Date gmtModified;                            // 修改时间

}
