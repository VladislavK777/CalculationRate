package com.uraltranscom.dynamicdistributionpark.controller;

/**
 *
 * Контроллер
 *
 * @author Vladislav Klochkov
 * @version 2.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 * 13.10.2018
 *   1. Версия 2.0
 *
 */

import com.uraltranscom.dynamicdistributionpark.service.export.WriteToFileExcel;
import com.uraltranscom.dynamicdistributionpark.service.impl.BasicClassImpl;
import com.uraltranscom.dynamicdistributionpark.util.MultipartFileToFileUtil;
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
    public String routeList(@RequestParam(value = "routesFile") MultipartFile routesFile,
                            @RequestParam(value = "wagonsFile") MultipartFile wagonsFile,
                            @RequestParam(value = "ratesFile") MultipartFile ratesFile, Model model) {
        basicClassImpl.getClassHandlerLookingFor().getGetListOfDistance().getGetListOfRoutesImpl().setFile(MultipartFileToFileUtil.multipartToFile(routesFile));
        basicClassImpl.getClassHandlerLookingFor().getGetListOfDistance().getGetListOfWagonsImpl().setFile(MultipartFileToFileUtil.multipartToFile(wagonsFile));
        basicClassImpl.getClassHandlerLookingFor().getGetListOfRates().setFile(MultipartFileToFileUtil.multipartToFile(ratesFile));
        basicClassImpl.fillMapRouteIsOptimal();
        if (basicClassImpl.isFlag()) {
            model.addAttribute("needFillRateOrTariff", basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            return "add";
        } else {
            basicClassImpl.getClassHandlerTotalCalculate().calculateYield(basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            model.addAttribute("finalWagonList", basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
            model.addAttribute("reportListOfError", basicClassImpl.getListOfError());
            model.addAttribute("finalCountDaysWithVolume", basicClassImpl.getClassHandlerTotalCalculate().getFinalCountOrdersWithVolume());
            model.addAttribute("count", basicClassImpl.getClassHandlerLookingFor().getGetListOfDistance().getGetListOfRoutesImpl().getCount());
            return "welcome";
        }
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public String routeList(@RequestParam(value = "rates") String rates,
                            @RequestParam(value = "tariffs") String tariffs,
                            @RequestParam(value = "wagons") String wagons,
                            @RequestParam(value = "routes") String routes,
                            HttpServletResponse response, Model model) {
        basicClassImpl.getClassHandlerTotalCalculate().updateMap(basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo(), wagons, rates, tariffs, routes);
        model.addAttribute("finalWagonList", basicClassImpl.getClassHandlerTotalCalculate().getNewMapWagonFinalInfo());
        model.addAttribute("reportListOfError", basicClassImpl.getListOfError());
        model.addAttribute("finalCountOrdersWithVolume", basicClassImpl.getClassHandlerTotalCalculate().getFinalCountOrdersWithVolume());
        model.addAttribute("count", basicClassImpl.getClassHandlerLookingFor().getGetListOfDistance().getGetListOfRoutesImpl().getCount());
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void getXLS(HttpServletResponse response, Model model) {
        basicClassImpl.getClassHandlerLookingFor().fillFinalMapByOrders();
        WriteToFileExcel.downloadFileExcel(response, basicClassImpl.getClassHandlerLookingFor().getMapFinalOrderInfo());
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/exportWagons", method = RequestMethod.GET)
    public void getXLSWagons(HttpServletResponse response, Model model) {
        basicClassImpl.getClassHandlerLookingFor().fillFinalMapByOrders();
        WriteToFileExcel.downloadWagonsFileExcel(response, basicClassImpl.getClassHandlerLookingFor().getMapFinalWagonInfo());
    }
}
