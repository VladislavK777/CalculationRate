package com.uraltranscom.calculaterate.model.cache;

import com.uraltranscom.calculaterate.dao.SearchStationDAO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 06.02.2019
 */

@Data
public class CacheStationsSearch {
    private static Logger logger = LoggerFactory.getLogger(CacheStationsSearch.class);
    private List<Object> cache;

    @Autowired
    private SearchStationDAO searchStationDAO;

    public void init() {
        List<Object> temp = searchStationDAO.getObject();
        cache = temp.isEmpty() ? null : temp;
        logger.info("Initializing Bean 'CacheStationsSearch'");
    }

    /*@Override
    public void start() {

    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return !cache.isEmpty();
    }*/
}
