package com.uraltranscom.calculaterate.model;

import lombok.Getter;

/**
 *
 * Типы рейсов
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 24.12.2018
 *
 * 24.12.2018
 *   1. Версия 1.0
 *
 */

@Getter
public enum RouteType {
    FULL_ROUTE("ГРУЖ"),
    EMPTY_ROUTE("ПОР");

    private final String code;
    RouteType(String code) {
        this.code = code;
    }
}
