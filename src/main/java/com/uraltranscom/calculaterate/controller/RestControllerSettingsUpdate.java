package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.*;
import com.uraltranscom.calculaterate.model.settings.*;
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
@RequestMapping("update")
public class RestControllerSettingsUpdate {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSettingsUpdate.class);

    @Autowired
    private UpdateSettingYieldDAO updateSettingYieldDAO;
    @Autowired
    private UpdateSettingBorderDistanceDAO updateSettingBorderDistanceDAO;
    @Autowired
    private UpdateSettingLoadUnloadDAO updateSettingLoadUnloadDAO;
    @Autowired
    private UpdateSettingReturnStationsDAO updateSettingReturnStationsDAO;
    @Autowired
    private UpdateSettingReturnExcetpionsDAO updateSettingReturnExcetpionsDAO;
    @Autowired
    private UpdateSettingBeginningExcetpionsDAO updateSettingBeginningExcetpionsDAO;

    @PutMapping("/updateYield")
    public void updateYield(@RequestBody SettingYield settingYield) {
        updateSettingYieldDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingYield.getId(),
                        settingYield.getYield()
                )
        );
    }

    @PutMapping("/updateBorderDistance")
    public void updateBorderDistance(@RequestBody SettingBorderDistance settingBorderDistance) {
        updateSettingBorderDistanceDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingBorderDistance.getId(),
                        settingBorderDistance.getDistanceFrom(),
                        settingBorderDistance.getDistanceTo(),
                        settingBorderDistance.getCoefficient()
                )
        );
    }

    @PutMapping("/updateLoadUnload")
    public void updateLoadUnload(@RequestBody SettingLoadUnload settingLoadUnload) {
        updateSettingLoadUnloadDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingLoadUnload.getId(),
                        settingLoadUnload.getValue()
                )
        );
    }

    @PutMapping("/updateReturnStations")
    public void updateReturnStations(@RequestBody SettingReturnStations settingReturnStations) {
        updateSettingReturnStationsDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnStations.getId(),
                        settingReturnStations.getRoad().getIdRoad(),
                        settingReturnStations.getIdStationString(),
                        settingReturnStations.getVolumeGroupsString(),
                        settingReturnStations.getIdStationReturn()
                )
        );
    }

    @PutMapping("/updateReturnExceptions")
    public void updateReturnExceptions(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        updateSettingReturnExcetpionsDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getId(),
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

    @PutMapping("/updateBeginningExceptions")
    public void updateBeginningExceptions(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        updateSettingBeginningExcetpionsDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingReturnExceptions.getId(),
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
