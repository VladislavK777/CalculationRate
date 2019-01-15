package com.uraltranscom.calculaterate.service.impl;

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
public class CommonLogicClassTest {

    @Autowired
    private CommonLogicClass commonLogicClass;

    @Test
    public void getCommon(){
        commonLogicClass.startLogic("190900", "191104", "131071", 138);
        System.out.println(commonLogicClass.totalModel);
        //Assert.assertEquals(TotalModel.class.getSimpleName(), commonLogicClass.getTotalModel().getClass().getSimpleName());
    }
}
