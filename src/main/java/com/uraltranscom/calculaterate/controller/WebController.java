package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

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

@Controller
public class WebController {
    private static Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private CommonLogicClass commonLogicClass;

    @RequestMapping(value = "/")
    public String home(Model model) {
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export")
    public void getXLS(HttpServletResponse response, Model model) {
        WriteToFileExcel.downloadFileExcel(response, commonLogicClass.getTotalListModels());
    }
}
