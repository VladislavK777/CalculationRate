package com.uraltranscom.calculaterate.util.ZookeeperUtil;

import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 *
 * Все настройки, значения которых приложение получает из Zookeeper
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

public class ZookeeperSettingHolder implements InitializingBean {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(ZookeeperSettingHolder.class);

    private static final String ZOOKEEPER_CHARSET_NAME = "UTF-8";
    private static final String DB_SETTINGS_ROOT = "/zookeeper/DB_CONNECT";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface ZookeeperSettings {
        String value();
    }

    @ZookeeperSettings("database_test")
    private String dataBase;

    @ZookeeperSettings("user")
    private String user;

    @ZookeeperSettings("password")
    private String password;

    private String secretKey;

    private String formatPath(String root, String propertyName) {
        return  root + "/" + propertyName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String zookeeperHost = JavaHelperBase.ZOOKEEPER_HOST;
        secretKey = JavaHelperBase.ZOOKEEPER_SECRET_KEY;

        try (CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperHost, new ExponentialBackoffRetry(1000,3))) {
            client.start();
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ZookeeperSettings.class))
                    processProperty(field, client);
            }
        }
    }

    private void processProperty(Field field, CuratorFramework client) throws Exception {
        String propertyName = field.getAnnotation(ZookeeperSettings.class).value();
        String path = formatPath(DB_SETTINGS_ROOT, propertyName);
        field.set(this, new String(client.getData().forPath(path), ZOOKEEPER_CHARSET_NAME));
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
