/**
Copyright(c) 2022 Verein SmartGridready Switzerland

This Open Source Software is BSD 3 clause licensed:
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in
the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from
this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartgridready.communicator.rest.http.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.GenHttpRequest;
import com.smartgridready.driver.api.http.GenUriBuilder;
import com.smartgridready.ns.v0.HeaderList;
import com.smartgridready.ns.v0.HttpMethod;
import com.smartgridready.ns.v0.ParameterList;

import com.smartgridready.ns.v0.RestApiServiceCall;
import com.smartgridready.utils.StringUtil;
import com.smartgridready.ns.v0.HeaderEntry;

/**
 * Implements an HTTP / REST client.
 */
public class RestServiceClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUri;
    private final boolean verifyCertificate;

    private final RestApiServiceCall restServiceCall;
    private final GenHttpClientFactory httpClientFactory;

    private static final Map<HttpMethod, com.smartgridready.driver.api.http.HttpMethod> HTTP_METHOD_MAP = new EnumMap<>(HttpMethod.class);
    static {
        HTTP_METHOD_MAP.put(HttpMethod.GET, com.smartgridready.driver.api.http.HttpMethod.GET);
        HTTP_METHOD_MAP.put(HttpMethod.POST, com.smartgridready.driver.api.http.HttpMethod.POST);
        HTTP_METHOD_MAP.put(HttpMethod.PUT, com.smartgridready.driver.api.http.HttpMethod.PUT);
        HTTP_METHOD_MAP.put(HttpMethod.PATCH, com.smartgridready.driver.api.http.HttpMethod.PATCH);
        HTTP_METHOD_MAP.put(HttpMethod.DELETE, com.smartgridready.driver.api.http.HttpMethod.DELETE);
    }

    /**
     * Constructs a new instance with empty substitution properties.
     * @param baseUri the base URI for all service calls
     * @param verifyCertificate enables or disables SSL certificate validation
     * @param serviceCall the service call specification
     * @param httpClientFactory the HTTP client factory implementation
     * @throws IOException when the specification contains errors
     */
    protected RestServiceClient(String baseUri, boolean verifyCertificate, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory) throws IOException {
        this(baseUri, verifyCertificate, serviceCall, httpClientFactory, new HashMap<>());
    }

    /**
     * Constructs a new instance.
     * @param baseUri the base URI for all service calls
     * @param verifyCertificate enables or disables SSL certificate validation
     * @param serviceCall the service call specification
     * @param httpClientFactory the HTTP client factory implementation
     * @param substitutions the parameter substitutions
     * @throws IOException when the specification contains errors
     */
    protected RestServiceClient(String baseUri, boolean verifyCertificate, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory, Map<String, String> substitutions) throws IOException {
        this.baseUri = baseUri;
        this.verifyCertificate = verifyCertificate;
        this.restServiceCall = cloneRestServiceCallWithSubstitutions(serviceCall, substitutions);
        this.httpClientFactory = httpClientFactory;
    }

    /**
     * Adds a new request header.
     * @param key the header name
     * @param value the header value
     */
    public void addHeader(String key, String value) {
        if (value != null) {
            HeaderEntry headerEntry = new HeaderEntry();
            headerEntry.setHeaderName(key);
            headerEntry.setValue(value);
            restServiceCall.getRequestHeader().getHeader().add(headerEntry);
        }
    }

    /**
     * Gets the request's base URI.
     * @return a string
     */
    public String getBaseUri() {
        return baseUri;
    }

    /**
     * Tells if SSL certificates are to be verified.
     * @return a boolean
     */
    public boolean isVerifyCertificate() {
        return verifyCertificate;
    }

    /**
     * Gets the SGr specification of the service call.
     * @return an instance of {@link RestApiServiceCall}
     */
    public RestApiServiceCall getRestServiceCall() {
        return restServiceCall;
    }

    private RestApiServiceCall cloneRestServiceCallWithSubstitutions(RestApiServiceCall restServiceCall, Map<String, String> substitutions) throws IOException {

        var serviceCall = cloneRestApiServiceCall(restServiceCall);

        // Substitutions can appear within the request path, request headers, request body or even the response query.
        serviceCall.setRequestPath(substituteParameterPlaceholders(serviceCall.getRequestPath(), substitutions));
        serviceCall.setRequestBody(substituteParameterPlaceholders(serviceCall.getRequestBody(), substitutions));

        if (serviceCall.getResponseQuery() != null) {
            serviceCall.getResponseQuery().setQuery(substituteParameterPlaceholders(restServiceCall.getResponseQuery().getQuery(), substitutions));
        }

        ParameterList queryParams = serviceCall.getRequestQuery();
        if (queryParams != null) {
            queryParams.getParameter().forEach(param -> param.setValue(substituteParameterPlaceholders(param.getValue(), substitutions)));
        }

        ParameterList formParams = serviceCall.getRequestForm();
        if (formParams != null) {
            formParams.getParameter().forEach(param -> param.setValue(substituteParameterPlaceholders(param.getValue(), substitutions)));
        }

        HeaderList headers = serviceCall.getRequestHeader();
        if (headers != null) {
            headers.getHeader().forEach(header -> header.setValue(substituteParameterPlaceholders(header.getValue(), substitutions)));
        } else {
            serviceCall.setRequestHeader(new HeaderList());
        }

        return serviceCall;
    }

    /**
     * Executes the HTTP request.
     * @return an instance of {@link GenHttpResponse}
     * @throws IOException when an error occurred
     */
    public GenHttpResponse callService() throws IOException {

        if (httpClientFactory == null) {
            throw new IOException("No implementation for HTTP client found");
        }

        GenHttpRequest httpRequest = httpClientFactory.createHttpRequest(verifyCertificate);

        httpRequest.setHttpMethod(mapHttpMethod(restServiceCall.getRequestMethod()));
        try {
            httpRequest.setUri(buildUri());
        } catch (URISyntaxException e) {
            throw new IOException("Cannot build request URI", e);
        }

        if (restServiceCall.getRequestHeader() != null) {
            restServiceCall.getRequestHeader().getHeader().forEach(headerEntry -> {
                if (StringUtil.isNotEmpty(headerEntry.getValue())) {
                    httpRequest.addHeader(headerEntry.getHeaderName(), headerEntry.getValue());
                }
            });
        }

        if (restServiceCall.getRequestForm() != null) {
            restServiceCall.getRequestForm().getParameter().forEach(formParam -> {
                if (StringUtil.isNotEmpty(formParam.getValue())) {
                    httpRequest.addFormParam(formParam.getName(), formParam.getValue());
                }
            });
        } else if (restServiceCall.getRequestBody() != null) {
            String content = restServiceCall.getRequestBody();
            httpRequest.setBody(content);
        }

        return httpRequest.execute();
    }

    private URI buildUri() throws URISyntaxException {
        final GenUriBuilder uriBuilder = httpClientFactory.createUriBuilder(getBaseUri());

        // add request path
        if (restServiceCall.getRequestPath() != null) {
            int startQueryPos = restServiceCall.getRequestPath().indexOf('?');
            if (startQueryPos >= 0) {
                // split path and query (old style)
                String path = restServiceCall.getRequestPath().substring(0, startQueryPos);
                String query = restServiceCall.getRequestPath().substring(startQueryPos + 1);
                uriBuilder.addPath(path);
                uriBuilder.setQueryString(query);
            } else {
                // just set path (new style)
                uriBuilder.addPath(restServiceCall.getRequestPath());
            }
        }

        // add query parameters
        if (restServiceCall.getRequestQuery() != null) {
            restServiceCall.getRequestQuery().getParameter().forEach(queryParam -> {
                if (StringUtil.isNotEmpty(queryParam.getValue())) {
                    uriBuilder.addQueryParameter(queryParam.getName(), queryParam.getValue());
                }
            });
        }

        return uriBuilder.build();
    }

    private static String substituteParameterPlaceholders(String template, Map<String, String> parameters) {
        // this is for dynamic parameters
        String convertedTemplate = template;
        if (template != null && parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                // no regex here, string literal replacement is sufficient
                convertedTemplate = convertedTemplate.replace("[[" + entry.getKey() + "]]", StringUtil.getOrEmpty(entry.getValue()));
            }
        }
        return convertedTemplate;
    }

    /**
     * Creates a new instance.
     * @param baseUri the base URI
     * @param verifyCertificate SSL certificate will be verified when true
     * @param serviceCall the SGr specification of the service call
     * @param httpClientFactory the SGr HTTP client factory
     * @return a new instance of {@link RestServiceClient}
     * @throws IOException when an error occurred
     */
    public static RestServiceClient of(String baseUri, boolean verifyCertificate, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory) throws IOException {
        return new RestServiceClient(baseUri, verifyCertificate, serviceCall, httpClientFactory);
    }

    /**
     * Creates a new instance.
     * @param baseUri the base URI
     * @param verifyCertificate SSL certificate will be verified when true
     * @param serviceCall the SGr specification of the service call
     * @param httpClientFactory the SGr HTTP client factory
     * @param substitutions substitutions of dynamic request parameters
     * @return a new instance of {@link RestServiceClient}
     * @throws IOException when an error occurred
     */
    public static RestServiceClient of(String baseUri, boolean verifyCertificate, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory, Map<String, String> substitutions) throws IOException {
        return new RestServiceClient(baseUri, verifyCertificate, serviceCall, httpClientFactory, substitutions);
    }

    private static com.smartgridready.driver.api.http.HttpMethod mapHttpMethod(HttpMethod httpMethod) throws IOException {
        return Optional.ofNullable(HTTP_METHOD_MAP.get(httpMethod))
                .orElseThrow(() -> new IOException("Unsupported HTTP method: " + httpMethod.name()));
    }

    private static RestApiServiceCall cloneRestApiServiceCall(RestApiServiceCall restApiServiceCall) throws IOException {
        // creates a deep copy
        return objectMapper.readValue(
            objectMapper.writeValueAsString(restApiServiceCall),
            RestApiServiceCall.class
        );
    }
}
