package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.model.pojo.HttpBodyRate;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uraltranscom.calculaterate.util.ParserInputName.getId;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 14.01.2019
 */

@RestController
@RequestMapping("rate")
public class RestControllerGetRate {
    private static Logger logger = LoggerFactory.getLogger(RestControllerGetRate.class);

    @Autowired
    private CommonLogicClass commonLogicClass;

    @PostMapping(value = "/info")
    public ResponseEntity<TotalModel> totalModel(@RequestBody HttpBodyRate object) {
        commonLogicClass.startLogic(getId(object.getStationFrom()), getId(object.getStationTo()), getId(object.getCargo()), object.getVolume());
        return new ResponseEntity<>(commonLogicClass.getTotalModel(), HttpStatus.OK);
    }
}
