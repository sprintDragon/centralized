package org.sprintdragon.centralized.node.dao;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.info.DbInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdi on 17-2-23.
 */
@Slf4j
@Service
public class DynamicDataSourceManager implements InitializingBean {

    private Map<String, DruidDataSource> dataSourceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Unit unit = ArbitrateConfigRegistry.getConfig().currentUnit();
        addDataSourceByUnit(unit);
    }

    public void addDataSourceByUnit(Unit unit) throws Exception {
        if (dataSourceMap.containsKey(unit.getUnitId())) {
            log.warn("已有该数据库连接池，不予添加,unitId={}", unit.getUnitId());
            return;
        } else {
            DruidDataSource source = new DruidDataSource();
            source.setDriverClassName("com.mysql.jdbc.Driver");
            DbInfo dbInfo = unit.getDbInfo();
            source.setUrl(dbInfo.getUrl());
            source.setUsername(dbInfo.getName());
            source.setPassword(dbInfo.getPwd());
            dataSourceMap.put(unit.getUnitId(), source);
        }
    }

    public JdbcTemplate getJdbcTemplateByUnitId(String unitId) throws Exception {
        DruidDataSource druidDataSource = dataSourceMap.get(unitId);
        if (druidDataSource == null) {
            throw new Exception("未找到数据源");
        } else {
            return new JdbcTemplate(druidDataSource);
        }
    }

    public void close() {
        for (Map.Entry<String, DruidDataSource> entry : dataSourceMap.entrySet()) {
            try {
                entry.getValue().close();
            } catch (Exception e) {
                log.error("关闭数据库连接池失败,unitId={}", entry.getKey(), e);
            }
        }
    }

}
