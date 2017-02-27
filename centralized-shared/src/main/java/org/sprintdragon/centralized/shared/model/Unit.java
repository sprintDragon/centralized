package org.sprintdragon.centralized.shared.model;

import lombok.Data;
import lombok.ToString;
import org.sprintdragon.centralized.shared.model.info.DbInfo;
import org.sprintdragon.centralized.shared.model.info.StatInfo;

import java.util.Date;

/**
 * Created by wangdi on 17-2-22.
 */
@Data
@ToString
public class Unit {

    private Long unitId;
    private DbInfo dbInfo;
    private StatInfo statInfo;

    private Date gmtCreate;                              // 创建时间
    private Date gmtModified;                            // 修改时间

}
