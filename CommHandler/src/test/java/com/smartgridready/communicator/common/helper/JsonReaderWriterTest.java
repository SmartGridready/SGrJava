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
        KEYWORD_MAP_TARIFF_IN1.put("startTime",  "[*].startTime");
        KEYWORD_MAP_TARIFF_IN1.put("endTime",    "[*].endTime");
        KEYWORD_MAP_TARIFF_IN1.put("tariffName", "[*].tariffs[*].name");
        KEYWORD_MAP_TARIFF_IN1.put("tariff",     "[*].tariffs[*].tariff");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN2 = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN2.put("tariffName", "[*].tariffName");
        KEYWORD_MAP_TARIFF_IN2.put("startTime",  "[*].periods[*].startTime");
        KEYWORD_MAP_TARIFF_IN2.put("endTime",    "[*].periods[*].endTime");
        KEYWORD_MAP_TARIFF_IN2.put("tariff",     "[*].periods[*].tariff");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_IN3 = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_IN3.put("startTime",  "[*].startTime");
        KEYWORD_MAP_TARIFF_IN3.put("endTime",    "[*].endTime");
        KEYWORD_MAP_TARIFF_IN3.put("tariffName", "[*].tariffName");
        KEYWORD_MAP_TARIFF_IN3.put("tariff",     "[*].tariff");
    }

    private static final Map<String, String> KEYWORD_MAP_TARIFF_SWISSPOWER = new HashMap<>();
    static {
        KEYWORD_MAP_TARIFF_SWISSPOWER.put("startTime",  "prices[*].start_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER.put("endTime",    "prices[*].end_timestamp");
        KEYWORD_MAP_TARIFF_SWISSPOWER.put("tariff",     "prices[*].price");
        KEYWORD_MAP_TARIFF_SWISSPOWER.put("unit",       "prices[*].unit");
    }

    private static  final LinkedHashMap<String, String> KEYWORD_MAP_TARIFF_OUT1 = new LinkedHashMap<>();
    static {
        KEYWORD_MAP_TARIFF_OUT1.put("startTime",  "[*].start_time");
        KEYWORD_MAP_TARIFF_OUT1.put("endTime",    "[*].end_time");
        KEYWORD_MAP_TARIFF_OUT1.put("tariffName", "[*].tariffs[*].name");
        KEYWORD_MAP_TARIFF_OUT1.put("tariff",     "[*].tariffs[*].tariff");
        KEYWORD_MAP_TARIFF_OUT1.put("unit",       "[*].tariffs[*].unit");
    }

    private static final String[] EXPECTED_TARIFF_RECORDS_IN1 = {
            "{startTime=2023-11-17T00:00:00+01:00, tariff=15.0, endTime=2023-11-17T00:15:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:00:00+01:00, tariff=12.0, endTime=2023-11-17T00:15:00+01:00, tariffName=moonlight}",
            "{startTime=2023-11-17T00:15:00+01:00, tariff=17.0, endTime=2023-11-17T00:30:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:15:00+01:00, tariff=14.0, endTime=2023-11-17T00:30:00+01:00, tariffName=moonlight}",
            "{startTime=2023-11-17T00:30:00+01:00, tariff=16.0, endTime=2023-11-17T00:45:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:30:00+01:00, tariff=13.0, endTime=2023-11-17T00:45:00+01:00, tariffName=moonlight}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN2 = {
            "{startTime=2023-11-17T00:00:00+01:00, tariff=15.0, endTime=2023-11-17T00:15:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:15:00+01:00, tariff=17.0, endTime=2023-11-17T00:30:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:30:00+01:00, tariff=16.0, endTime=2023-11-17T00:45:00+01:00, tariffName=sunlight}",
            "{startTime=2023-11-17T00:00:00+01:00, tariff=12.0, endTime=2023-11-17T00:15:00+01:00, tariffName=moonlight}",
            "{startTime=2023-11-17T00:15:00+01:00, tariff=14.0, endTime=2023-11-17T00:30:00+01:00, tariffName=moonlight}",
            "{startTime=2023-11-17T00:30:00+01:00, tariff=13.0, endTime=2023-11-17T00:45:00+01:00, tariffName=moonlight}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_IN3 = {
            "{tariffName=sunlight, startTime=2023-11-17T00:00:00+01:00, tariff=15.0, endTime=2023-11-17T00:15:00+01:00}",
            "{tariffName=moonlight, startTime=2023-11-17T00:00:00+01:00, tariff=12.0, endTime=2023-11-17T00:15:00+01:00}",
            "{tariffName=sunlight, startTime=2023-11-17T00:15:00+01:00, tariff=17.0, endTime=2023-11-17T00:30:00+01:00}",
            "{tariffName=moonlight, startTime=2023-11-17T00:15:00+01:00, tariff=14.0, endTime=2023-11-17T00:30:00+01:00}",
            "{tariffName=sunlight, startTime=2023-11-17T00:15:00+01:00, tariff=16.0, endTime=2023-11-17T00:30:00+01:00}",
            "{tariffName=moonlight, startTime=2023-11-17T00:30:00+01:00, tariff=13.0, endTime=2023-11-17T00:45:00+01:00}"
    };

    private static final String[] EXPECTED_TARIFF_RECORDS_SWISSPOWER = {
            "{unit=Rp./kWh, startTime=2024-02-14T00:00:00.000+02:00, tariff=6.841903, endTime=2024-02-14T00:15:00.000+02:00}",
            "{unit=Rp./kWh, startTime=2024-02-14T00:15:00.000+02:00, tariff=6.020112, endTime=2024-02-14T00:30:00.000+02:00}",
            "{unit=Rp./kWh, startTime=2024-02-14T00:30:00.000+02:00, tariff=6.655699, endTime=2024-02-14T00:45:00.000+02:00}",
            "{unit=Rp./kWh, startTime=2024-02-14T00:45:00.000+02:00, tariff=6.671192, endTime=2024-02-14T01:00:00.000+02:00}"
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
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(receivedJson, KEYWORD_MAP_TARIFF_SWISSPOWER);

        assertEquals(4, tariffRecords.size());

        Object[] tarifRecord = tariffRecords.values().toArray();
        for (int i = 0; i < tarifRecord.length; i++) {
            assertEquals(EXPECTED_TARIFF_RECORDS_SWISSPOWER[i], tarifRecord[i].toString());
        }
    }

    @Test
    void jsonWriter_build() throws Exception {

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
        Map<JsonReader.Key, Map<String, Object>> tariffRecords = JsonReader.mapToFlatList(inputJson, KEYWORD_MAP_TARIFF_SWISSPOWER);

        JsonWriter builder = new JsonWriter(KEYWORD_MAP_TARIFF_OUT1);
        String jsonResult = builder.buildJsonString(tariffRecords.values());

        assertEquals(MAPPER.readTree(expectedOutputJson), MAPPER.readTree(jsonResult));
    }
}
