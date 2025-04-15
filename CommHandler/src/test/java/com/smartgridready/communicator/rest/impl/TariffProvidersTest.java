package com.smartgridready.communicator.rest.impl;

import com.smartgridready.communicator.common.api.SGrDeviceBuilder;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TariffProvidersTest {

    static final Logger LOG = LoggerFactory.getLogger(TariffProvidersTest.class);

    private static final String XML_BASE_DIR="../../SGrSpecifications/XMLInstances/ExtInterfaces/";

    @Test
    void testSwisspower() throws Exception {

        var properties = new Properties();
        properties.put("token", "19d6ca0bb9bf4d8b6525440eead80da6");
        properties.put("metering_code", "CH1018601234500000000000000011642");

        var dynamicParameters = new Properties();
        dynamicParameters.put("start_timestamp", "2025-01-01T00:00:00+01:00");
        dynamicParameters.put("end_timestamp", "2025-01-01T01:00:00+01:00");

        var device = new SGrDeviceBuilder()
                .eid(Path.of(XML_BASE_DIR + "SGr_05_mmmm_dddd_Dynamic_Tariffs_Swisspower_V0.0.2.xml"))
                .properties(properties)
                .build();
        
        var dynamicRequestParameters = device
                .getDataPoint("DynamicTariff", "TariffSupply")
                .getDynamicRequestParameters()
                .stream()
                .collect(Collectors.toMap(e -> e.getName(), e -> e));
        assertTrue(dynamicRequestParameters.containsKey("start_timestamp"));
        assertTrue(dynamicRequestParameters.containsKey("end_timestamp"));

        var result = device.getVal("DynamicTariff", "TariffSupply", dynamicParameters);
        LOG.info(result.getString());

        String expected = "[{\"start_timestamp\":\"2025-01-01T00:00:00";
        assertThat(result.getString(), CoreMatchers.startsWith(expected));
    }

    @Test
    void testGroupeE() throws Exception {

        var dynamicParameters = new Properties();
        dynamicParameters.put("start_timestamp", "2025-01-01T00:00:00+01:00");
        dynamicParameters.put("end_timestamp", "2025-01-02T00:00:00+01:00");

        var device = new SGrDeviceBuilder()
                .eid(Path.of(XML_BASE_DIR + "SGr_05_mmmm_dddd_Dynamic_Tariffs_GroupeE_V0.0.2.xml"))
                .build();

        var dynamicRequestParameters = device
                .getDataPoint("DynamicTariff", "TariffSupply")
                .getDynamicRequestParameters()
                .stream()
                .collect(Collectors.toMap(e -> e.getName(), e -> e));
        assertTrue(dynamicRequestParameters.containsKey("start_timestamp"));
        assertTrue(dynamicRequestParameters.containsKey("end_timestamp"));

        var result = device.getVal("DynamicTariff", "TariffSupply", dynamicParameters);
        LOG.info(result.getString());

        assertThat(result.getString(),
                CoreMatchers.startsWith("[{\"start_timestamp\":\"2025-01-01T00:00:00"));
    }
}
