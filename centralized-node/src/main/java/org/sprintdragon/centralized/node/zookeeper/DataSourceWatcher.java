package org.sprintdragon.centralized.node.zookeeper;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.sprintdragon.centralized.node.dao.DynamicDataSourceManager;
import org.sprintdragon.centralized.shared.arbitrate.helper.ManagePathUtils;
import org.sprintdragon.centralized.shared.model.Unit;

import javax.annotation.Resource;

/**
 * Created by wangdi on 17-2-23.
 */
@Slf4j
public class DataSourceWatcher extends ZKBase {

    @Resource
    DynamicDataSourceManager dynamicDataSourceManager;

    public DataSourceWatcher(CuratorFramework curatorFramework) {
        super(curatorFramework);
    }

    public void watch() throws Exception {
        String path = ManagePathUtils.getUnitInfoRootPath();
        PathChildrenCache watcher = new PathChildrenCache(curatorFramework, path, true);
        watcher.getListenable().addListener((client, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                log.info("No data in event[" + event + "]");
            } else {
                Unit unit = JSON.parseObject(data.getData(), Unit.class);
                log.info("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + data.getPath() + "]"
                        + ", data=[" + new String(data.getData()) + "]"
                        + ", stat=[" + data.getStat() + "]");
                switch (event.getType()) {
                    case CHILD_ADDED:
                        log.info("watch CHILD_ADDED");
                        dynamicDataSourceManager.addDataSourceByUnit(unit);
                        break;
                    case CHILD_REMOVED:
                        log.info("watch CHILD_REMOVED");
                        dynamicDataSourceManager.removeDataSourceByUnitId(unit.getUnitId());
                        break;
                    case CHILD_UPDATED:
                        log.info("watch CHILD_UPDATE 不处理");
                        break;
                    default:
                }
            }
        });
        try {
            watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            log.error("watch error", e);
        }
        log.info("Register zk watcher successfully!");
    }

    @Override
    protected void call() throws Exception {
        watch();
    }

}
