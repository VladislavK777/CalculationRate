package com.uraltranscom.calculaterate.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 13.02.2019
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ZookeeperSettings {
    String value();
}
