package com.smartgridready.communicator.common.helper;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonHelper {

    public static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);

    private JsonHelper() {
        throw new IllegalStateException("Helper class");
    }

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
        } catch (IOException e) {
            throw new GenDriverException("Failed to parse JSON response", e);
        }
    }

    public static Value mapJsonResponse(JMESPathMapping jmesPathMapping, String response) throws GenDriverException {

        // Build mapping tables from EI-XML mappings
        Map<String, String> mapFrom = new HashMap<>();
        Map<String, String> mapTo = new HashMap<>();
        Map<String, String> names = new HashMap<>();
        List<JMESPathMappingRecord> mappingRecords = jmesPathMapping.getMapping();
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

        final String errorMsg = ("Unable to map JSON response according the JSONMapping rules in EI-XML");
        try {
            Map<JsonReader.Key, Map<String, Object>> flatRepresentation = JsonReader.mapToFlatList(response, mapFrom);
            if (flatRepresentation != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Flat representation of JSON response: {}", flatRepresentation.values());
                }

                Map<Integer, Map<String, Object>> enhancedMap = enhanceWithNamings(flatRepresentation, names);

                // TODO find a solution without parsing the JSON string again
                JsonWriter builder = new JsonWriter(mapTo);
                return parseJsonResponse(null, builder.buildJson(enhancedMap.values()));
            } else {
                throw new GenDriverException(errorMsg);
            }
        } catch (JsonProcessingException e) {
            throw new GenDriverException(errorMsg, e);
        }
    }

    private  static Map<Integer, Map<String, Object>> enhanceWithNamings(
            Map<JsonReader.Key, Map<String, Object>> flatRepresentation, Map<String, String> names) {

        Map<Integer, Map<String, Object>> enhanced = new HashMap<>();

        final AtomicInteger key = new AtomicInteger(0);
        flatRepresentation.forEach( (valuesKey, valuesMap) -> {
            for (int i = 0; i < valuesMap.size(); i++) {
                if (!names.isEmpty()) {
                    enhanced.putAll(flattenNamedRecords(key, valuesMap, names));
                } else {
                    enhanced.put( (key.getAndIncrement()), valuesMap);
                }
            }
        });
        return enhanced;
    }

    private static Map<Integer, Map<String, Object>> flattenNamedRecords(
            AtomicInteger currentRecordKey,
            Map<String, Object> valuesMap,
            Map<String, String> names) {

        Map<Integer, Map<String, Object>> flattenedRecords = new HashMap<>();

        HashMap<String, Object> unnamedValues = new HashMap<>();
        valuesMap.forEach((key, value) -> {
            if (!names.containsKey(key)) {
                unnamedValues.put(key, value);
            }
        });

        names.forEach((nameKey, nameValue) -> {
            // Create a separate record for each name
            Map<String, Object> newValues = new HashMap<>(unnamedValues);
            newValues.put(nameValue, nameValue.replace("$", ""));
            newValues.put(nameKey, valuesMap.get(nameKey));
            flattenedRecords.put(currentRecordKey.getAndIncrement(), newValues);
        });

        return flattenedRecords;
    }
}
