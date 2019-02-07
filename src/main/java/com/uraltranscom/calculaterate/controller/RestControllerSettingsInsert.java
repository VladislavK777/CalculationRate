package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingBeginningExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingReturnExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingReturnStationsDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneBeginningExceptionDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneReturnExceptionDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneReturnStationDAO;
import com.uraltranscom.calculaterate.model.settings.SettingReturnExceptions;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@RestController
@RequestMapping("add")
public class RestControllerSettingsInsert {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSettingsInsert.class);

    @Autowired
    private InsertSettingReturnStationsDAO insertSettingReturnStationsDAO;
    @Autowired
    private InsertSettingReturnExceptionsDAO insertSettingReturnExceptionsDAO;
    @Autowired
    private InsertSettingBeginningExceptionsDAO insertSettingBeginningExceptionsDAO;
    @Autowired
    private CloneReturnStationDAO cloneReturnStationDAO;
    @Autowired
    private CloneReturnExceptionDAO cloneReturnExceptionDAO;
    @Autowired
    private CloneBeginningExceptionDAO cloneBeginningExceptionDAO;

    @PostMapping("/addReturnStation")
    public void addReturnStation(@RequestBody SettingReturnStations settingReturnStations) {
        insertSettingReturnStationsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnStations.getIdsRoad(),
                        settingReturnStations.getNamesRoad(),
                        settingReturnStations.getIdsStationString(),
                        settingReturnStations.getVolumeGroupsString(),
                        settingReturnStations.getIdStationReturn()
                )
        );
    }

    @PostMapping("/addReturnException")
    public void addReturnException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        insertSettingReturnExceptionsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getIdsRoad(),
                        settingReturnExceptions.getNamesRoad(),
                        settingReturnExceptions.getIdsStationString(),
                        settingReturnExceptions.getVolumeGroupsString(),
                        settingReturnExceptions.getStationFrom().getIdStation(),
                        settingReturnExceptions.getStationTo().getIdStation(),
                        settingReturnExceptions.getCargo().getIdCargo(),
                        settingReturnExceptions.getCargoTypeString(),
                        settingReturnExceptions.getRouteType(),
                        settingReturnExceptions.getDistance(),
                        settingReturnExceptions.getCountDays(),
                        settingReturnExceptions.getRate(),
                        settingReturnExceptions.getTariff()
                )
        );
    }

    @PostMapping("/addBeginningException")
    public void addBeginningException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        insertSettingBeginningExceptionsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getIdsRoad(),
                        settingReturnExceptions.getNamesRoad(),
                        settingReturnExceptions.getIdsStationString(),
                        settingReturnExceptions.getVolumeGroupsString(),
                        settingReturnExceptions.getStationFrom().getIdStation(),
                        settingReturnExceptions.getStationTo().getIdStation(),
                        settingReturnExceptions.getCargo().getIdCargo(),
                        settingReturnExceptions.getCargoTypeString(),
                        settingReturnExceptions.getRouteType(),
                        settingReturnExceptions.getDistance(),
                        settingReturnExceptions.getCountDays(),
                        settingReturnExceptions.getRate(),
                        settingReturnExceptions.getTariff()
                )
        );
    }

    @GetMapping("/cloneReturnStation/{id}")
    public SettingReturnStations cloneReturnStation(@PathVariable int id) {
        return cloneReturnStationDAO.getObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @GetMapping("/cloneReturnException/{id}")
    public SettingReturnExceptions cloneReturnException(@PathVariable int id) {
        return cloneReturnExceptionDAO.getObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @GetMapping("/cloneBeginningException/{id}")
    public SettingReturnExceptions cloneBeginningException(@PathVariable int id) {
        return cloneBeginningExceptionDAO.getObject(PrepareMapParams.prepareMapWithParams(id));
    }
}
