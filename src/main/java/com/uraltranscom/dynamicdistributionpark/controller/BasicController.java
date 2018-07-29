package com.uraltranscom.dynamicdistributionpark.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

import com.uraltranscom.dynamicdistributionpark.service.impl.BasicClassLookingForImpl;
import com.uraltranscom.dynamicdistributionpark.util.MultipartFileToFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BasicController {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private BasicClassLookingForImpl basicClassLookingForImpl;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "routes") MultipartFile routesFile,
                            @RequestParam(value = "wagons") MultipartFile wagonsFile,
                            @RequestParam(value = "rates") MultipartFile ratesFile,
                            @RequestParam(value = "emptyroutes") MultipartFile emptyRoutesFile, Model model) {
        basicClassLookingForImpl.getGetListOfDistance().getGetListOfRoutesImpl().setFile(MultipartFileToFileUtil.multipartToFile(routesFile));
        basicClassLookingForImpl.getGetListOfDistance().getGetListOfWagonsImpl().setFile(MultipartFileToFileUtil.multipartToFile(wagonsFile));
        basicClassLookingForImpl.getGetListOfRates().setFile(MultipartFileToFileUtil.multipartToFile(ratesFile));
        basicClassLookingForImpl.getGetListOfEmptyRoutes().setFile(MultipartFileToFileUtil.multipartToFile(emptyRoutesFile));
        basicClassLookingForImpl.fillMapRouteIsOptimal();
        if (basicClassLookingForImpl.isFlag()) {
            model.addAttribute("needFillRateOrTariff", basicClassLookingForImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            return "add";
        } else {
            basicClassLookingForImpl.getClassHandlerTotalCalculate().calculateYield(basicClassLookingForImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            model.addAttribute("finalWagonList", basicClassLookingForImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            model.addAttribute("reportListOfError", basicClassLookingForImpl.getListOfError());
            model.addAttribute("yield", basicClassLookingForImpl.getClassHandlerTotalCalculate().getYield());
            return "welcome";
        }
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "rate") String rate,
                            @RequestParam(value = "tariff") String tariff,
                            @RequestParam(value = "number") String numberOfWagon, Model model) {
        basicClassLookingForImpl.getClassHandlerTotalCalculate().updateMap(basicClassLookingForImpl.getClassHandlerLookingFor().getMapFinalWagonInfo(), numberOfWagon, rate, tariff);
        model.addAttribute("finalWagonList", basicClassLookingForImpl.getClassHandlerTotalCalculate().getNewMapWagonFinalInfo());
        model.addAttribute("reportListOfError", basicClassLookingForImpl.getListOfError());
        model.addAttribute("yield", basicClassLookingForImpl.getClassHandlerTotalCalculate().getYield());
        return "welcome";
    }
/**
    // Выгрузка в Excel
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void getXLS(HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileExcel(response, basicClassLookingForImpl.getTotalMapWithWagonNumberAndRoute());
    }*/
}
