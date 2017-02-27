package org.sprintdragon.centralized.manager.statistics.throughput.param;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by wangdi on 17-2-27.
 */
@Data
@ToString
public class Kav {

    private String name;
    private List<Long> data;

}
