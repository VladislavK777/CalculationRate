package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import com.uraltranscom.calculaterate.service.impl.GroupCalculateRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

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

@org.springframework.stereotype.Controller
public class WebController {
    private static Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private CommonLogicClass commonLogicClass;
    @Autowired
    private GroupCalculateRate groupCalculateRate;

    @RequestMapping(value = "/")
    public String home(Model model, Principal principal) {
        //model.addAttribute("user", principal.getName());
        //model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        commonLogicClass.getTotalListModels().clear();
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export")
    public void getXLS(HttpServletResponse response) {
        WriteToFileExcel.downloadFileExcel(response, commonLogicClass.getTotalListModels());
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/exportGroup")
    public void getGroupXLS(HttpServletResponse response) {
        WriteToFileExcel.downloadFileExcel(response, groupCalculateRate.getTotalListModels().getTotalModelList());
    }
}
