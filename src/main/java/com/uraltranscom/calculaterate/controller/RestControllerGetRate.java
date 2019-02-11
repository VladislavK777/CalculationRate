package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.TestErrorDAO;
import com.uraltranscom.calculaterate.model.CalcRateBody;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import com.uraltranscom.calculaterate.util.CheckMandatoryParams;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TestErrorDAO testErrorDAO;

    //@CrossOrigin(origins = "*")
    @PostMapping(value = "/info")
    public ResponseEntity<Object> totalModel(@RequestBody CalcRateBody object) {
        Conflict conflict = CheckMandatoryParams.checkMandatoryParams(object, "stationFrom", "stationTo", "cargo", "volume");
        if (conflict == null) {
            commonLogicClass.startLogic(getId(object.getStationFrom()), getId(object.getStationTo()), getId(object.getCargo()), object.getVolume(), object.getFile());
            return new ResponseEntity<>(commonLogicClass.getTotalModel(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(conflict, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/test/{id}")
    public ResponseEntity testModel(@PathVariable String id) {
        Object obj = testErrorDAO.getObject(PrepareMapParams.prepareMapWithParams(id));
        HttpStatus code;
        if (obj instanceof Conflict) {
            code = HttpStatus.NOT_FOUND;
        } else {
            code = HttpStatus.OK;
        }
        return new ResponseEntity<>(obj, code);
    }

}
