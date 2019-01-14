package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.GetSettingDAO;
import com.uraltranscom.calculaterate.model.Setting;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

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
    private GetSettingDAO getSettingDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting(Model model) {
        Map<String, List<Setting>> map = getSettingDAO.getObject(PrepareMapParams.prepareMapWithParams("name"));
        model.addAttribute("map", map);
        return "settings";
    }
}
