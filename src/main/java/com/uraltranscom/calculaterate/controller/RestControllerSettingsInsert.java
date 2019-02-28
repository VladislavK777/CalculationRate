package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingBeginningExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingReturnExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.add.InsertSettingReturnStationsDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneBeginningExceptionDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneReturnExceptionDAO;
import com.uraltranscom.calculaterate.dao.setting.clone.CloneReturnStationDAO;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.settings.SettingReturnExceptions;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.CheckMandatoryParams;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

//@CrossOrigin(origins = "*")
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
    public ResponseEntity<Conflict> addReturnStation(@RequestBody SettingReturnStations settingReturnStations) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnStations, "namesRoad", "volumeGroupsString", "idStationReturn");
        if (conflict == null) {
            conflict = (Conflict) insertSettingReturnStationsDAO.insertObject(
                    PrepareMapParams.prepareMapWithParams(
                            settingReturnStations.getNamesRoad(),
                            settingReturnStations.getIdsStationString(),
                            settingReturnStations.getIdsDepartment(),
                            settingReturnStations.getNamesDepartment(),
                            settingReturnStations.getVolumeGroupsString(),
                            settingReturnStations.getIdStationReturn()
                    )
            );
            if (conflict == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(conflict, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addReturnException")
    public ResponseEntity<Conflict> addReturnException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnExceptions, "namesRoad", "volumeGroupsString", "stationFrom", "stationTo", "cargo", "cargoTypeString", "routeType", "distance", "rate", "tariff");
        if (conflict == null) {
            conflict = (Conflict) insertSettingReturnExceptionsDAO.insertObject(
                    PrepareMapParams.prepareMapWithParams(
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
            if (conflict == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(conflict, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addBeginningException")
    public ResponseEntity<Conflict> addBeginningException(@RequestBody SettingReturnExceptions settingReturnExceptions) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(settingReturnExceptions, "namesRoad", "volumeGroupsString", "stationFrom", "stationTo", "cargo", "cargoTypeString", "routeType", "distance", "rate", "tariff");
        if (conflict == null) {
            conflict = (Conflict) insertSettingBeginningExceptionsDAO.insertObject(
                    PrepareMapParams.prepareMapWithParams(
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
            if (conflict == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(conflict, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
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
