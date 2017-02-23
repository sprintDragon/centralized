package org.sprintdragon.centralized.shared.model.info;

import lombok.Data;
import lombok.ToString;

/**
 * Created by wangdi on 17-2-20.
 */
@Data
@ToString
public class ServerInfo {

    private String name;                                   // 机器名字
    private String ip;                                     // 机器ip
    private Long port;                                   // 和manager对应的通讯端口
    private String description;                            // 详细描述

}
