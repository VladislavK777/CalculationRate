package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.DeleteSettingBeginningExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.DeleteSettingReturnExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.DeleteSettingReturnStationsDAO;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@RestController
@RequestMapping("delete")
public class RestControllerSettingsDelete {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSettingsDelete.class);

    @Autowired
    private DeleteSettingReturnStationsDAO deleteSettingReturnStationsDAO;
    @Autowired
    private DeleteSettingReturnExceptionsDAO deleteSettingReturnExceptionsDAO;
    @Autowired
    private DeleteSettingBeginningExceptionsDAO deleteSettingBeginningExceptionsDAO;

    @DeleteMapping("/deleteReturnStations/{id}")
    public void deleteReturnStations(@PathVariable int id) {
        deleteSettingReturnStationsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @DeleteMapping("/deleteReturnExceptions/{id}")
    public void deleteReturnExceptions(@PathVariable int id) {
        deleteSettingReturnExceptionsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @DeleteMapping("/deleteBeginningExceptions/{id}")
    public void deleteBeginningExceptions(@PathVariable int id) {
        deleteSettingBeginningExceptionsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }
}
