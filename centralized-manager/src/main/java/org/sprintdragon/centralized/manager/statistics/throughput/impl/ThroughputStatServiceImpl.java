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

package org.sprintdragon.centralized.manager.statistics.throughput.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.sprintdragon.centralized.manager.statistics.throughput.ThroughputStatService;
import org.sprintdragon.centralized.manager.statistics.throughput.param.ThroughputCondition;
import org.sprintdragon.centralized.manager.statistics.throughput.param.ThroughputInfo;
import org.sprintdragon.centralized.manager.statistics.throughput.param.TimelineThroughputCondition;
import org.sprintdragon.centralized.manager.statistics.throughput.repository.ThroughputStatDO;
import org.sprintdragon.centralized.manager.statistics.throughput.repository.ThroughputStatRepository;
import org.sprintdragon.centralized.manager.statistics.throughput.view.Highcharts;
import org.sprintdragon.centralized.manager.statistics.throughput.view.Series;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputType;
import org.sprintdragon.centralized.shared.utils.CopyPropertyUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author danping.yudp
 */
@Service
public class ThroughputStatServiceImpl implements ThroughputStatService {

    @Resource
    ThroughputStatRepository throughputStatRepository;

    /**
     * 在数据库中插入throughputStat
     */
    public void createOrUpdateThroughput(ThroughputStat item) {
        Assert.isTrue(item != null);
        throughputStatRepository.save(throughputStatModelToDo(item));
    }

    public ThroughputStat findThroughputStatByUnitId(ThroughputCondition condition) {
        Assert.isTrue(condition != null);
        ThroughputStatDO throughputStatDO = throughputStatRepository.findFirstByUnitIdAndTypeOrderByEndTimeDesc(condition.getUnitId(), condition.getType().name());
        ThroughputStat throughputStat = new ThroughputStat();
        if (throughputStatDO != null) {
            throughputStat = throughputStatDOToModel(throughputStatDO);
        }
        return throughputStat;
    }

    /**
     * 3种时间间隔的统计信息
     */

    public ThroughputInfo listRealtimeThroughput(ThroughputCondition condition) {
        Assert.isTrue(condition != null);
        Date realtime = new Date(System.currentTimeMillis());
        //10分钟
        int distance = 10;
        int minute = 1;
        Date from = new Date(realtime.getTime() - distance * 60 * 1000);
        Date end = realtime;
        List<ThroughputStatDO> throughputStatDOs = throughputStatRepository.findByUnitIdAndTypeAndEndTimeBetweenOrderByEndTimeDesc(condition.getUnitId(), condition.getType().name(), from, end);
        ThroughputInfo throughputInfo = new ThroughputInfo();
        List<ThroughputStat> throughputStat = new ArrayList<ThroughputStat>();
        for (ThroughputStatDO throughputStatDO : throughputStatDOs) {
            if (realtime.getTime() - throughputStatDO.getEndTime().getTime() <= distance * 60 * 1000) {
                throughputStat.add(throughputStatDOToModel(throughputStatDO));
            }
        }
        throughputInfo.setItems(throughputStat);
        //1分钟
        throughputInfo.setSeconds(minute * 60L);
        return throughputInfo;

    }

    @Override
    public Highcharts listTimeLineThroughtForView() {
        TimelineThroughputCondition condition = new TimelineThroughputCondition();
        Date to = new Date();
        condition.setStart(new Date(to.getTime() - 10 * 60 * 1000));
        condition.setEnd(to);
        condition.setUnitId(101l);
        Highcharts kavs = new Highcharts();
        List<Series> seriesList = new ArrayList<>();
        for (ThroughputType throughputType : ThroughputType.values()) {
            condition.setType(throughputType);
            Map<Long, ThroughputInfo> infos = listTimelineThroughput(condition);
            Series series = new Series();
            series.setName(throughputType.name());
            List<Long> vs = new ArrayList<>();
            List<String> xds = new ArrayList<>();
            for (Map.Entry<Long, ThroughputInfo> entry : infos.entrySet()) {
                vs.add(entry.getValue().getNumber());
                xds.add(new Date(entry.getKey()).toString());
            }
            series.setData(vs);
            seriesList.add(series);
            if (!xds.isEmpty()) {
                kavs.setXaxis(xds);
            }
        }

        kavs.setSeries(seriesList);
        return kavs;
    }

    /**
     * <pre>
     * 列出unitId下，start-end时间段下的throughputStat
     * 首先从数据库中取出这一段时间所以数据，该数据都是根据end_time倒排序的, 每隔1分钟将这些数据分组
     * </pre>
     */
    public Map<Long, ThroughputInfo> listTimelineThroughput(TimelineThroughputCondition condition) {
        Assert.isTrue(condition != null);
        Map<Long, ThroughputInfo> throughputInfos = new LinkedHashMap<Long, ThroughputInfo>();
        List<ThroughputStatDO> throughputStatDOs = throughputStatRepository.findByUnitIdAndTypeAndEndTimeBetweenOrderByEndTimeDesc(condition.getUnitId(), condition.getType().name(), condition.getStart(), condition.getEnd());
        int size = throughputStatDOs.size();
        int k = size - 1;
        for (Long i = condition.getStart().getTime(); i <= condition.getEnd().getTime(); i += 60 * 1000) {
            ThroughputInfo throughputInfo = new ThroughputInfo();
            List<ThroughputStat> throughputStat = new ArrayList<ThroughputStat>();
            // 取出每个时间点i以内的数据，k是一个游标，每次遍历时前面已经取过了的数据就不用再遍历了
            for (int j = k; j >= 0; --j) {
                if ((i - throughputStatDOs.get(j).getEndTime().getTime() <= 60 * 1000)
                        && (i - throughputStatDOs.get(j).getEndTime().getTime() >= 0)) {
                    throughputStat.add(throughputStatDOToModel(throughputStatDOs.get(j)));
                    k = j - 1;
                }// 如果不满足if条件，则后面的数据也不用再遍历
                else {
                    break;
                }
            }
            if (throughputStat.size() > 0) {
                throughputInfo.setItems(throughputStat);
                throughputInfo.setSeconds(1 * 60L);
                throughputInfos.put(i, throughputInfo);
            }
        }
        return throughputInfos;
    }

    //    public List<ThroughputStat> listRealtimeThroughputByPipelineIds(List<Long> pipelineIds, int minute) {
//        Assert.isTrue(pipelineIds != null);
//        List<ThroughputStatDO> throughputStatDOs = throughputDao.listRealTimeThroughputStatByPipelineIds(pipelineIds,
//                minute);
//
//        List<ThroughputStat> infos = new ArrayList<ThroughputStat>();
//        for (ThroughputStatDO throughputStatDO : throughputStatDOs) {
//            infos.add(throughputStatDOToModel(throughputStatDO));
//        }
//
//        return infos;
//    }

    /**
     * 用于Model对象转化为DO对象
     *
     * @param throughputStat
     * @return throughputStatDO
     */
    private ThroughputStatDO throughputStatModelToDo(ThroughputStat throughputStat) {
        ThroughputStatDO throughputStatDO = CopyPropertyUtils.copyPropertiesAndInstance(throughputStat, ThroughputStatDO.class);
        throughputStatDO.setType(throughputStat.getTypeEnum().name());
        return throughputStatDO;
    }

    /**
     * 用于DO对象转化为Model对象
     *
     * @param throughputStatDO
     * @return throughputStat
     */
    private ThroughputStat throughputStatDOToModel(ThroughputStatDO throughputStatDO) {
        ThroughputStat throughputStat = CopyPropertyUtils.copyPropertiesAndInstance(throughputStatDO, ThroughputStat.class);
        throughputStat.setTypeEnum(ThroughputType.valueOf(throughputStatDO.getType()));
        return throughputStat;
    }

}
