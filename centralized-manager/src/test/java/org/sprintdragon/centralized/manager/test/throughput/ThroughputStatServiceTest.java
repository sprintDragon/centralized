package org.sprintdragon.centralized.manager.test.throughput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sprintdragon.centralized.manager.ManagerApplication;
import org.sprintdragon.centralized.manager.statistics.throughput.ThroughputStatService;
import org.sprintdragon.centralized.manager.statistics.throughput.param.AnalysisType;
import org.sprintdragon.centralized.manager.statistics.throughput.param.RealtimeThroughputCondition;
import org.sprintdragon.centralized.manager.statistics.throughput.param.ThroughputInfo;
import org.sprintdragon.centralized.manager.statistics.throughput.param.TimelineThroughputCondition;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputStat;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputType;
import org.sprintdragon.centralized.shared.utils.CommonTimeUtils;

import javax.annotation.Resource;
import javax.management.timer.Timer;
import java.util.*;

/**
 * Created by wangdi on 17-2-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
public class ThroughputStatServiceTest {

    @Resource
    ThroughputStatService throughputStatService;

    Long unitId;
    Date fromDate;

    @Before
    public void setUp() throws Exception {
        unitId = 101l;
        fromDate = CommonTimeUtils.getDateFromStr("2017-2-27 10:10:00");
    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 100; i++) {
            ThroughputStat d = new ThroughputStat();
            d.setUnitId(unitId);
            d.setTypeEnum(ThroughputType.MEM);
            d.setCreate(new Date());
            d.setModified(new Date());
            d.setNumber((long) new Random().nextInt(100));
            d.setSize(10l);
            d.setStartTime(fromDate);
            Date to = new Date(fromDate.getTime() + 5 * Timer.ONE_SECOND);
            d.setEndTime(to);
            throughputStatService.createOrUpdateThroughput(d);
            fromDate = to;
        }
    }

    @Test
    public void testFind1() throws Exception {
        RealtimeThroughputCondition realtimeThroughputCondition = new RealtimeThroughputCondition();
        realtimeThroughputCondition.setUnitId(unitId);
        List<AnalysisType> types = new ArrayList<>();
        types.add(AnalysisType.ONE_MINUTE);
        types.add(AnalysisType.FIVE_MINUTE);
        types.add(AnalysisType.FIFTEEN_MINUTE);
        realtimeThroughputCondition.setType(ThroughputType.MEM);
        realtimeThroughputCondition.setAnalysisType(types);
        Map<AnalysisType, ThroughputInfo> map = throughputStatService.listRealtimeThroughput(realtimeThroughputCondition);
        for (Map.Entry<AnalysisType, ThroughputInfo> entry : map.entrySet()) {
            System.out.println("##" + entry);
        }
    }

    @Test
    public void testFindList() throws Exception {
        TimelineThroughputCondition timelineThroughputCondition = new TimelineThroughputCondition();
        timelineThroughputCondition.setUnitId(unitId);
        timelineThroughputCondition.setStart(CommonTimeUtils.getDateFromStr("2000-1-1 10:00:00"));
        timelineThroughputCondition.setEnd(new Date());
        timelineThroughputCondition.setType(ThroughputType.MEM);
        Map<Long, ThroughputInfo> map = throughputStatService.listTimelineThroughput(timelineThroughputCondition);
        for (Map.Entry<Long, ThroughputInfo> entry : map.entrySet()) {
            System.out.println("##" + entry);
        }
    }

    @Test
    public void testReal() throws Exception {
        System.out.println("##" + throughputStatService.listRealTimeThroughtForView());
    }
}
