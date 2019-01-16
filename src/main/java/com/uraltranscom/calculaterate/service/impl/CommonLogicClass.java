package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.GetTotalModelDAO;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.util.GetVolumeGroup;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Класс основной логике
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.12.2018
 *
 * 26.12.2018
 *   1. Версия 1.0
 *
 */

@Component
@Getter
public class CommonLogicClass {
    private final Logger logger = LoggerFactory.getLogger(CommonLogicClass.class);

    @Autowired
    private GetTotalModelDAO getTotalModelDAO;
    TotalModel totalModel = null;

    public void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);
        totalModel = getTotalModelDAO.getObject(PrepareMapParams.prepareMapWithParams(idStationDeparture, idStationDestination, idCargo, GetVolumeGroup.getVolumeGroup(volumeWagon)));
    }
}
