/*
package com.uraltranscom.calculaterate.impl;

import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.model_ex.TotalListModels;
import com.uraltranscom.calculaterate.service.impl.GroupCalculateRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.util.List;

*/
/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 06.03.2019
 *//*


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class GroupCalculateRateTest {
    @Autowired
    private GroupCalculateRate groupCalculateRate;

    private TotalListModels totalListModels;
    private List<String> listError;

    File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\Книга1.xlsx");

    @Test
    public void test() {
        groupCalculateRate.fetchGroupModels(file);
        totalListModels = groupCalculateRate.getTotalListModels();
        listError = groupCalculateRate.getListError();

        System.out.println(totalListModels);
        System.out.println(listError);
    }
}
*/
