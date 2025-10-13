package com.smartgridready.communicator.common.api;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.communicator.common.api.dto.ProductInfo;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;
import com.smartgridready.communicator.rest.http.client.RestServiceClient;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.http.HttpHeaders;
import com.smartgridready.ns.v0.HttpMethod;
import com.smartgridready.ns.v0.ObjectFactory;

/**
 * Provides a client for the SGr declaration library.
 * Can be used to retrieve EI-XML data and information.
 * The implementation is backed by the commhandler's HTTP driver.
 */
public class SGrDeclarationLibrary {

    private static final String LIBRARY_BASE_URL = "https://library.smartgridready.ch";
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private static final String APPLICATION_XML = "application/xml;charset=UTF-8";

    private final GenHttpClientFactory httpClientFactory;
    private final ObjectFactory factory;

    /**
     * Constructs with default HTTP implementation.
     */
    public SGrDeclarationLibrary() {
        this(DriverFactoryLoader.getRestApiDriver());
    }

    /**
     * Constructs with custom HTTP implementation.
     * @param httpClientFactory the HTTP client factory implementation
     */
    public SGrDeclarationLibrary(GenHttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
        this.factory = new ObjectFactory();
    }

    /**
     * Gets product EI-XML data.
     * @param eidName the EID name
     * @return EI-XML as string
     * @throws IOException when the request failed
     */
    public String getProductEidXml(String eidName) throws IOException {
        var path = new StringBuffer()
            .append("/prodx/")
            .append(eidName)
            .toString();

        var headers = Map.of(HttpHeaders.ACCEPT, APPLICATION_XML);
        var client = createClient(HttpMethod.GET, path, headers);
        var response = client.callService();
        if (response.isOk()) {
            return response.getResponse();
        }
        throw new IOException(String.format("Failed with status %d - %s", response.getResponseCode(), response.getReason()));
    }

    /**
     * Gets information about a product EID.
     * @param eidName the EID name
     * @return an instance of {@link ProductInfo}
     * @throws IOException when the request or decoding failed
     */
    public ProductInfo getProductInfo(String eidName) throws IOException {
        var path = new StringBuffer()
            .append("/prods/")
            .append(eidName)
            .toString();

        var headers = Map.of(HttpHeaders.ACCEPT, APPLICATION_JSON);
        var client = createClient(HttpMethod.GET, path, headers);
        var response = client.callService();
        if (response.isOk()) {
            return new ObjectMapper().readValue(response.getResponse(), ProductInfo.class);
        }
        throw new IOException(String.format("Failed with status %d - %s", response.getResponseCode(), response.getReason()));
    }

    private RestServiceClient createClient(HttpMethod method, String path, Map<String, String> headers) throws IOException {
        var callHeaders = factory.createHeaderList();
        headers.forEach((name, value) -> {
            var header = factory.createHeaderEntry();
            header.setHeaderName(name);
            header.setValue(value);
            callHeaders.getHeader().add(header);
        });
        
        var call = factory.createRestApiServiceCall();
        call.setRequestPath(path);
        call.setRequestMethod(method);
        call.setRequestHeader(callHeaders);

        return RestServiceClient.of(LIBRARY_BASE_URL, true, call, httpClientFactory);
    }
}
