package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.service.additional.JavaHelperBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Входной класс
 * Implementation for {@link BasicClass} interface
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 19.07.2018
 *
 * 19.07.2018
 *   1. Версия 1.0
 *
 */

@Service
@Component
public class BasicClassImpl extends JavaHelperBase implements BasicClass {
    // Подключаем логгер
    private static Logger logger = LoggerFactory.getLogger(BasicClassImpl.class);

    @Autowired
    private ClassHandlerLookingForImpl classHandlerLookingFor;
    @Autowired
    private ClassHandlerTotalCalculateImpl classHandlerTotalCalculate;

    // Массив ошибок
    private Set<String> setOfError = new HashSet<>();
    private List<String> listOfError = new ArrayList<>();

    // Флаг, что нужно заполнить или проверить ставку или тариф
    private boolean isFlag = false;

    private BasicClassImpl() {
    }

    @Override
    public void fillMapRouteIsOptimal() {
        // Очищаем массивы итоговые
        setOfError.clear();
        listOfError.clear();

        // Запускаем метод заполненеия первоначальной мапы расстояний
        classHandlerLookingFor.getGetListOfDistance().fillMap();

        // Запускаем распределение
        classHandlerLookingFor.lookingForOptimalMapOfRoute();

        set2list();
    }

    public Set<String> getSetOfError() {
        return setOfError;
    }

    public void setSetOfError(Set<String> setOfError) {
        this.setOfError = setOfError;
    }

    public List<String> getListOfError() {
        return listOfError;
    }

    public void setListOfError(List<String> listOfError) {
        this.listOfError = listOfError;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public ClassHandlerLookingForImpl getClassHandlerLookingFor() {
        return classHandlerLookingFor;
    }

    public void setClassHandlerLookingFor(ClassHandlerLookingForImpl classHandlerLookingFor) {
        this.classHandlerLookingFor = classHandlerLookingFor;
    }

    public ClassHandlerTotalCalculateImpl getClassHandlerTotalCalculate() {
        return classHandlerTotalCalculate;
    }

    public void setClassHandlerTotalCalculate(ClassHandlerTotalCalculateImpl classHandlerTotalCalculate) {
        this.classHandlerTotalCalculate = classHandlerTotalCalculate;
    }

    private void set2list() {
        listOfError.addAll(setOfError);
    }

}