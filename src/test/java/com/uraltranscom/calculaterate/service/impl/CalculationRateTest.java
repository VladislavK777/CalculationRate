package com.uraltranscom.calculaterate.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-03
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class CalculationRateTest {

    @Autowired
    CalculationRate calculationRate;

    @Test
    public void getRate() {
        calculationRate.getRate("120005", "722400", "131071", 138);
        Assert.assertFalse("Error", calculationRate.getRate() == 0);
    }
}
