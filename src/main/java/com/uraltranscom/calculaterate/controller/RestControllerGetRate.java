package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 14.01.2019
 */

@RestController
@RequestMapping("rate")
public class RestControllerGetRate {

    @Autowired
    private CommonLogicClass commonLogicClass;

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public TotalModel totalModel() {
        commonLogicClass.startLogic("191104", "722400", "131071", 138);
        return commonLogicClass.getTotalModel();
    }
    /*@RequestMapping(value = "/rate", method = RequestMethod.GET)
    public String rate(@RequestParam(value = "station_from") String station_from,
                       @RequestParam(value = "station_to") String station_to,
                       @RequestParam(value = "cargo") String cargo,
                       @RequestParam(value = "volume") int volume, Model model) {
        calculationRate.getRate(getId(station_from), getId(station_to), getId(cargo), volume);
        model.addAttribute("list", calculationRate.getTotalModel().getExitList());
        model.addAttribute("rate", calculationRate.getRate());
        model.addAttribute("fullCountDays", calculationRate.getSumFullCountDays());
        model.addAttribute("countDays", calculationRate.getSumCountDays());
        model.addAttribute("countCost", calculationRate.getSumCosts());
        model.addAttribute("yield", calculationRate.getYield());
        model.addAttribute("countDistance", calculationRate.getSumDistance());
        return "add";
    }*/
}
