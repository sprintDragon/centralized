package org.sprintdragon.centralized.node.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sprintdragon.centralized.shared.arbitrate.event.UnitOperateEvent;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.info.DbInfo;

import javax.annotation.Resource;

/**
 * Created by wangdi on 17-2-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF.spring/spring-config-zk.xml"})
public class TestUnitEvent {

    @Resource
    UnitOperateEvent unitOperateEvent;

    @Test
    public void testAdd() throws Exception {
        Unit u1 = new Unit();
        DbInfo dbInfo = new DbInfo();
        dbInfo.setUrl("jdbc:mysql://192.168.1.2:3306/teleport?characterEncoding=utf-8");
        dbInfo.setName("root");
        dbInfo.setPwd("808080wd1");
        u1.setUnitId("db1");
        u1.setDbInfo(dbInfo);
        unitOperateEvent.upsert(u1);
        dbInfo = new DbInfo();
        dbInfo.setUrl("jdbc:mysql://192.168.153.36:3306/pop_customs?characterEncoding=utf-8");
        dbInfo.setName("root");
        dbInfo.setPwd("123");
        u1.setUnitId("db2");
        u1.setDbInfo(dbInfo);
        unitOperateEvent.upsert(u1);
    }

    @Test
    public void testStart() throws Exception {
        unitOperateEvent.start();
    }
}
