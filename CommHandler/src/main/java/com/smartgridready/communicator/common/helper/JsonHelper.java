package com.smartgridready.communicator.common.helper;

import com.dashjoin.jsonata.Jsonata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.ns.v0.JMESPathMapping;
import com.smartgridready.ns.v0.JMESPathMappingRecord;
import com.smartgridready.communicator.common.api.values.JsonValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;
import io.burt.jmespath.Expression;
import io.burt.jmespath.JmesPath;
import io.burt.jmespath.jackson.JacksonRuntime;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides helper methods for JSON.
 */
public class JsonHelper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);

    private JsonHelper() {
        throw new IllegalStateException("Helper class");
    }

    /**
     * Evaluates a JMESPath expression on a JSON string and returns as SGr value.
     * @param jmesPath the JMESPath expression
     * @param jsonResp the JSON string
     * @return an instance of {@link JsonValue}
     * @throws GenDriverException when an error occurred during parsing
     */
    public static Value parseJsonResponse(String jmesPath, String jsonResp) throws GenDriverException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(jsonResp);
            if (jmesPath == null || jmesPath.trim().isEmpty()) {
                return JsonValue.of(jsonNode);
            } else {
                JmesPath<JsonNode> path = new JacksonRuntime();
                Expression<JsonNode> expression = path.compile(jmesPath);
                JsonNode res = expression.search(jsonNode);
                return JsonValue.of(res);
            }
        } catch (Exception e) {
            throw new GenDriverException("Failed to parse JSON response", e);
        }
    }

    /**
     * Evaluates a JSONata expression on a JSON string and returns as SGr value.
     * @param jsonataExpression the JSONata expression
     * @param jsonResp the JSON string
     * @return an instance of {@link JsonValue}
     * @throws GenDriverException when an error occurred during parsing
     */
    public static Value parseJsonResponseWithJsonata(String jsonataExpression, String jsonResp) throws GenDriverException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (jsonataExpression != null && !jsonataExpression.trim().isEmpty()) {
                var expression = Jsonata.jsonata(jsonataExpression);
                var jsonObj = mapper.readValue(jsonResp, Object.class);
                var result = expression.evaluate(jsonObj);
                return JsonValue.of(result);
            }

            return JsonValue.of(mapper.readTree(jsonResp));

        } catch (IOException e) {
            throw new GenDriverException("Failed to parse JSON response", e);
        }
    }

    /**
     * Evaluates a JMESPath mapping on a JSON string and returns as SGr value.
     * @param jmesPathMapping the JMESPath mapping
     * @param response the JSON string
     * @return an instance of {@link JsonValue}
     * @throws GenDriverException when an error occurred during parsing and mapping
     */
    public static Value mapJsonResponse(JMESPathMapping jmesPathMapping, String response) throws GenDriverException {

        // Build mapping tables from EI-XML mappings
        Map<String, String> mapFrom = new LinkedHashMap<>();
        Map<String, String> mapTo = new LinkedHashMap<>();
        Map<String, String> names = new LinkedHashMap<>();
        buildMappingTables(jmesPathMapping.getMapping(), mapFrom, mapTo, names);

        final String errorMsg = ("Unable to map JSON response according the JSONMapping rules in EI-XML");
        try {
            Map<JsonReader.Key, Map<String, Object>> flatRepresentation = JsonReader.mapToFlatList(response, mapFrom);
            if (flatRepresentation != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Flat representation of JSON response: {}", flatRepresentation.values());
                }

                Map<Integer, Map<String, Object>> enhancedMap = enhanceWithNamings(flatRepresentation, names);

                JsonWriter builder = new JsonWriter(mapTo);
                return JsonValue.of(builder.buildJsonNode(enhancedMap.values()));
            } else {
                throw new GenDriverException(errorMsg);
            }
        } catch (JsonProcessingException e) {
            throw new GenDriverException(errorMsg, e);
        }
    }

    static int buildMappingTables(List<JMESPathMappingRecord> mappingRecords, Map<String, String> mapFrom, Map<String, String> mapTo, Map<String, String> names) {
        for (int i = 0; i < mappingRecords.size(); i++) {
            mapFrom.put(String.valueOf(i), mappingRecords.get(i).getFrom());
            if (mappingRecords.get(i).getFrom().startsWith("$")) {
                mapTo.put(mappingRecords.get(i).getFrom(), mappingRecords.get(i).getTo());
            } else {
                mapTo.put(String.valueOf(i), mappingRecords.get(i).getTo());
            }
            if (mappingRecords.get(i).getName() != null) {
                names.put(String.valueOf(i), mappingRecords.get(i).getName());
            }
        }
        return mappingRecords.size();
    }

    private static Map<Integer, Map<String, Object>> enhanceWithNamings(
            Map<JsonReader.Key, Map<String, Object>> flatRepresentation, Map<String, String> names) {

        Map<Integer, Map<String, Object>> enhanced = new LinkedHashMap<>();

        int key = 0;
        for (Map.Entry<JsonReader.Key, Map<String, Object>> e: flatRepresentation.entrySet()) {
            for (int i = 0; i < e.getValue().size(); i++) {
                if (!names.isEmpty()) {
                    var flatr = flattenNamedRecords(key, e.getValue(), names);
                    enhanced.putAll(flatr._2);
                    key = flatr._1;
                } else {
                    enhanced.put(key, e.getValue());
                    key++;
                }
            }
        }
        return enhanced;
    }

    private static Tuple2<Integer, Map<Integer, Map<String, Object>>> flattenNamedRecords(
            int currentRecordKey,
            Map<String, Object> valuesMap,
            Map<String, String> names) {

        Map<Integer, Map<String, Object>> flattenedRecords = new LinkedHashMap<>();

        Map<String, Object> unnamedValues = new LinkedHashMap<>();
        valuesMap.forEach((key, value) -> {
            if (!names.containsKey(key)) {
                unnamedValues.put(key, value);
            }
        });

        for (Map.Entry<String, String> e: names.entrySet()) {
            // Create a separate record for each name
            Map<String, Object> newValues = SerializationUtils.clone((LinkedHashMap<String, Object>) unnamedValues);
            newValues.put(e.getValue(), e.getValue().replace("$", ""));
            newValues.put(e.getKey(), valuesMap.get(e.getKey()));
            flattenedRecords.put(currentRecordKey, newValues);
            currentRecordKey++;
        }

        return Tuple.of(currentRecordKey, flattenedRecords);
    }
}
