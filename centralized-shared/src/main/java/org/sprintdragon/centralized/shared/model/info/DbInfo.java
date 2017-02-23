package org.sprintdragon.centralized.shared.model.info;

import lombok.Data;
import lombok.ToString;

/**
 * Created by wangdi on 17-2-20.
 */
@Data
@ToString
public class DbInfo {

    private String url;
    private String name;
    private String pwd;

}
