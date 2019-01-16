package com.uraltranscom.calculaterate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }
}
