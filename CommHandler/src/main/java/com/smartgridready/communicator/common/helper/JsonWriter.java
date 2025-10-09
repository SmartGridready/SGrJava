package com.smartgridready.communicator.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts data to JSON.
 */
public class JsonWriter extends JsonBase {

    /**
     * Constructs a new instance.
     * @param keywordMapInput the keyword map
     */
    public JsonWriter(Map<String, String> keywordMapInput) {
        super(keywordMapInput);
    }

    /**
     * Builds a JSON string from a list of key-value pairs.
     * @param flatDataRecords the key-value pairs
     * @return a string
     * @throws JsonProcessingException when a key-value pair cannot be encoded
     */
    public String buildJsonString(Collection<Map<String, Object>> flatDataRecords) throws JsonProcessingException {
        return objectMapper.writeValueAsString(buildJsonNode(flatDataRecords));
    }

    /**
     * Builds a JSON node from a list of key-value pairs.
     * @param flatDataRecords the key-value pairs
     * @return a JSON node
     * @throws JsonProcessingException when a key-value pair cannot be encoded
     */
    public JsonNode buildJsonNode(Collection<Map<String, Object>> flatDataRecords) throws JsonProcessingException {

        // Group by first level group.
        List<Map.Entry<String, String>> keywordsForIteration = this.getKeywordsForIteration(1);

        // Put all records that have the same first level values into one group with a combined key, built from the values.
        Map<String, List<Map<String, Object>>> firstLevelGroups = new LinkedHashMap<>();
        flatDataRecords.forEach(flatRecord -> {

            // Build a combined key
            StringBuilder combinedKey = new StringBuilder();
            keywordsForIteration.forEach(keywordEntry -> {
                Object value = flatRecord.get(keywordEntry.getKey());
                combinedKey.append(value);
            });

            // Get the group by the combined key or add a new group if it does not exist.
            List<Map<String, Object>> recordGroup =
                    Optional.ofNullable(firstLevelGroups.get(combinedKey.toString())).orElse(new ArrayList<>());

            // Add the flat record to the group it belongs to
            recordGroup.add(flatRecord);
            // Update the first level group map
            firstLevelGroups.put(combinedKey.toString(), recordGroup);
        });


        // Get a mapping for the source element names to the destination element names
        Map<String, String> firstLevelNameMappings = getFirstLevelElements(keywordsForIteration);

        // Build up the JsonNode
        ArrayNode rootNode = objectMapper.createArrayNode(); // Assuming below the root node is an array
        firstLevelGroups.forEach((groupKey, flatRecordsBelongingToGroup) -> {
            // Add a node for each group to the array node
            ObjectNode firstLevelNode = objectMapper.createObjectNode();
            flatRecordsBelongingToGroup.forEach(flatRecord -> {
                flatRecord.forEach((valueNames, values) ->
                        // Pick all first level elements, map the elementNames get the values and add the elements to the firstLevel node.
                        keywordsForIteration.forEach(mappingEntry -> {
                            Object val = flatRecord.get(mappingEntry.getKey());
                            if (val == null) {
                                return;
                            }
                            if (val instanceof Double) {
                                firstLevelNode.put(firstLevelNameMappings.get(mappingEntry.getKey()), ((Number) val).doubleValue());
                            } else if (val instanceof Number) {
                                firstLevelNode.put(firstLevelNameMappings.get(mappingEntry.getKey()), ((Number) val).longValue());
                            } else if (val instanceof Boolean) {
                                firstLevelNode.put(firstLevelNameMappings.get(mappingEntry.getKey()), (Boolean) val);
                            } else {
                                firstLevelNode.put(firstLevelNameMappings.get(mappingEntry.getKey()), val.toString());
                            }
                        }));
                // Then add the second level nodes to the first level node.
                addSecondLevelNodes(firstLevelNode, flatRecordsBelongingToGroup);
            });
            // We have finished adding the first level node.
            rootNode.add(firstLevelNode);
        });

        return rootNode;
    }

    private void addSecondLevelNodes(ObjectNode firstLevelNode, List<Map<String, Object>> flatRecordsBelongingToGroup) {

        List<Map.Entry<String, String>> keywordsForIteration = getKeywordsForIteration(2);

        Map<String, List<Map<String, Object>>> secondLevelGroups = new LinkedHashMap<>();

        flatRecordsBelongingToGroup.forEach(flatRecord -> {
            // Build a combined key
            StringBuilder combinedKey = new StringBuilder();
            keywordsForIteration.forEach(keywordEntry -> {
                Object value = flatRecord.get(keywordEntry.getKey());
                combinedKey.append(value);
            });

            // Get the group by the combined key or add a new group if it does not exist.
            List<Map<String, Object>> recordGroup =
                    Optional.ofNullable(secondLevelGroups.get(combinedKey.toString())).orElse(new ArrayList<>());

            recordGroup.add(flatRecord);
            secondLevelGroups.put(combinedKey.toString(), recordGroup);
        });

        // Build the Json tree
        Map<String, Map<String, String>> secondLeveGroupElements = getSecondLevelElements(keywordsForIteration);
        secondLeveGroupElements.forEach((parentName, childNameMapping) -> {

            ArrayNode arrayNode = firstLevelNode.putArray(parentName);
            secondLevelGroups.forEach( (groupKey, flatRecordsOfGroup) -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                flatRecordsOfGroup.forEach(flatRecord -> flatRecord.forEach((valueNames, values) -> keywordsForIteration.forEach(mappingEntry -> {
                    Object val = flatRecord.get(mappingEntry.getKey());
                    if (val == null) {
                        return;
                    }
                    if (val instanceof Double) {
                        objectNode.put(childNameMapping.get(mappingEntry.getKey()), (Double) val);
                    } else if (val instanceof Number) {
                        objectNode.put(childNameMapping.get(mappingEntry.getKey()), ((Number) val).longValue());
                    } else if (val instanceof Boolean) {
                        objectNode.put(childNameMapping.get(mappingEntry.getKey()), (Boolean) val);
                    } else {
                        objectNode.put(childNameMapping.get(mappingEntry.getKey()), val.toString());
                    }
                })));
                arrayNode.add(objectNode);
            });
        });
    }

    private Map<String, String> getFirstLevelElements(List<Map.Entry<String, String>> keywords) {

        Map<String, String> result = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("\\[\\*\\]\\.(.*?)$");
        keywords.forEach(entry -> {
            // noinspection RegExpRedundantEscape
            Matcher matcher = pattern.matcher(entry.getValue());
            if (matcher.find()) {
                String targetName = matcher.group(1);
                result.put(entry.getKey(), targetName);
            }
        });
        return result;
    }

    private Map<String, Map<String, String>> getSecondLevelElements(List<Map.Entry<String, String>> keywords) {

        Map<String, Map<String, String>> result = new LinkedHashMap<>();

        Pattern regexMatch = Pattern.compile("\\[\\*\\]\\.(.*?)\\[\\*\\]");
        Pattern regexRepl = Pattern.compile("\\[\\*\\]\\.(.*?)\\[\\*\\]\\.");
        keywords.forEach(entry -> {
            // noinspection RegExpRedundantEscape
            Matcher matcher = regexMatch.matcher(entry.getValue());
            if (matcher.find()) {
                String groupKey = matcher.group(1);
                Map<String, String> valueNames = Optional.ofNullable(result.get(groupKey)).orElse(new LinkedHashMap<>());
                // noinspection RegExpRedundantEscape
                String valueName = regexRepl.matcher(entry.getValue()).replaceAll("");
                valueNames.put(entry.getKey(), valueName);
                result.put(groupKey, valueNames);
            }
        });
        return result;
    }
}
