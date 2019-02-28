package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.model.cache.CacheSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 14.01.2019
 */

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("search")
public class RestControllerSearch {
    private static Logger logger = LoggerFactory.getLogger(RestControllerSearch.class);

    @Qualifier("cacheStationsSearch")
    @Autowired
    private CacheSearch cacheStationSearch;

    @Qualifier("cacheRoadSearch")
    @Autowired
    private CacheSearch cacheRoadSearch;

    @Qualifier("cacheCargoSearch")
    @Autowired
    private CacheSearch cacheCargoSearch;

    @Qualifier("cacheDepartmentsStationSearch")
    @Autowired
    private CacheSearch cacheDepartmentsStationSearch;

    @RequestMapping(value = "/station")
    public List<Object> stationSearch() {
        return cacheStationSearch.getCache();
    }

    @RequestMapping(value = "/cargo")
    public List<Object> cargoSearch() {
        return cacheCargoSearch.getCache();
    }

    @RequestMapping(value = "/road")
    public List<Object> roadSearch() {
        return cacheRoadSearch.getCache();
    }

    @RequestMapping(value = "/department")
    public List<Object> departmentSearch() {
        return cacheDepartmentsStationSearch.getCache();
    }
}
