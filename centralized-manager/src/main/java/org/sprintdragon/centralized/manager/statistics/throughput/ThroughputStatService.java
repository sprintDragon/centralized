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

package org.sprintdragon.centralized.manager.statistics.throughput;

import org.sprintdragon.centralized.manager.statistics.throughput.param.ThroughputCondition;
import org.sprintdragon.centralized.manager.statistics.throughput.param.ThroughputInfo;
import org.sprintdragon.centralized.manager.statistics.throughput.param.TimelineThroughputCondition;
import org.sprintdragon.centralized.manager.statistics.throughput.view.Highcharts;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;

import java.util.List;
import java.util.Map;

/**
 */
public interface ThroughputStatService {

    ThroughputInfo listRealtimeThroughput(ThroughputCondition condition);

    Highcharts listTimeLineThroughtForView();

    Map<Long, ThroughputInfo> listTimelineThroughput(TimelineThroughputCondition condition);

//    public List<ThroughputStat> listRealtimeThroughputByPipelineIds(List<Long> pipelineIds, int minute);

    ThroughputStat findThroughputStatByUnitId(ThroughputCondition condition);

    void createOrUpdateThroughput(ThroughputStat item);
}
