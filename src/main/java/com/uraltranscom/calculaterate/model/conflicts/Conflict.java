package com.uraltranscom.calculaterate.model.conflicts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 26.01.2019
 */

@Data
@AllArgsConstructor
@ToString
public class Conflict {
    private String conflictCode;
    private String conflictType;
    private String conflictMessage;
}
