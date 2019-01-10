package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 28.12.2018
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class SearchCargoDAOTest {

    @Autowired
    private SearchCargoDAO searchCargoDAO;

    @Test
    public void getStation(){
        List<Object> list = searchCargoDAO.getObject(PrepareMapParams.prepareMapWithParams("510"));
        System.out.println(list);
    }
}
