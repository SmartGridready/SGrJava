package com.smartgridready.communicator.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;
import com.smartgridready.driver.api.http.GenHttpClientFactory;

import io.vavr.Tuple3;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

public class JsonMapperTestBase {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    protected static String loadJson(String resourceName) throws Exception {

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new FileNotFoundException(resourceName);
        }
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "UTF-8");
        return writer.toString();
    }

    protected static Tuple3<DeviceFrame, SGrDeviceBase<?, ?, ?>, Properties> createDevice(String eiXmlFileName)
            throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL deviceDescUrl = classloader.getResource(eiXmlFileName);

        DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
        DeviceFrame devDesc = loader.load("", deviceDescUrl != null ? deviceDescUrl.getPath() : null);

        GenHttpClientFactory factory = DriverFactoryLoader.getRestApiDriver();
        SGrRestApiDevice restApiDevice = new SGrRestApiDevice(devDesc, factory);
        return new Tuple3<>(devDesc, restApiDevice, null);
    }
}
