package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 19.02.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class GetCountryStationDAOTest {

    @Autowired
    private GetCountryStationDAO getCountryStationDAO;

    @Test
    public void test() {
        Assert.assertEquals("20", getCountryStationDAO.getObject(PrepareMapParams.prepareMapWithParams("190900")).getIdCountry());
    }
}
