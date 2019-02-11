import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.CheckMandatoryParams;

import java.io.IOException;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 09.02.2019
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
        CheckMandatoryParams checkMandatoryParams = new CheckMandatoryParams();
        Conflict conflict;
        SettingReturnStations settingReturnStations = new SettingReturnStations(0, "26", "СЕВ", "", "120", "121208", null);
        conflict = checkMandatoryParams.checkMandatoryParams(settingReturnStations, "namesRoad", "volumeGroupsString", "idStationReturn");
        System.out.println(conflict);
    }
}

