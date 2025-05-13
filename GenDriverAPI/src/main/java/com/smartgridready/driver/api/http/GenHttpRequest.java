package com.smartgridready.driver.api.http;

import java.io.IOException;
import java.net.URI;

/**
 * Defines the interface of an HTTP request.
 */
public interface GenHttpRequest {

    /**
     * Executes the request and delivers the response.
     * @return a new instance of {@link GenHttpResponse}
     * @throws IOException when the request failed
     */
    GenHttpResponse execute() throws IOException;

    /**
     * Sets the request URI.
     * @param uri the URI
     * @return the same instance of {@link GenHttpRequest}
     */
    GenHttpRequest setUri(URI uri);

    /**
     * Sets the HTTP method.
     * @param httpMethod the HTTP method
     */
    void setHttpMethod(HttpMethod httpMethod);

    /**
     * Adds a request header.
     * @param key the header name
     * @param value the header value
     */
    void addHeader(String key, String value);

    /**
     * Sets the request body.
     * @param body the request body as string
     */
    void setBody(String body);

    /**
     * Adds a form parameter to the request.
     * @param key the parameter name
     * @param value the parameter value
     */
    void addFormParam(String key, String value);
}
