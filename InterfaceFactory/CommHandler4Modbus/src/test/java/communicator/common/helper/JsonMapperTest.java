package communicator.common.helper;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.JMESPathMapping;
import communicator.common.impl.SGrDeviceBase;
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

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_Swisspower_Dynamic_Tariffs_0.0.1.xml");
        DeviceFrame deviceFrame = device._1;

        JMESPathMapping jmesPathMapping = getJmesPathMapping(deviceFrame);

        String jsonResponse = loadJson("TariffInSwisspower.json");
        String expectedJson = loadJson("TariffOutSwisspower_withTariffName.json");

        String jsonResult = JsonHelper.mapJsonResponse(jmesPathMapping, jsonResponse).getString();
        LOG.info("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson), MAPPER.readTree(jsonResult));

    }

    @Test
    void mapGroupeE() throws Exception {

        Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> device = createDevice("SGr_05_GroupeE_Dynamic_Tariffs_0.0.1.xml");
        DeviceFrame deviceFrame = device._1;

        JMESPathMapping jmesPathMapping = getJmesPathMapping(deviceFrame);

        String jsonResponse = loadJson("TariffInGroupeE.json");
        String expectedJson = loadJson("TariffOutGroupeE.json");

        String jsonResult = JsonHelper.mapJsonResponse(jmesPathMapping, jsonResponse).getString();
        LOG.info("JSON result: {}", jsonResult);
        assertEquals(MAPPER.readTree(expectedJson), MAPPER.readTree(jsonResult));
    }

    private JMESPathMapping getJmesPathMapping(DeviceFrame deviceFrame) {

        return deviceFrame
                .getInterfaceList()
                .getRestApiInterface()
                .getFunctionalProfileList()
                .getFunctionalProfileListElement().get(0)
                .getDataPointList().getDataPointListElement().get(0)
                .getRestApiDataPointConfiguration().getRestApiServiceCall()
                .getResponseQuery().getJmesPathMappings();
    }

}
