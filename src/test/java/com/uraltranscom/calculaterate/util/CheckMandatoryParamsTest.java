package com.uraltranscom.calculaterate.util;

import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 13.02.2019
 */

public class CheckMandatoryParamsTest {
    private Conflict conflict;
    private SettingReturnStations obj = new SettingReturnStations(0, "ВСБ", "", null, null, "120", null, null);

    @Test
    public void testCheckMandatoryParams() {
        conflict = CheckMandatoryParams.checkMandatoryParams(obj, "idStationReturn");
        Assert.assertNotNull("It's not ok", conflict);
    }

}
