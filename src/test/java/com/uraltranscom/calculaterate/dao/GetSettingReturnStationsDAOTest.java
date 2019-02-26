package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.dao.setting.get.GetSettingReturnStationsDAO;
import com.uraltranscom.calculaterate.model.settings.SettingReturnStations;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 21.02.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class GetSettingReturnStationsDAOTest {
    @Autowired
    private GetSettingReturnStationsDAO getSettingReturnStationsDAO;

    @Test
    public void test(){
        Map<String, List<SettingReturnStations>> map;
        map = getSettingReturnStationsDAO.getObject(PrepareMapParams.prepareMapWithParams(""));
        Assert.assertNotNull("It's not ok", map);
    }
}
