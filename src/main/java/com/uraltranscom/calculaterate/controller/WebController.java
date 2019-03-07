package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.service.export.WriteToFileExcel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import com.uraltranscom.calculaterate.service.impl.GroupCalculateRate;
import com.uraltranscom.calculaterate.util.MultipartFileToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("user", principal.getName());
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        commonLogicClass.getTotalListModels().clear();
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/export")
    public void getXLS(HttpServletResponse response) {
        WriteToFileExcel.downloadFileExcel(response, commonLogicClass.getTotalListModels());
    }

    // Групповая загрузка
    @PostMapping(value = "/group")
    public String groupLoading(@RequestParam(value = "file") MultipartFile file, Model model) {
        groupCalculateRate.fetchGroupModels(MultipartFileToFile.multipartToFile(file));
        model.addAttribute("error", groupCalculateRate.getListError());
        return "welcome";
    }

    // Выгрузка в Excel
    @RequestMapping(value = "/exportGroup")
    public void getGroupXLS(HttpServletResponse response) {
        WriteToFileExcel.downloadFileExcel(response, groupCalculateRate.getTotalListModels().getTotalModelList());
    }
}
