/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sprintdragon.centralized.shared.statistics.throughput;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 吞吐量统计
 */
@Data
@ToString
public class ThroughputStat implements Serializable {

    private static final long serialVersionUID = 1953478777704061454L;
    private Long id;
    private Long unitId;
    private Date startTime;
    private Date endTime;
    private ThroughputType typeEnum;
    private Long number;
    private Long size;
    private Date create;
    private Date modified;

    public void sub(ThroughputStat stat) {
        this.unitId = stat.getUnitId();
        this.startTime = stat.getStartTime();
        this.endTime = stat.getEndTime();
        this.typeEnum = stat.getTypeEnum();
        this.create = stat.getCreate();
        this.modified = stat.getModified();
        if (this.number == null) {
            this.number = 0l;
        }
        if (this.size == null) {
            this.size = 0l;
        }
        this.number += stat.getNumber();
        this.size += stat.getSize();
    }

}
