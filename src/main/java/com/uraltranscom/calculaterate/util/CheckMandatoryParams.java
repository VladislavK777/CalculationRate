package com.uraltranscom.calculaterate.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Klochkov
 * @project CalculationRate
 * @create 2019-02-09
 */

public class CheckMandatoryParams {
    private static Logger logger = LoggerFactory.getLogger(CheckMandatoryParams.class);

    public static Conflict checkMandatoryParams(Object object, String... params) {
        Conflict conflict = null;
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String objToJson;
        try {
            objToJson = objectWriter.writeValueAsString(object);
            logger.debug("JSON object: {}", objToJson);
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser jsonParser = factory.createParser(objToJson);
            JsonNode node = mapper.readTree(jsonParser);
            List<String> errorList = new ArrayList<>();
            for (String param : params) {
                Object o = mapper.treeToValue(node.get(param), Object.class);
                String s = mapper.writeValueAsString(o);
                if (s.equals("null")) {
                    errorList.add(param);
                }
            }
            if (!errorList.isEmpty()) {
                if (errorList.size() == 1) {
                    conflict = new Conflict(errorList.get(0), "ERROR", "Не заполнен обязательный параметр!");
                } else {
                    conflict = new Conflict(errorList, "ERROR", "Не заполнены обязательные параметры!");
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error convert object to JSON: {}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conflict;
    }

}
