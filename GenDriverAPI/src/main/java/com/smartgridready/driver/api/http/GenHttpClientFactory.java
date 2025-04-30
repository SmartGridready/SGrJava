package com.smartgridready.driver.api.http;

import java.net.URISyntaxException;

/**
 * Defines the interface of an HTTP request factory.
 */
public interface GenHttpClientFactory {

	/**
	 * Creates a new request.
	 * @return a new instance of {@code GenHttpRequest}
	 */
	GenHttpRequest createHttpRequest();

	/**
	 * Creates a new request.
	 * Can turn off HTTPS certificate verification.
	 * @param verifyCertificate verify certificate if true, otherwise do not
	 * @return a new instance of {@code GenHttpRequest}
	 */
	GenHttpRequest createHttpRequest(boolean verifyCertificate);

	/**
	 * Creates an URI builder.
	 * @param baseUri the URI to begin with
	 * @return a new instance of {@code GenUriBuilder}
	 * @throws URISyntaxException when base URI is invalid
	 */
	GenUriBuilder createUriBuilder(String baseUri) throws URISyntaxException;
}
