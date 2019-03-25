package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.model.GetTotalModelDAO;
import com.uraltranscom.calculaterate.model.GroupListBasicDate;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model_ex.TotalListModels;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @date 05.03.2019
 */

@Component
@Getter
public class GroupCalculateRate {
    private final Logger logger = LoggerFactory.getLogger(GroupCalculateRate.class);

    @Autowired
    private GetGroupListBasicDate getGroupListBasicDate;
    @Autowired
    private GetTotalModelDAO getTotalModelDAO;
    private TotalListModels totalListModels = new TotalListModels(new ArrayList<>());
    private List<String> listError = new ArrayList<>();
    private List<GroupListBasicDate> list;

    public void fetchGroupModels(File file) {
        logger.info("start fetchGroupModels");
        long timeStart = System.currentTimeMillis();
        list = getGroupListBasicDate.getGroupListBasicDateFromFile(file);
        clearArrays();
        for (GroupListBasicDate groupListBasicDate : list) {
            Object object = getTotalModelDAO.getObject(PrepareMapParams.prepareMapWithParams(
                    groupListBasicDate.getStationFrom().getIdStation(),
                    groupListBasicDate.getStationTo().getIdStation(),
                    groupListBasicDate.getCargo().getIdCargo(),
                    groupListBasicDate.getVolumeGroup()
            ));

            if (object instanceof TotalModel) {
                TotalModel totalModel = (TotalModel) object;
                totalListModels.getTotalModelList().add(totalModel);
            } else {
                Conflict conflict = (Conflict) object;
                String errorString = "Строка " +
                        groupListBasicDate.getNumber() +
                        ": Маршрут: " +
                        groupListBasicDate.getStationFrom().getNameStation() +
                        " - " +
                        groupListBasicDate.getStationTo().getNameStation() +
                        " - " +
                        conflict.getConflictMessage();
                listError.add(errorString);
            }
        }
        if (listError.isEmpty()) {
            if (list.isEmpty()) {
                listError.add("Файл пустой.");
            } else {
                listError.add("Нет ошибок.");
            }
        }
        long timeEnd = (System.currentTimeMillis() - timeStart);
        logger.info("finish fetchGroupModels. Work time: {} ms", timeEnd);
    }

    private void clearArrays() {
        totalListModels.getTotalModelList().clear();
        listError.clear();
    }
}
