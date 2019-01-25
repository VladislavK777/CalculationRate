package com.uraltranscom.calculaterate.configuration;

import com.uraltranscom.calculaterate.util.ZookeeperUtil.ZookeeperSettingHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 25.01.2019
 */

@Configuration
public class AppConfig {
    @Bean
    public ZookeeperSettingHolder zookeeperSettingHolder() {
        return new ZookeeperSettingHolder();
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        return new CommonsMultipartResolver();
    }

}
