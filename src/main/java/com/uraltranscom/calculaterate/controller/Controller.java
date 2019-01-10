package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.GetSettingDAO;
import com.uraltranscom.calculaterate.dao.SearchCargoDAO;
import com.uraltranscom.calculaterate.dao.SearchStationDAO;
import com.uraltranscom.calculaterate.model.Setting;
import com.uraltranscom.calculaterate.service.impl.CalculationRate;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.uraltranscom.calculaterate.util.ParserInputName.getId;

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
    private static Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private CalculationRate calculationRate;
    @Autowired
    private SearchStationDAO searchStationDAO;
    @Autowired
    private SearchCargoDAO searchCargoDAO;
    @Autowired
    private GetSettingDAO getSettingDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/rate", method = RequestMethod.GET)
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
    }

    @RequestMapping(value = "/station/search", method = RequestMethod.GET)
    public @ResponseBody List<Object> stationSearch(@RequestParam(value = "station") String station) {
        return searchStationDAO.getObject(PrepareMapParams.prepareMapWithParams(station));
    }

    @RequestMapping(value = "/cargo/search", method = RequestMethod.GET)
    public @ResponseBody List<Object> cargoSearch(@RequestParam(value = "cargo") String cargo) {
        return searchCargoDAO.getObject(PrepareMapParams.prepareMapWithParams(cargo));
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting(Model model) {
        Map<String, List<Setting>> map = getSettingDAO.getObject(PrepareMapParams.prepareMapWithParams("name"));
        model.addAttribute("map", map);
        return "settings";
    }

}
