package com.uraltranscom.calculaterate.impl;

import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.model.GroupListBasicDate;
import com.uraltranscom.calculaterate.service.impl.GetGroupListBasicDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 06.03.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class GetGroupListBasicDateTest {
    @Autowired
    GetGroupListBasicDate getGroupListBasicDate;

    //private File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\Книга1.xlsx");

    @Test
    public void test() {
        List<GroupListBasicDate> list = null;
        Assert.assertNull("It's not ok", list);
    }
}
