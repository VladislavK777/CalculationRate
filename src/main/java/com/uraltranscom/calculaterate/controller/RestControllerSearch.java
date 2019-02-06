package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.SearchCargoDAO;
import com.uraltranscom.calculaterate.dao.SearchRoadDAO;
import com.uraltranscom.calculaterate.dao.SearchStationDAO;
import com.uraltranscom.calculaterate.model.cache.CacheStationsSearch;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 14.01.2019
 */

@RestController
@RequestMapping("search")
public class RestControllerSearch {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSearch.class);

    @Autowired
    private SearchStationDAO searchStationDAO;
    @Autowired
    private SearchCargoDAO searchCargoDAO;
    @Autowired
    private SearchRoadDAO searchRoadDAO;
    @Autowired
    private CacheStationsSearch cacheStationsSearch;

    @RequestMapping(value = "/station")
    public List<Object> stationSearch() {
        return cacheStationsSearch.getCache();
    }

    @RequestMapping(value = "/cargo", method = RequestMethod.GET, produces = "application/json")
    public List<Object> cargoSearch(@RequestParam(value = "cargo") String cargo) {
        return searchCargoDAO.getObject(PrepareMapParams.prepareMapWithParams(cargo));
    }

    @RequestMapping(value = "/road", method = RequestMethod.GET, produces = "application/json")
    public List<Object> roadSearch(@RequestParam(value = "road") String road) {
        return searchRoadDAO.getObject(PrepareMapParams.prepareMapWithParams(road));
    }
}
