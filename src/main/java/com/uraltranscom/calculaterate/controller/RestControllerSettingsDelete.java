package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.delete.DeleteSettingBeginningExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.delete.DeleteSettingReturnExceptionsDAO;
import com.uraltranscom.calculaterate.dao.setting.delete.DeleteSettingReturnStationsDAO;
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

//@CrossOrigin(origins = "*")
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

    @DeleteMapping("/deleteReturnStation/{id}")
    public void deleteReturnStation(@PathVariable int id) {
        deleteSettingReturnStationsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @DeleteMapping("/deleteReturnException/{id}")
    public void deleteReturnException(@PathVariable int id) {
        deleteSettingReturnExceptionsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }

    @DeleteMapping("/deleteBeginningException/{id}")
    public void deleteBeginningException(@PathVariable int id) {
        deleteSettingBeginningExceptionsDAO.deleteObject(PrepareMapParams.prepareMapWithParams(id));
    }
}
