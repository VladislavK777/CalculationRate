package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.setting.*;
import com.uraltranscom.calculaterate.model.settings.*;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 16.01.2019
 */

@RestController
@RequestMapping("settings")
public class RestControllerSettings {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSettings.class);

    @Autowired
    private GetSettingReturnStationsDAO getSettingReturnStationsDAO;
    @Autowired
    private GetSettingReturnExceptionsDAO getSettingReturnExceptionsDAO;
    @Autowired
    private GetSettingBeginningExceptionsDAO getSettingBeginningExceptionsDAO;
    @Autowired
    private GetSettingBorderDistanceDAO getSettingBorderDistanceDAO;
    @Autowired
    private GetSettingLoadUnloadDAO getSettingLoadUnloadDAO;
    @Autowired
    private GetSettingYieldDAO getSettingYieldDAO;
    @Autowired

    @GetMapping
    public ModelAndView setting() {
        ModelAndView mav = new ModelAndView("settings");

        Map<String, List<SettingReturnStations>> mapReturnStations = getSettingReturnStationsDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("mapReturnStations", mapReturnStations);

        Map<String, List<SettingReturnExceptions>> mapReturnException = getSettingReturnExceptionsDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("mapReturnException", mapReturnException);

        Map<String, List<SettingReturnExceptions>> mapBeginningException = getSettingBeginningExceptionsDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("mapBeginningException", mapBeginningException);

        List<SettingBorderDistance> listBorderDistance = getSettingBorderDistanceDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("listBorderDistance", listBorderDistance);

        List<SettingLoadUnload> listLoadUnload = getSettingLoadUnloadDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("listLoadUnload", listLoadUnload);

        List<SettingYield> listYield = getSettingYieldDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        mav.addObject("listYield", listYield);
        return mav;
    }
}
