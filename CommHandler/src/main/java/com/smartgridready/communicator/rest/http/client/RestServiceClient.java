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
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.GenHttpRequest;
import com.smartgridready.driver.api.http.GenUriBuilder;
import com.smartgridready.ns.v0.HeaderList;
import com.smartgridready.ns.v0.HttpMethod;
import com.smartgridready.ns.v0.ParameterList;

import com.smartgridready.ns.v0.RestApiServiceCall;
import com.smartgridready.ns.v0.HeaderEntry;

public class RestServiceClient {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final String baseUri;

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

	protected RestServiceClient(String baseUri, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory) throws IOException {
		this(baseUri, serviceCall, httpClientFactory, new Properties());
	}

	protected RestServiceClient(String baseUri, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory, Properties substitutions) throws IOException {
		this.baseUri = replacePropertyPlaceholders(baseUri, substitutions);
		this.restServiceCall = cloneRestServiceCallWithSubstitutions(serviceCall, substitutions);
		this.httpClientFactory = httpClientFactory;
	}

	public void addHeader(String key, String value) {
		if (value != null) {
			HeaderEntry headerEntry = new HeaderEntry();
			headerEntry.setHeaderName(key);
			headerEntry.setValue(value);
			restServiceCall.getRequestHeader().getHeader().add(headerEntry);
		}
	}

	public String getBaseUri() {
		return getBaseUri(new Properties());
	}

	public String getBaseUri(Properties substitutions) {
		return replacePropertyPlaceholders(baseUri, substitutions);
	}

	public RestApiServiceCall getRestServiceCall() {
		return restServiceCall;
	}

	private RestApiServiceCall cloneRestServiceCallWithSubstitutions(RestApiServiceCall restServiceCall, Properties substitutions) throws IOException {

		var serviceCall = cloneRestApiServiceCall(restServiceCall);

		// Substitutions can appear within the request path, request body or even the response query.
		serviceCall.setRequestPath(replacePropertyPlaceholders(serviceCall.getRequestPath(), substitutions));
		serviceCall.setRequestBody(replacePropertyPlaceholders(serviceCall.getRequestBody(), substitutions));

		if (serviceCall.getResponseQuery() != null) {
			serviceCall.getResponseQuery().setQuery(replacePropertyPlaceholders(restServiceCall.getResponseQuery().getQuery(), substitutions));
		}

		ParameterList queryParams = serviceCall.getRequestQuery();
		if (queryParams != null) {
			queryParams.getParameter().forEach(param -> param.setValue(replacePropertyPlaceholders(param.getValue(), substitutions)));
		}

		ParameterList formParams = serviceCall.getRequestForm();
		if (formParams != null) {
			formParams.getParameter().forEach(param -> param.setValue(replacePropertyPlaceholders(param.getValue(), substitutions)));
		}

		HeaderList headers = serviceCall.getRequestHeader();
		if (headers != null) {
			headers.getHeader().forEach(header -> header.setValue(replacePropertyPlaceholders(header.getValue(), substitutions)));
		} else {
			serviceCall.setRequestHeader(new HeaderList());
		}

		return serviceCall;
	}

	public GenHttpResponse callService() throws IOException {

		if (httpClientFactory == null) {
			throw new IOException("No implementation for HTTP client found");
		}

		RestApiServiceCall serviceCall = getRestServiceCall();
		GenHttpRequest httpRequest = httpClientFactory.createHttpRequest();

		httpRequest.setHttpMethod(mapHttpMethod(serviceCall.getRequestMethod()));
		try {
			httpRequest.setUri(buildUri(serviceCall));
		} catch (URISyntaxException e) {
			throw new IOException("Cannot build request URI", e);
		}

		if (restServiceCall.getRequestHeader() != null) {
			restServiceCall.getRequestHeader().getHeader().forEach( headerEntry ->
					httpRequest.addHeader(headerEntry.getHeaderName(), headerEntry.getValue())
			);
		}

		if (serviceCall.getRequestForm() != null) {
			serviceCall.getRequestForm().getParameter().forEach(
					p -> httpRequest.addFormParam(p.getName(), p.getValue())
			);

		} else if (serviceCall.getRequestBody() != null) {
			String content = serviceCall.getRequestBody();
			httpRequest.setBody(content);
		}

		return httpRequest.execute();
	}

	private URI buildUri(RestApiServiceCall serviceCall) throws URISyntaxException {
		final GenUriBuilder uriBuilder = httpClientFactory.createUriBuilder(getBaseUri());

		// add request path
		if (serviceCall.getRequestPath() != null) {
			int startQueryPos = serviceCall.getRequestPath().indexOf('?');
			if (startQueryPos >= 0) {
				// split path and query (old style)
				String path = serviceCall.getRequestPath().substring(0, startQueryPos);
				String query = serviceCall.getRequestPath().substring(startQueryPos + 1);
				uriBuilder.addPath(path);
				uriBuilder.setQueryString(query);
			} else {
				// just set path (new style)
				uriBuilder.addPath(serviceCall.getRequestPath());
			}
		}

		// add query parameters
		if (serviceCall.getRequestQuery() != null) {
			serviceCall.getRequestQuery().getParameter().forEach(p ->
				uriBuilder.addQueryParameter(p.getName(), p.getValue()));
		}

		return uriBuilder.build();
	}

	private static String replacePropertyPlaceholders(String template, Properties properties) {

		String convertedTemplate = template;
		if (template != null && properties != null) {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				// noinspection RegExpRedundantEscape
				convertedTemplate = convertedTemplate.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", (String)entry.getValue());
			}
		}
		return convertedTemplate;
	}

	public static RestServiceClient of(String baseUri, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory) throws IOException {
		return new RestServiceClient(baseUri, serviceCall, httpClientFactory);
	}

	public static RestServiceClient of(String baseUri, RestApiServiceCall serviceCall, GenHttpClientFactory httpClientFactory, Properties substitutions) throws IOException {
		return new RestServiceClient(baseUri, serviceCall, httpClientFactory, substitutions);
	}

	private static com.smartgridready.driver.api.http.HttpMethod mapHttpMethod(HttpMethod httpMethod) throws IOException {
		return Optional.ofNullable(HTTP_METHOD_MAP.get(httpMethod))
				.orElseThrow(() -> new IOException("Unsupported HTTP method: " + httpMethod.name()));
	}

	private static RestApiServiceCall cloneRestApiServiceCall(RestApiServiceCall restApiServiceCall) throws IOException {
		return objectMapper.readValue(
			objectMapper.writeValueAsString(restApiServiceCall),
			RestApiServiceCall.class
		);
	}
}
