package com.smartgridready.communicator.common.helper;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.JMESPathMapping;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.ns.v0.RestApiServiceCall;
import io.vavr.Tuple3;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonMapperTest extends JsonMapperTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapperTest.class);

    @Test
    void mapSwisspower() throws Exception {

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_mmmm_dddd_Dynamic_Tariffs_Swisspower_Test_V1.0.xml");
        DeviceFrame deviceFrame = device._1;

        JMESPathMapping jmesPathMapping = getJmesPathMapping(deviceFrame);

        // test keyword map
        Map<String, String> mapFrom = new LinkedHashMap<>();
        Map<String, String> mapTo = new LinkedHashMap<>();
        Map<String, String> names = new LinkedHashMap<>();
        JsonHelper.buildMappingTables(jmesPathMapping.getMapping(), mapFrom, mapTo, names);
        for (var entry: mapFrom.entrySet()) {
            LOG.debug("mapFrom: {} -> {}", entry.getKey(), entry.getValue());
        }
        for (var entry: mapTo.entrySet()) {
            LOG.debug("mapTo: {} -> {}", entry.getKey(), entry.getValue());
        }
        for (var entry: names.entrySet()) {
            LOG.debug("names: {} -> {}", entry.getKey(), entry.getValue());
        }
        assertEquals(5, mapFrom.size());
        assertEquals(5, mapTo.size());
        assertEquals(0, names.size());

        // map JSON
        String jsonResponse = loadJson("TariffInSwisspower.json");
        String expectedJson = loadJson("TariffOutSwisspower_withTariffName.json");

        String jsonResult = JsonHelper.mapJsonResponse(jmesPathMapping, jsonResponse).getJson().toString();
        LOG.debug("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson).toString(), jsonResult);
    }

    @Test
    void mapSwisspower_Jsonata() throws Exception {

        String expression = "prices[].{\"start_timestamp\":start_timestamp,\"end_timestamp\":end_timestamp,\"integrated\":integrated[].{\"value\":value,\"unit\":unit,\"component\":component}}";

        // map JSON
        String jsonResponse = loadJson("TariffInSwisspower.json");
        String expectedJson = loadJson("TariffOutSwisspower_withTariffName.json");

        String jsonResult = JsonHelper.parseJsonResponseWithJsonata(expression, jsonResponse).getJson().toString();
        LOG.debug("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson).toString(), jsonResult);
    }

    @Test
    void mapGroupeE() throws Exception {

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_mmmm_dddd_Dynamic_Tariffs_GroupeE_V1.0.xml");
        DeviceFrame deviceFrame = device._1;

        JMESPathMapping jmesPathMapping = getJmesPathMapping(deviceFrame);

        // test keyword map
        Map<String, String> mapFrom = new LinkedHashMap<>();
        Map<String, String> mapTo = new LinkedHashMap<>();
        Map<String, String> names = new LinkedHashMap<>();
        JsonHelper.buildMappingTables(jmesPathMapping.getMapping(), mapFrom, mapTo, names);
        for (var entry: mapFrom.entrySet()) {
            LOG.debug("mapFrom: {} -> {}", entry.getKey(), entry.getValue());
        }
        for (var entry: mapTo.entrySet()) {
            LOG.debug("mapTo: {} -> {}", entry.getKey(), entry.getValue());
        }
        for (var entry: names.entrySet()) {
            LOG.debug("names: {} -> {}", entry.getKey(), entry.getValue());
        }
        assertEquals(4, mapFrom.size());
        assertEquals(4, mapTo.size());
        assertEquals(0, names.size());

        // map JSON
        String jsonResponse = loadJson("TariffInGroupeE.json");
        String expectedJson = loadJson("TariffOutGroupeE.json");

        String jsonResult = JsonHelper.mapJsonResponse(jmesPathMapping, jsonResponse).getJson().toString();
        LOG.debug("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson).toString(), jsonResult);
    }

    @Test
    void mapGroupeE_JSONata() throws Exception {

        String expression = "$.{\"start_timestamp\":start_timestamp,\"end_timestamp\":end_timestamp,\"integrated\":[{\"value\":vario_plus,\"unit\":unit}]}";

        // map JSON
        String jsonResponse = loadJson("TariffInGroupeE.json");
        String expectedJson = loadJson("TariffOutGroupeE.json");

        String jsonResult = JsonHelper.parseJsonResponseWithJsonata(expression, jsonResponse).getJson().toString();
        LOG.debug("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson).toString(), jsonResult);
    }

    private static JMESPathMapping getJmesPathMapping(DeviceFrame deviceFrame) {

        var restApiConfigurationContent = deviceFrame
                .getInterfaceList()
                .getRestApiInterface()
                .getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getRestApiDataPointConfiguration().getContent();

        for (var jaxbelement : restApiConfigurationContent) {
            if (jaxbelement.getValue() instanceof RestApiServiceCall) {
                return ((RestApiServiceCall)jaxbelement.getValue()).getResponseQuery().getJmesPathMappings();
            }
        }
        throw new IllegalArgumentException("Device Frame does not contain RestApiServiceCall");
    }

    private static String getQueryExpression(DeviceFrame deviceFrame) {

        var restApiConfigurationContent = deviceFrame
                .getInterfaceList()
                .getRestApiInterface()
                .getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getRestApiDataPointConfiguration().getContent();

        for (var jaxbelement : restApiConfigurationContent) {
            if (jaxbelement.getValue() instanceof RestApiServiceCall) {
                return ((RestApiServiceCall)jaxbelement.getValue()).getResponseQuery().getQuery();
            }
        }
        throw new IllegalArgumentException("Device Frame does not contain RestApiServiceCall");
    }
}
