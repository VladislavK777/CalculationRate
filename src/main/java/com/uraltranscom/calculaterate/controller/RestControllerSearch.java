package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.SearchCargoDAO;
import com.uraltranscom.calculaterate.dao.SearchStationDAO;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 14.01.2019
 */

@RestController
@RequestMapping("search")
public class RestControllerSearch {

    @Autowired
    private SearchStationDAO searchStationDAO;
    @Autowired
    private SearchCargoDAO searchCargoDAO;

    @RequestMapping(value = "/station", method = RequestMethod.GET, produces = "application/json")
    public List<Object> stationSearch(@RequestParam(value = "station") String station) {
        return searchStationDAO.getObject(PrepareMapParams.prepareMapWithParams(station));
    }

    @RequestMapping(value = "/cargo", method = RequestMethod.GET, produces = "application/json")
    public List<Object> cargoSearch(@RequestParam(value = "cargo") String cargo) {
        return searchCargoDAO.getObject(PrepareMapParams.prepareMapWithParams(cargo));
    }
}
