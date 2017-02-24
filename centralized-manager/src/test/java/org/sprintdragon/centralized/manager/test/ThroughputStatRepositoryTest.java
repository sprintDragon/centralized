package org.sprintdragon.centralized.manager.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sprintdragon.centralized.manager.ManagerApplication;
import org.sprintdragon.centralized.manager.statistics.throughput.repository.ThroughputStatDO;
import org.sprintdragon.centralized.manager.statistics.throughput.repository.ThroughputStatRepository;
import org.sprintdragon.centralized.shared.statistics.throughput.ThroughputType;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangdi on 17-2-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
public class ThroughputStatRepositoryTest {

    @Resource
    ThroughputStatRepository throughputStatRepository;

    @Test
    public void testInsert() throws Exception {
        ThroughputStatDO d = new ThroughputStatDO();
        d.setUnitId(1l);
        d.setType(ThroughputType.MEM.name());
        d.setCreate(new Date());
        d.setModified(new Date());
        d.setNumber(10l);
        d.setSize(10l);
        d.setStartTime(new Date());
        d.setEndTime(new Date());
        throughputStatRepository.save(d);
    }
}
