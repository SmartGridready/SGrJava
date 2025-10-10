package com.smartgridready.communicator.common.helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for JSON reader and writer.
 */
public abstract class JsonBase {

    /** The JSON object mapper. */
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    /** The keyword map. */
    protected final Map<String, String> keywordMapInput;

    /**
     * Constructs a new instance.
     * @param keywordMapInput the keyword map
     */
    protected JsonBase(Map<String, String> keywordMapInput) {
        this.keywordMapInput = keywordMapInput;
    }

    /**
     * Gets the keywords of a given iteration.
     * @param iteration the iteration number
     * @return a list of keyword entries
     */
    List<Map.Entry<String, String>> getKeywordsForIteration(int iteration) {
        final int iterationDepth = iteration;
        return keywordMapInput.entrySet().stream()
                .filter(entry -> StringUtils.countMatches(entry.getValue(), "[*]")==iterationDepth)
                .collect(Collectors.toList());
    }
}
