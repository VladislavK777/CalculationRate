package com.uraltranscom.calculaterate.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.uraltranscom.calculaterate.controller.RestControllerSettingsInsert;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vladislav Klochkov
 * @create 2019-02-09
 */

public class CheckMandatoryParams {
    private static Logger logger = LoggerFactory.getLogger(CheckMandatoryParams.class);

    public static Conflict checkMandatoryParams(Object object, String... params) {
        Conflict conflict = null;
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String objToJson = null;
        try {
            objToJson = objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error convert object to JSON: {}", e.getMessage());
        }
        //JSONPObject jsonpObject = new JSONPObject(objToJson);
        for (String param : params) {

        }
        return conflict;
    }

}
