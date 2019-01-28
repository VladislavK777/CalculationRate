package com.uraltranscom.calculaterate.dao;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class TestErrorDAOTest {
    @Autowired
    TestErrorDAO testErrorDAO;

    @Test
    public void testGetSetting() {
        Object error = testErrorDAO.getObject(PrepareMapParams.prepareMapWithParams("190910"));
        System.out.println(error);
    }
}
