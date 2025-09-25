package com.smartgridready.communicator.common.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartgridready.communicator.common.api.dto.ProductInfo;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.http.GenHttpRequest;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.GenUriBuilder;
import com.smartgridready.driver.api.http.HttpStatus;

@ExtendWith(value = MockitoExtension.class)
public class SGrDeclarationLibraryTest {

    @Mock
    GenHttpClientFactory restServiceClientFactory;

    @Mock
    GenHttpRequest httpRequest;

    @Mock
    GenUriBuilder uriBuilder;

    @Test
    void getEidXml() throws Exception {
        final String expectedContent = "<DeviceFrame></DeviceFrame>";

        when(restServiceClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
        when(restServiceClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(expectedContent, HttpStatus.OK, ""));

        var xml = new SGrDeclarationLibrary(restServiceClientFactory)
            .getProductEidXml("SGr_02_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml");

            assertInstanceOf(String.class, xml);
            assertEquals(expectedContent, xml);
    }

    @Test
    void getEidInfo() throws Exception {
        final String expectedContent = "{\"identifier\":\"SGr_02_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml\",\"releaseState\":\"Published\",\"testState\":\"Tested\",\"manufacturer\":\"Stiebel-Eltron\",\"productName\":\"Internet Service Gateway ISG\",\"productType\":\"HeatPumpAppliance\",\"interface\":\"Modbus\",\"level\":\"2m\",\"version\":\"1.0.0\",\"swRevision\":\"1.0.0\",\"hwRevision\":\"1.1.0\"}";

        when(restServiceClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
        when(restServiceClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        when(httpRequest.execute()).thenReturn(GenHttpResponse.of(expectedContent, HttpStatus.OK, ""));

        var info = new SGrDeclarationLibrary(restServiceClientFactory)
            .getProductInfo("SGr_02_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml");

            assertInstanceOf(ProductInfo.class, info);
            assertEquals("SGr_02_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml", info.getIdentifier());
            assertEquals("Published", info.getReleaseState());
            assertEquals("Tested", info.getTestState());
            assertEquals("Stiebel-Eltron", info.getManufacturer());
            assertEquals("Internet Service Gateway ISG", info.getProductName());
            assertEquals("HeatPumpAppliance", info.getProductType());
            assertEquals("Modbus", info.getInterface());
            assertEquals("2m", info.getLevel());
            assertEquals("1.0.0", info.getVersion());
            assertEquals("1.0.0", info.getSwRevision());
            assertEquals("1.1.0", info.getHwRevision());
    }
}
