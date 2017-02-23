package org.sprintdragon.centralized.node.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;
import org.sprintdragon.centralized.shared.arbitrate.helper.ManagePathUtils;

/**
 * Created by wangdi on 17-2-23.
 */
@Slf4j
public class DataSourceWatcher extends ZKBase {

    public DataSourceWatcher(CuratorFramework curatorFramework) {
        super(curatorFramework);
    }

    public void watch() throws Exception {
        String path = ManagePathUtils.getUnitInfoPath(ArbitrateConfigRegistry.getConfig().currentUnit().getUnitId());
        NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        nodeCache.getListenable().addListener(() -> {
            ChildData data = nodeCache.getCurrentData();
            if (data == null) {
                log.warn("dataSource delete!! path={},data={},stat={}", data.getPath(), new String(data.getData()), data.getStat());
            } else {
                log.warn("dataSource update!! path={},data={},stat={}", data.getPath(), new String(data.getData()), data.getStat());
            }
        });
        nodeCache.start();
    }

    @Override
    protected void call() throws Exception {
        watch();
    }

}
