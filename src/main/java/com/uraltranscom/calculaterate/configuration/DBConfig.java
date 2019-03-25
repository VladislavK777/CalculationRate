package com.uraltranscom.calculaterate.configuration;

import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import com.uraltranscom.calculaterate.util.zookeeper.ZookeeperSettingHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 04.02.2019
 */

@Configuration
@ComponentScan(basePackages = { "com.uraltranscom.calculaterate" })
public class DBConfig {
    private static Logger logger = LoggerFactory.getLogger(DBConfig.class);

    @Bean
    @Primary
    public ZookeeperSettingHolder zookeeperSettingHolder() {
        return new ZookeeperSettingHolder();
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() throws Exception {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(com.uraltranscom.calculaterate.util.KeyMaster.class);
        methodInvokingFactoryBean.setTargetMethod("dec");
        methodInvokingFactoryBean.setArguments(new String[] {zookeeperSettingHolder().getPassword(), zookeeperSettingHolder().getSecretKey()});
        methodInvokingFactoryBean.afterPropertiesSet();
        return methodInvokingFactoryBean;
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() throws Exception {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(zookeeperSettingHolder().getDataBase());
        config.setUsername(zookeeperSettingHolder().getUser());
        config.setPassword((String) methodInvokingFactoryBean().getObject());
        config.setMaximumPoolSize(20);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public ConnectionDB connectionDB() throws Exception {
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.setDataSource(dataSource());
        return connectionDB;
    }
}
