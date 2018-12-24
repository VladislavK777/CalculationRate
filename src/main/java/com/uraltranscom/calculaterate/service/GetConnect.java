package com.uraltranscom.calculaterate.service;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 24.12.2018
 */

public interface GetConnect<T> {
    T getDateFromDB(T params, String predict);
}
