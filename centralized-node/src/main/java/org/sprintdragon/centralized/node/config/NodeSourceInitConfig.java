package org.sprintdragon.centralized.node.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigImpl;
import org.sprintdragon.centralized.shared.arbitrate.event.NodeOperateEvent;
import org.sprintdragon.centralized.shared.arbitrate.event.UnitOperateEvent;
import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.Unit;
import org.sprintdragon.centralized.shared.model.enums.MsType;
import org.sprintdragon.centralized.shared.model.enums.NodeStatus;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;
import org.sprintdragon.centralized.shared.utils.SystemIpUtil;
import org.sprintdragon.centralized.shared.arbitrate.config.ArbitrateConfigRegistry;
import org.sprintdragon.centralized.shared.model.builder.NodeBuilder;
import org.sprintdragon.centralized.shared.model.info.DbInfo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wangdi on 17-2-21.
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.sprintdragon.centralized")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Slf4j
public class NodeSourceInitConfig {

    private static final String DATABASE_DRIVER = "db.driver";
    private static final String PACKAGES_TO_SCAN = "packages.to.scan";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";

    @Resource
    private Environment env;
    @Resource
    UnitOperateEvent unitOperateEvent;
    @Resource
    NodeOperateEvent nodeOperateEvent;

    @PostConstruct
    public void init() throws Exception {
        Unit unit = unitOperateEvent.pickUnit();
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName("本机名1");
        serverInfo.setDescription("描述1");
        serverInfo.setIp(SystemIpUtil.getRealIp());
        Node node = new NodeBuilder().unit(unit).msType(MsType.SLAVE).status(NodeStatus.INIT).serverInfo(serverInfo).build();
        //初始化
        nodeOperateEvent.upsert(node);
        new ArbitrateConfigImpl(unit, node);
    }


    @Bean
    public DataSource dataSource() {
        try {
            Unit unit = ArbitrateConfigRegistry.getConfig().currentUnit();
            DruidDataSource source = new DruidDataSource();
            source.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));

            DbInfo dbInfo = unit.getDbInfo();
            source.setUrl(dbInfo.getUrl());
            source.setUsername(dbInfo.getName());
            source.setPassword(dbInfo.getPwd());
            return source;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan(env.getRequiredProperty(PACKAGES_TO_SCAN));
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        return properties;
    }

}
