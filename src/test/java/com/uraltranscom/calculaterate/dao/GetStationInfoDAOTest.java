package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.Station;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 28.12.2018
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class GetStationInfoDAOTest {

    @Autowired
    @Qualifier("getStation")
    private GetStationInfoDAO getStationInfoDAO;

    @Test
    public void getStation(){
        Station station = getStationInfoDAO.getObject(PrepareMapParams.prepareMapWithParams("037202"));
        System.out.println(station.getNameStation());
        //Assert.assertEquals("Щербинка", station.getNameStation());
    }
}
