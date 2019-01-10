package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.Setting;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 09.01.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class GetSettingDAOTest {
    @Autowired
    GetSettingDAO getSettingDAO;

    @Test
    public void testGetSetting() {
        Map<String, List<Setting>> map = getSettingDAO.getObject(PrepareMapParams.prepareMapWithParams("name"));
        System.out.println(map);
    }
}
