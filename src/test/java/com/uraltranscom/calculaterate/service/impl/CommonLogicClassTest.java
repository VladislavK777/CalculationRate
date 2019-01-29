package com.uraltranscom.calculaterate.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * @author Vladislav Klochkov
 * @create 2019-01-03
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext/applicationContext.xml"})
public class CommonLogicClassTest {

    @Autowired
    private CommonLogicClass commonLogicClass;

    //File file;
    File file = new File("C:\\Users\\Vladislav.Klochkov\\Desktop\\test.xlsx");

    @Test
    public void getCommon(){
        commonLogicClass.startLogic("930305", "851005", "131071", 122, file);
        System.out.println(commonLogicClass.getListRates());
        System.out.println(commonLogicClass.getTotalListModels());
        //Assert.assertEquals(TotalModel.class.getSimpleName(), commonLogicClass.getTotalModel().getClass().getSimpleName());
    }
}
