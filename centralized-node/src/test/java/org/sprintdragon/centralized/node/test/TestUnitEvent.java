package org.sprintdragon.centralized.node.test;

import org.junit.Test;
import org.junit.runner.RunWith;
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
        dbInfo.setUrl("jdbc:mysql://192.168.1.2:3306/teleport?useSSL=false");
        dbInfo.setName("root");
        dbInfo.setPwd("808080wd");
        u1.setUnitId(101l);
        u1.setDbInfo(dbInfo);
        unitOperateEvent.upsert(u1);
        dbInfo = new DbInfo();
        dbInfo.setUrl("jdbc:mysql://192.168.153.36:3306/pop_customs?useSSL=false");
        dbInfo.setName("root");
        dbInfo.setPwd("123");
        u1.setUnitId(102l);
        u1.setDbInfo(dbInfo);
        unitOperateEvent.upsert(u1);
    }

    @Test
    public void testStart() throws Exception {
        unitOperateEvent.start();
    }
}
