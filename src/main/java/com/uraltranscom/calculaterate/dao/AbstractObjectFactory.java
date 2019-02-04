package com.uraltranscom.calculaterate.dao;

import lombok.Getter;

import java.util.Map;

/**
 * @author Vladislav Klochkov
 * @create 2018-12-25
 */

@Getter
public abstract class AbstractObjectFactory<T> implements ObjectFactory {
    @Override
    public abstract T getObject(Map<String, Object> params);
}
