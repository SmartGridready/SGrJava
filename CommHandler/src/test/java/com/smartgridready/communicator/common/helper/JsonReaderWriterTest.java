package com.smartgridready.communicator.common.helper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonReaderWriterTest extends JsonMapperTestBase
{
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(JsonReaderWriterTest.class);

    private static final  Map<String, String> KEYWORD_MAP_TARIFF_IN1 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN1.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN1.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN1.put("component",       "[*].integrated[*].component");
        KEYWORD_MAP_TARIFF_IN1.put("value",           "[*].integrated[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN2 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN2.put("component",       "[*].component");
        KEYWORD_MAP_TARIFF_IN2.put("start_timestamp", "[*].periods[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN2.put("end_timestamp",   "[*].periods[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN2.put("value",           "[*].periods[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN3 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN3.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN3.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN3.put("component",       "[*].component");
        KEYWORD_MAP_TARIFF_IN3.put("value",           "[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_OUT1 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_OUT1.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_OUT1.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_OUT1.put("component",       "[*].integrated[*].component");
        KEYWORD_MAP_TARIFF_OUT1.put("value",           "[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_OUT1.put("unit",            "[*].integrated[*].unit");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_SWISSPOWER_IN = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("start_timestamp", "prices[*].start_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("end_timestamp",   "prices[*].end_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("value",           "prices[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("unit",            "prices[*].integrated[*].unit");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("component",       "prices[*].integrated[*].component");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_SWISSPOWER_OUT = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("value",           "[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("unit",            "[*].integrated[*].unit");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("component",       "[*].integrated[*].component");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_GROUPEE_IN = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_GROUPEE_IN.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_GROUPEE_IN.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_GROUPEE_IN.put("value",           "[*].vario_plus");
        KEYWORD_MAP_TARIFF_GROUPEE_IN.put("unit",            "[*].unit");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_GROUPEE_OUT = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_GROUPEE_OUT.put("start_timestamp", "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_GROUPEE_OUT.put("end_timestamp",   "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_GROUPEE_OUT.put("value",           "[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_GROUPEE_OUT.put("unit",            "[*].integrated[*].unit");
    }

    private static final String[] EXPECTED_TARIFF_RECORDS_IN1 = {
            "{start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, component=sunlight, value=15.0}",
            "{start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, component=moonlight, value=12.0}",
            "{start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, component=sunlight, value=17.0}",
            "{start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, component=moonlight, value=14.0}",
            "{start_timestamp=2023-11-17T00:30:00+01:00, end_timestamp=2023-11-17T00:45:00+01:00, component=sunlight, value=16.0}",
            "{start_timestamp=2023-11-17T00:30:00+01:00, end_timestamp=2023-11-17T00:45:00+01:00, component=moonlight, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN2 = {
            "{component=sunlight, start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, value=15.0}",
            "{component=sunlight, start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, value=17.0}",
            "{component=sunlight, start_timestamp=2023-11-17T00:30:00+01:00, end_timestamp=2023-11-17T00:45:00+01:00, value=16.0}",
            "{component=moonlight, start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, value=12.0}",
            "{component=moonlight, start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, value=14.0}",
            "{component=moonlight, start_timestamp=2023-11-17T00:30:00+01:00, end_timestamp=2023-11-17T00:45:00+01:00, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN3 = {
            "{start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, component=sunlight, value=15.0}",
            "{start_timestamp=2023-11-17T00:00:00+01:00, end_timestamp=2023-11-17T00:15:00+01:00, component=moonlight, value=12.0}",
            "{start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, component=sunlight, value=17.0}",
            "{start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, component=moonlight, value=14.0}",
            "{start_timestamp=2023-11-17T00:15:00+01:00, end_timestamp=2023-11-17T00:30:00+01:00, component=sunlight, value=16.0}",
            "{start_timestamp=2023-11-17T00:30:00+01:00, end_timestamp=2023-11-17T00:45:00+01:00, component=moonlight, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_SWISSPOWER_IN = {
            "{start_timestamp=2025-09-01T00:00:00+02:00, end_timestamp=2025-09-01T00:15:00+02:00, value=0.22652, unit=CHF/kWh, component=work}",
            "{start_timestamp=2025-09-01T00:15:00+02:00, end_timestamp=2025-09-01T00:30:00+02:00, value=0.22191, unit=CHF/kWh, component=work}",
            "{start_timestamp=2025-09-01T00:30:00+02:00, end_timestamp=2025-09-01T00:45:00+02:00, value=0.214368, unit=CHF/kWh, component=work}",
            "{start_timestamp=2025-09-01T00:45:00+02:00, end_timestamp=2025-09-01T01:00:00+02:00, value=0.20898, unit=CHF/kWh, component=work}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_GROUPEE_IN = {
            "{start_timestamp=2025-10-03T00:00:00+02:00, end_timestamp=2025-10-03T00:15:00+02:00, value=23.75, unit=Rp./kWh}",
            "{start_timestamp=2025-10-03T00:15:00+02:00, end_timestamp=2025-10-03T00:30:00+02:00, value=23.04, unit=Rp./kWh}",
            "{start_timestamp=2025-10-03T00:30:00+02:00, end_timestamp=2025-10-03T00:45:00+02:00, value=21.31, unit=Rp./kWh}",
            "{start_timestamp=2025-10-03T00:45:00+02:00, end_timestamp=2025-10-03T01:00:00+02:00, value=19.93, unit=Rp./kWh}"
    };

    @Test
    void convertToFlatList_TariffIn1() throws Exception {

        int nIterations = JsonReader.determineRequiredIterations(KEYWORD_MAP_TARIFF_IN1);
        assertEquals(2, nIterations);

        String receivedJson = loadJson("TariffIn1.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN1);
        assertNotNull(tariffRecords);
        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN1[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffIn2() throws Exception {

        int nIterations = JsonReader.determineRequiredIterations(KEYWORD_MAP_TARIFF_IN2);
        assertEquals(2, nIterations);

        String receivedJson = loadJson("TariffIn2.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN2);
        assertNotNull(tariffRecords);
        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN2[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffIn3() throws Exception {

        int nIterations = JsonReader.determineRequiredIterations(KEYWORD_MAP_TARIFF_IN3);
        assertEquals(1, nIterations);

        String receivedJson = loadJson("TariffIn3.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN3);
        assertNotNull(tariffRecords);
        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN3[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffInSwisspower() throws Exception {

        int nIterations = JsonReader.determineRequiredIterations(KEYWORD_MAP_TARIFF_SWISSPOWER_IN);
        assertEquals(2, nIterations);

        String receivedJson = loadJson("TariffInSwisspower.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_SWISSPOWER_IN);
        assertNotNull(tariffRecords);
        assertEquals(96, tariffRecords.size());

        // only check first 4 records
        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < 4; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_SWISSPOWER_IN[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffInGroupeE() throws Exception {

        int nIterations = JsonReader.determineRequiredIterations(KEYWORD_MAP_TARIFF_GROUPEE_IN);
        assertEquals(1, nIterations);

        String receivedJson = loadJson("TariffInGroupeE.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_GROUPEE_IN);
        assertNotNull(tariffRecords);
        assertEquals(96, tariffRecords.size());

        // only check first 4 records
        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < 4; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_GROUPEE_IN[i], tarifRecord[i].toString());
        }
    }

    @Test
    void jsonWriter_build_Tariff1() throws Exception {

        String expectedOutputJson = loadJson("TariffOut1.json");
        String inputJson = loadJson("TariffIn1.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_IN1);
        assertNotNull(tariffRecords);
        assertEquals(6, tariffRecords.size());

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_OUT1);
        JsonNode jsonResult = builder.buildJsonNode(tariffRecords.values());
        assertTrue(jsonResult.isArray());
        assertEquals(3, jsonResult.size());

        JsonNode expectedJsonResult = MAPPER.readTree(expectedOutputJson);
        assertEquals(expectedJsonResult.toString(), jsonResult.toString());
    }

    @Test
    void jsonWriter_build_Swisspower() throws Exception {
        String expectedOutputJson = loadJson("TariffOutSwisspower_withTariffName.json");
        String inputJson = loadJson("TariffInSwisspower.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_SWISSPOWER_IN);
        assertNotNull(tariffRecords);
        assertEquals(96, tariffRecords.size());

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_SWISSPOWER_OUT);
        JsonNode jsonResult = builder.buildJsonNode(tariffRecords.values());
        assertTrue(jsonResult.isArray());
        assertEquals(96, jsonResult.size());

        JsonNode expectedJsonResult = MAPPER.readTree(expectedOutputJson);
        assertEquals(expectedJsonResult.toString(), jsonResult.toString());
    }

    @Test
    void jsonWriter_build_GroupeE() throws Exception {
        String expectedOutputJson = loadJson("TariffOutGroupeE.json");
        String inputJson = loadJson("TariffInGroupeE.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_GROUPEE_IN);
        assertNotNull(tariffRecords);
        assertEquals(96, tariffRecords.size());

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_GROUPEE_OUT);
        JsonNode jsonResult = builder.buildJsonNode(tariffRecords.values());
        assertTrue(jsonResult.isArray());
        assertEquals(96, jsonResult.size());

        JsonNode expectedJsonResult = MAPPER.readTree(expectedOutputJson);
        assertEquals(expectedJsonResult.toString(), jsonResult.toString());
    }
}
