package com.uraltranscom.calculaterate.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 21.12.2018
 *
 * 21.12.2018
 *   1. Версия 1.0
 *
 */

import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;
import com.uraltranscom.calculaterate.service.impl.BasicClassImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller
public class Controller {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private BasicClassImpl basicClassImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "routesFile") MultipartFile routesFile, Model model) {

        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/exportWagons", method = RequestMethod.GET)
    public void getXLSWagons(HttpServletResponse response, Model model) {
        basicClassImpl.getClassHandlerLookingFor().fillFinalMapByOrders();
        WriteToFileExcel.downloadWagonsFileExcel(response, basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
    }
}
