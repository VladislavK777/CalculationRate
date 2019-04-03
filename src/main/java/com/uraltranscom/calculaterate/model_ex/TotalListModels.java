package com.uraltranscom.calculaterate.model_ex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 24.01.2019
 */

@Data
@ToString
@AllArgsConstructor
public class TotalListModels {
    private ArrayList<TotalModel> totalModelList;
}
