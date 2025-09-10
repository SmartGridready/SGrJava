package com.smartgridready.communicator.common.helper;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonReaderWriterTest extends JsonMapperTestBase
{

    private static  final  Map<String, String> KEYWORD_MAP_TARIFF_IN1 = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN1.put("start_timestamp",  "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN1.put("end_timestamp",    "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN1.put("component", "[*].integrated[*].component");
        KEYWORD_MAP_TARIFF_IN1.put("value",     "[*].integrated[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN2 = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN2.put("component", "[*].component");
        KEYWORD_MAP_TARIFF_IN2.put("start_timestamp",  "[*].periods[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN2.put("end_timestamp",    "[*].periods[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN2.put("value",     "[*].periods[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN3 = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN3.put("start_timestamp",  "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_IN3.put("end_timestamp",    "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_IN3.put("component", "[*].component");
        KEYWORD_MAP_TARIFF_IN3.put("value",     "[*].value");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_SWISSPOWER_IN = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("start_timestamp",  "prices[*].start_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("end_timestamp",    "prices[*].end_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("value",     "prices[*].electricity[*].value");
        KEYWORD_MAP_TARIFF_SWISSPOWER_IN.put("unit",       "prices[*].electricity[*].unit");
    }

    private static  final LinkedHashMap<String, String> KEYWORD_MAP_TARIFF_SWISSPOWER_OUT = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("start_timestamp",  "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("end_timestamp",    "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("value",     "[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_SWISSPOWER_OUT.put("unit",       "[*].integrated[*].unit");
    }

    private static  final LinkedHashMap<String, String> KEYWORD_MAP_TARIFF_OUT1 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_OUT1.put("start_timestamp",  "[*].start_timestamp");
        KEYWORD_MAP_TARIFF_OUT1.put("end_timestamp",    "[*].end_timestamp");
        KEYWORD_MAP_TARIFF_OUT1.put("component", "[*].integrated[*].component");
        KEYWORD_MAP_TARIFF_OUT1.put("value",     "[*].integrated[*].value");
        KEYWORD_MAP_TARIFF_OUT1.put("unit",       "[*].integrated[*].unit");
    }

    private static final String[] EXPECTED_TARIFF_RECORDS_IN1 = {
            "{component=sunlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=15.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=12.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=17.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=14.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:45:00+01:00, start_timestamp=2023-11-17T00:30:00+01:00, value=16.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:45:00+01:00, start_timestamp=2023-11-17T00:30:00+01:00, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN2 = {
            "{component=sunlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=15.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=17.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:45:00+01:00, start_timestamp=2023-11-17T00:30:00+01:00, value=16.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=12.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=14.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:45:00+01:00, start_timestamp=2023-11-17T00:30:00+01:00, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN3 = {
            "{component=sunlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=15.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:15:00+01:00, start_timestamp=2023-11-17T00:00:00+01:00, value=12.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=17.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=14.0}",
            "{component=sunlight, end_timestamp=2023-11-17T00:30:00+01:00, start_timestamp=2023-11-17T00:15:00+01:00, value=16.0}",
            "{component=moonlight, end_timestamp=2023-11-17T00:45:00+01:00, start_timestamp=2023-11-17T00:30:00+01:00, value=13.0}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_SWISSPOWER_IN = {
            "{unit=Rp./kWh, end_timestamp=2024-02-14T00:15:00+01:00, start_timestamp=2024-02-14T00:00:00+01:00, value=6.841903}",
            "{unit=Rp./kWh, end_timestamp=2024-02-14T00:30:00+01:00, start_timestamp=2024-02-14T00:15:00+01:00, value=6.020112}",
            "{unit=Rp./kWh, end_timestamp=2024-02-14T00:45:00+01:00, start_timestamp=2024-02-14T00:30:00+01:00, value=6.655699}",
            "{unit=Rp./kWh, end_timestamp=2024-02-14T01:00:00+01:00, start_timestamp=2024-02-14T00:45:00+01:00, value=6.671192}"
    };

    @Test
    void convertToFlatList_TariffIn1() throws Exception {

        String receivedJson = loadJson("TariffIn1.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN1);

        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN1[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffIn2() throws Exception {

        String receivedJson = loadJson("TariffIn2.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN2);

        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN2[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffIn3() throws Exception {

        String receivedJson = loadJson("TariffIn3.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_IN3);

        assertEquals(6, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_IN3[i], tarifRecord[i].toString());
        }
    }

    @Test
    void convertToFlatList_TariffInSwisspower() throws Exception {

        String receivedJson = loadJson("TariffInSwisspower.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_SWISSPOWER_IN);

        assertEquals(4, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_SWISSPOWER_IN[i], tarifRecord[i].toString());
        }
    }

    @Test
    void jsonWriter_build_Tariff1() throws Exception {

        String expectedOutputJson = loadJson("TariffOut1.json");
        String inputJson = loadJson("TariffIn1.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_IN1);

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_OUT1);
        String jsonResult = builder.buildJsonString(tariffRecords.values());

        assertEquals(MAPPER.readTree(expectedOutputJson), MAPPER.readTree(jsonResult));
    }

    @Test
    void jsonWriter_build_Swisspower() throws Exception {
        String expectedOutputJson = loadJson("TariffOutSwisspower.json");
        String inputJson = loadJson("TariffInSwisspower.json");
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_SWISSPOWER_IN);

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_SWISSPOWER_OUT);
        String jsonResult = builder.buildJsonString(tariffRecords.values());

        assertEquals(MAPPER.readTree(expectedOutputJson), MAPPER.readTree(jsonResult));
    }
}
