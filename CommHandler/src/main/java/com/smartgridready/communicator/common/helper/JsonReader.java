package com.smartgridready.communicator.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.burt.jmespath.Expression;
import io.burt.jmespath.JmesPath;
import io.burt.jmespath.jackson.JacksonRuntime;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Maps a received JSON value into the required SmartGridready JSON output value.
 * The timestamps are converted to a canonical format first and afterwards.
 */
public class JsonReader extends JsonBase
{
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(JsonReader.class);

    private static final JmesPath<JsonNode> jmespath = new JacksonRuntime();

    /**
     * Constructs a new instance.
     * @param keywordMapInput the keyword map
     */
    public JsonReader(Map<String, String> keywordMapInput) {
        super(keywordMapInput);
    }

    /**
     * Implements a map key.
     * Contains a list of indices.
     */
    public static class Key implements Comparable<Key>, Serializable {

        /** The indices forming the key. */
        private final List<Integer> indices;

        private Key() {
            indices = new ArrayList<>();
        }

        /**
         * Adds an index to the key.
         * @param index the index position
         */
        public void add(int index) {
            indices.add(index);
        }

        /**
         * Gets the key as string.
         * @return a string containing all indices.
         */
        public String key() {
            StringBuilder sb = new StringBuilder();
            indices.forEach(sb::append);
            return sb.toString();
        }

        /**
         * Gets the element at a given position.
         * @param iteration the position
         * @return an integer
         */
        public int indexAt(int iteration) {
            return indices.get(iteration);
        }

        @Override
        public int compareTo(Key o) {
            return key().compareTo(o.key());
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Key && this.compareTo((Key)o) == 0;
        }

        @Override
        public int hashCode() {
            return key().hashCode();
        }

        /**
         * Clones the instance.
         * @param srcKey the source instance
         * @return a new instance
         */
        public static Key copy(Key srcKey) {
            return SerializationUtils.clone(srcKey);
        }
    }

    /**
     * Converts a JSON string to a data structure of key-value pairs, using a keyword map.
     * @param jsonFile the JSON input
     * @param keywordMap the keyword map
     * @return a map of key-value pairs
     * @throws JsonProcessingException if parsing failed
     */
    public static Map<Key, Map<String, Object>> mapToFlatList(String jsonFile, Map<String, String> keywordMap)
            throws JsonProcessingException {

        JsonNode root = objectMapper.readTree(jsonFile);

        var reader = new JsonReader(keywordMap);
        return reader.parseJsonTree(root, null, 1, keywordMap);
    }

    private Map<Key, Map<String, Object>> parseJsonTree(JsonNode node, Map<Key, Map<String, Object>> parentData, int iteration, Map<String, String> keywordMap) {

        // Cannot use TreeMap - it results in incorrect order
        Map<Key, Map<String, Object>> recordMap = new LinkedHashMap<>();

        // Get all keywords for the given iteration depth
        List<Map.Entry<String, String>> keywords = getKeywordsForIteration(iteration);

        var niterations = determineRequiredIterations(keywordMap);
        if (iteration <= niterations) {
            if (parentData == null) {
                processChildElements(node, iteration, recordMap, keywords, 0, null);
            } else {
                int parentIndex = 0;
                for (Map.Entry<Key, Map<String, Object>> parentRec : parentData.entrySet()) {
                    processChildElements(node, iteration, recordMap, keywords, parentIndex, parentRec);
                    parentIndex++;
                }
            }
            return parseJsonTree(node, recordMap, iteration+1, keywordMap);
        }
        return parentData;
    }

    private static void processChildElements(JsonNode node,
                                             int iteration,
                                             Map<Key, Map<String, Object>> recordMap,
                                             List<Map.Entry<String, String>> keywords,
                                             int parentIndex,
                                             Map.Entry<Key, Map<String, Object>> parentRec) {

        // Count the number of child records for the given parent record
        Optional<Map.Entry<String, String>> kwOpt = keywords.stream().findFirst();
        if (kwOpt.isPresent()) {
            int noOfElem = getNoOfElem(node, parentIndex, kwOpt.get(), iteration);

            // loop over the child records
            for (int i = 0; i < noOfElem; i++) {
                Key key = parentRec == null ? new Key() : Key.copy(parentRec.getKey());
                key.add(i);
                if (parentRec == null) {
                    // process the root node.
                    recordMap.put(key, new LinkedHashMap<>());
                } else {
                    // process the child nodes, mix-in the values of the parent node.
                    recordMap.put(key, new LinkedHashMap<>(parentRec.getValue()));
                }

                final int iter = iteration;
                keywords.forEach(kw -> addChildElement(node, recordMap, key, kw, iter));
            }
        }
    }

    private static void addChildElement(
            JsonNode node,
            Map<Key, Map<String, Object>> recordMap,
            Key key,
            Map.Entry<String, String> kw,
            int iteration) {

        String pattern = kw.getValue();

        Pattern regex = Pattern.compile("\\[\\*\\]");
        for (int i=0; i<iteration; i++) {
            // noinspection RegExpRedundantEscape
            pattern = regex.matcher(pattern).replaceFirst(String.format("[%d]", key.indexAt(i)));
        }
        Expression<JsonNode> expression = jmespath.compile(pattern);
        JsonNode value = expression.search(node);

        if (value.isTextual()) {
            recordMap.get(key).put(kw.getKey(), value.asText());
        } else if (value.isFloatingPointNumber()) {
            recordMap.get(key).put(kw.getKey(), value.asDouble());
        } else if (value.isIntegralNumber()) {
            recordMap.get(key).put(kw.getKey(), value.asLong());
        } else if (value.isBoolean()) {
            recordMap.get(key).put(kw.getKey(), value.asBoolean());
        }
    }

    private static int getNoOfElem(JsonNode node,
                                   int parentIndex,
                                   Map.Entry<String, String> keyword,
                                   int iteration) {

        // Get count the number of records
        String searchPattern = keyword.getValue();
        Pattern regex = Pattern.compile("\\[\\*\\]");
        for (int i=1; i < iteration; i++) {
            // noinspection RegExpRedundantEscape
            searchPattern = regex.matcher(keyword.getValue()).replaceFirst(String.format("[%d]", parentIndex));
        }

        Expression<JsonNode> jmesQuery = jmespath.compile(searchPattern + " | length(@)");
        JsonNode result = jmesQuery.search(node);
        return result.asInt();
    }

    static int determineRequiredIterations(Map<String, String> keywordMap) {
        return keywordMap.values().stream()
                .map(value -> StringUtils.countMatches(value, "[*]"))
                .max(Comparator.naturalOrder())
                .orElse(0);
    }
}
