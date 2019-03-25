package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.update.*;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.settings.*;
import com.uraltranscom.calculaterate.util.CheckMandatoryParams;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 16.01.2019
 */

//@CrossOrigin(origins = "*")
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
    private UpdateSettingReturnExceptionsDAO updateSettingReturnExceptionsDAO;
    @Autowired
    private UpdateSettingBeginningExceptionsDAO updateSettingBeginningExceptionsDAO;
    @Autowired
    private UpdateSettingOtherDAO updateSettingOtherDAO;

    @PutMapping("/updateYield")
    public void updateYield(@RequestBody SettingYield settingYield) {
        updateSettingYieldDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingYield.getId(),
                        settingYield.getYield()
                )
        );
    }

    @PutMapping("/updateOther")
    public void updateOther(@RequestBody SettingOther settingOther) {
        updateSettingOtherDAO.updateObject(
                PrepareMapParams.prepareMapWithParams(
                        settingOther.getId(),
                        settingOther.getValue()
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

    @PutMapping("/updateReturnStation")
    public ResponseEntity<?> updateReturnStation(@RequestBody SettingReturnStations settingReturnStations) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnStations, "namesRoad", "volumeGroupsString", "idStationReturn");
        if (conflict == null) {
            updateSettingReturnStationsDAO.updateObject(
                    PrepareMapParams.prepareMapWithParams(
                            settingReturnStations.getId(),
                            settingReturnStations.getNamesRoad(),
                            settingReturnStations.getIdsStationString(),
                            settingReturnStations.getIdsDepartment(),
                            settingReturnStations.getNamesDepartment(),
                            settingReturnStations.getVolumeGroupsString(),
                            settingReturnStations.getIdStationReturn()
                    )
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateReturnException")
    public ResponseEntity<?> updateReturnException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnExceptions, "namesRoad", "volumeGroupsString", "stationFrom", "stationTo", "cargo", "cargoTypeString", "routeType", "distance", "rate", "tariff");
        if (conflict == null) {
            updateSettingReturnExceptionsDAO.updateObject(
                    PrepareMapParams.prepareMapWithParams(
                            settingReturnExceptions.getId(),
                            settingReturnExceptions.getNamesRoad(),
                            settingReturnExceptions.getIdsStationString(),
                            settingReturnExceptions.getIdsDepartment(),
                            settingReturnExceptions.getNamesDepartment(),
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
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBeginningException")
    public ResponseEntity<?> updateBeginningException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnExceptions, "namesRoad", "volumeGroupsString", "stationFrom", "stationTo", "cargo", "cargoTypeString", "routeType", "distance", "rate", "tariff");
        if (conflict == null) {
            updateSettingBeginningExceptionsDAO.updateObject(
                    PrepareMapParams.prepareMapWithParams(
                            settingReturnExceptions.getId(),
                            settingReturnExceptions.getNamesRoad(),
                            settingReturnExceptions.getIdsStationString(),
                            settingReturnExceptions.getIdsDepartment(),
                            settingReturnExceptions.getNamesDepartment(),
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
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }
}
