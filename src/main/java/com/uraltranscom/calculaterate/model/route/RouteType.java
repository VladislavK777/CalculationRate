package com.uraltranscom.calculaterate.model.route;

import lombok.Getter;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 24.12.2018
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
