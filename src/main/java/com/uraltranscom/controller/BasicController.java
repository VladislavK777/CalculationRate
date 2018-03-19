package com.uraltranscom.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 4.0
 * @create 12.01.2018
 *
 * 12.01.2018
 *   1. Версия 3.0
 * 14.03.2018
 *   1. Версия 4.0
 *
 */

import com.uraltranscom.service.MethodOfBasicLogic;
import com.uraltranscom.service.export.WriteToFileExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unchecked")
@Controller
public class BasicController {

    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private MethodOfBasicLogic methodOfBasicLogic;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String reportList(@RequestParam(value = "routes") MultipartFile routeFile,
                             @RequestParam(value = "wagons") MultipartFile wagonFile, Model model) {

        methodOfBasicLogic.lookingForOptimalMapOfRoute();
        model.addAttribute("reportListOfDistributedRoutesAndWagons", methodOfBasicLogic.getListOfDistributedRoutesAndWagons());
        model.addAttribute("reportListOfDistributedRoutes", methodOfBasicLogic.getListOfUndistributedRoutes());
        model.addAttribute("reportListOfDistributedWagons", methodOfBasicLogic.getListOfUndistributedWagons());
        model.addAttribute("reportListOfError", methodOfBasicLogic.getListOfError());
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void getXLS(HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileExcel(response, methodOfBasicLogic.getListOfDistributedRoutesAndWagons(), methodOfBasicLogic.getListOfUndistributedRoutes(),
                methodOfBasicLogic.getListOfUndistributedWagons(), methodOfBasicLogic.getListOfError());
    }
}
