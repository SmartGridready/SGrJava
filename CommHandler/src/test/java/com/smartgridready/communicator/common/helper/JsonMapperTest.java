package com.smartgridready.communicator.common.helper;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.JMESPathMapping;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.ns.v0.RestApiServiceCall;
import io.vavr.Tuple3;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonMapperTest extends JsonMapperTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapperTest.class);

    @Test
    void mapSwisspower() throws Exception {

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_mmmm_dddd_Dynamic_Tariffs_Swisspower_V1.0.xml");
        DeviceFrame deviceFrame = device._1;

        String jmesQuery = getJmesQuery(deviceFrame);

        String jsonResponse = loadJson("TariffInSwisspower.json");
        String expectedJson = loadJson("TariffOutSwisspower_withTariffName.json");

        String jsonResult = JsonHelper.parseJsonResponse(jmesQuery, jsonResponse).getString();
        LOG.info("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson), MAPPER.readTree(jsonResult));

    }

    @Test
    void mapGroupeE() throws Exception {

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_mmmm_dddd_Dynamic_Tariffs_GroupeE_V1.0.xml");
        DeviceFrame deviceFrame = device._1;

        JMESPathMapping jmesPathMapping = getJmesPathMapping(deviceFrame);

        String jsonResponse = loadJson("TariffInGroupeE.json");
        String expectedJson = loadJson("TariffOutGroupeE.json");

        String jsonResult = JsonHelper.mapJsonResponse(jmesPathMapping, jsonResponse).getString();
        LOG.info("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson), MAPPER.readTree(jsonResult));
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

    private static String getJmesQuery(DeviceFrame deviceFrame) {

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
