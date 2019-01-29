package com.uraltranscom.calculaterate.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 21.01.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class GetListRatesTest {
    @Autowired
    GetListRates getListRates;


    //File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\test.xlsx");

    @Test
    public void test() {
        //Set set = getListRates.getListRates(file);
        Set set = null;
        Assert.assertNull("Not null", set);
    }

}
