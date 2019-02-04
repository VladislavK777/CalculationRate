package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.InsertSettingBeginningExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.InsertSettingReturnExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.InsertSettingReturnStationsDAO;
import com.uraltranscom.calculaterate.model.settings.SettingReturnExceptions;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@RestController
@RequestMapping("add")
@EnableAutoConfiguration
public class RestControllerSettingsInsert {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSettingsInsert.class);

    @Autowired
    private InsertSettingReturnStationsDAO insertSettingReturnStationsDAO;
    @Autowired
    private InsertSettingReturnExceptionsDAO insertSettingReturnExceptionsDAO;
    @Autowired
    private InsertSettingBeginningExceptionsDAO insertSettingBeginningExceptionsDAO;

    @PostMapping("/addReturnStations")
    public void addReturnStations(@RequestBody SettingReturnStations settingReturnStations) {
        insertSettingReturnStationsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnStations.getRoad().getIdRoad(),
                        settingReturnStations.getIdStationString(),
                        settingReturnStations.getVolumeGroupsString(),
                        settingReturnStations.getIdStationReturn()
                )
        );
    }

    @PostMapping("/addReturnExceptions")
    public void addReturnExceptions(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        insertSettingReturnExceptionsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getRoad().getIdRoad(),
                        settingReturnExceptions.getIdStationString(),
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

    @PostMapping("/addBeginningExceptions")
    public void addBeginningExceptions(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        insertSettingBeginningExceptionsDAO.insertObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getRoad().getIdRoad(),
                        settingReturnExceptions.getIdStationString(),
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
}
