package com.smartgridready.driver.api.http;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Defines the interface of an URI builder.
 * Uses a fluent API.
 */
public interface GenUriBuilder {

    /**
     * Sets the raw query string, overriding query parameters.
     * @param queryString the raw query string
     * @return the same instance of {@link GenUriBuilder}
     */
    public GenUriBuilder setQueryString(String queryString);

    /**
     * Adds a request path.
     * @param path the request path
     * @return the same instance of {@link GenUriBuilder}
     */
    public GenUriBuilder addPath(String path);

    /**
     * Adds a query parameter.
     * @param name the parameter name
     * @param value the parameter value
     * @return the same instance of {@link GenUriBuilder}
     */
    public GenUriBuilder addQueryParameter(String name, String value);

    /**
     * Builds the final URI.
     * @return a new instance of {@link URI}
     * @throws URISyntaxException when URI cannot be built
     */
    public URI build() throws URISyntaxException;
}
