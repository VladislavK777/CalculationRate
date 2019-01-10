package com.uraltranscom.calculaterate.model_ex;

import com.uraltranscom.calculaterate.model.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 25.12.2018
 */

@Getter
@AllArgsConstructor
public class TotalModel {
    List<Route> exitList = new ArrayList<>();
}
