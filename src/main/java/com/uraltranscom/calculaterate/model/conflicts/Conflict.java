package com.uraltranscom.calculaterate.model.conflicts;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 26.01.2019
 */

@Data
@ToString
public class Conflict {
    private String conflictCode;
    private String conflictType;
    private String conflictMessage;
    private List<String> conflictCodes;

    public Conflict(String conflictCode, String conflictType, String conflictMessage) {
        this.conflictCode = conflictCode;
        this.conflictType = conflictType;
        this.conflictMessage = conflictMessage;
    }

    public Conflict(List<String> conflictCodes, String conflictType, String conflictMessage) {
        this.conflictCodes = conflictCodes;
        this.conflictType = conflictType;
        this.conflictMessage = conflictMessage;
    }
}
