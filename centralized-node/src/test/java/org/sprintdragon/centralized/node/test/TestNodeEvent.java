package org.sprintdragon.centralized.node.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sprintdragon.centralized.shared.arbitrate.event.NodeOperateEvent;

import javax.annotation.Resource;

/**
 * Created by wangdi on 17-2-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF.spring/spring-config-zk.xml"})
public class TestNodeEvent {

    @Resource
    NodeOperateEvent nodeOperateEvent;

    @Test
    public void testAdd() throws Exception {
//        nodeOperateEvent.add();
    }
}
