package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.service.impl.CalculationRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class Controller {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private CalculationRate calculationRate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        calculationRate.getRate("840109", "722400", "131071", 138);
        model.addAttribute("list", calculationRate.getExitModel().getExitList());
        model.addAttribute("rate", calculationRate.getRate());
        model.addAttribute("countDays", calculationRate.getSumCountDays());
        model.addAttribute("countCost", calculationRate.getSumCosts());
        model.addAttribute("yield", calculationRate.getYield());
    return "welcome";
    }
}
