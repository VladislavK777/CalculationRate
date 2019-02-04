package com.uraltranscom.calculaterate.configuration;

import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import com.uraltranscom.calculaterate.util.zookeeper.ZookeeperSettingHolder;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
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

    @Bean(name = "decodedPassword")
    public MethodInvokingFactoryBean methodInvokingFactoryBean() throws Exception {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(com.uraltranscom.calculaterate.util.KeyMaster.class);
        methodInvokingFactoryBean.setTargetMethod("dec");
        methodInvokingFactoryBean.setArguments(new String[] {zookeeperSettingHolder().getPassword(), zookeeperSettingHolder().getSecretKey()});
        methodInvokingFactoryBean.afterPropertiesSet();
        return methodInvokingFactoryBean;
    }

    @Bean(destroyMethod = "close")
    @Lazy
    public DataSource dataSource() throws Exception {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(zookeeperSettingHolder().getDataBase());
        dataSource.setUsername(zookeeperSettingHolder().getUser());
        dataSource.setPassword((String) methodInvokingFactoryBean().getObject());
        return dataSource;
    }

    @Bean
    public ConnectionDB connectionDB() throws Exception {
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.setDataSource(dataSource());
        return connectionDB;
    }
}
