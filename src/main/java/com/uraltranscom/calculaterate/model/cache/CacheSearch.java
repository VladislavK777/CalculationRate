package com.uraltranscom.calculaterate.model.cache;

import com.uraltranscom.calculaterate.dao.cache.CacheSearchDAO;
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
public class CacheSearch {
    private static Logger logger = LoggerFactory.getLogger(CacheSearch.class);
    private List<Object> cache;
    private String marker;

    public CacheSearch(String marker) {
        this.marker = marker;
    }

    @Autowired
    private CacheSearchDAO cacheSearchDAO;

    public void init() {
        List<Object> temp = cacheSearchDAO.getObject(marker);
        cache = temp.isEmpty() ? null : temp;
        logger.info("Initializing Bean 'CacheSearch' with marker: {}", marker);
    }
}
