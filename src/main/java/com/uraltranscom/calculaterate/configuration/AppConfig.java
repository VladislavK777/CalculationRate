package com.uraltranscom.calculaterate.configuration;

import com.uraltranscom.calculaterate.model.cache.CacheSearch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 25.01.2019
 */


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.uraltranscom.calculaterate" })
@Import({DBConfig.class})
public class AppConfig implements WebMvcConfigurer {

    public AppConfig() {
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

   /* public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }*/

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(10000000);
        return commonsMultipartResolver;
    }

    @Bean(initMethod = "init", name = "cacheStationsSearch")
    public CacheSearch cacheStationsSearch() {
        return new CacheSearch("station");
    }

    @Bean(initMethod = "init", name = "cacheRoadSearch")
    public CacheSearch cacheRoadSearch() {
        return new CacheSearch("road");
    }

    @Bean(initMethod = "init", name = "cacheCargoSearch")
    public CacheSearch cacheCargoSearch() {
        return new CacheSearch("cargo");
    }

    @Bean(initMethod = "init", name = "cacheDepartmentsStationSearch")
    public CacheSearch cacheDepartmentsStationSearch() {
        return new CacheSearch("department_station");
    }
}
