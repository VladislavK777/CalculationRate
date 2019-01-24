package com.uraltranscom.calculaterate.model_ex;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 24.01.2019
 */

@Data
@ToString
public class TotalListModels {
    private List<TotalModel> totalModelList;
}
