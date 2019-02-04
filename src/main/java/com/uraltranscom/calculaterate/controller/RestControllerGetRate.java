package com.uraltranscom.calculaterate.controller;

import com.uraltranscom.calculaterate.dao.TestErrorDAO;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.service.impl.CommonLogicClass;
import com.uraltranscom.calculaterate.util.MultipartFileToFileUtil;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<TotalModel> totalModel(@RequestParam("stationFrom") String stationFrom,
                                                 @RequestParam("stationTo") String stationTo,
                                                 @RequestParam("cargo") String cargo,
                                                 @RequestParam("volume") int volume,
                                                 @RequestParam("file") MultipartFile file) {
        commonLogicClass.startLogic(getId(stationFrom), getId(stationTo), getId(cargo), volume, MultipartFileToFileUtil.multipartToFile(file));
        return new ResponseEntity<>(commonLogicClass.getTotalModel(), HttpStatus.OK);
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
