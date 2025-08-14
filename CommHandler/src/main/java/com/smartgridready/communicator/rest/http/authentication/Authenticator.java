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

package com.smartgridready.communicator.rest.http.authentication;

import java.io.IOException;

import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.driver.api.http.GenHttpClientFactory;

/**
 * Defines the interface of an HTTP authenticator.
 */
public interface Authenticator {

	/**
	 * Retrieves the value of the authorization header of HTTP requests.
	 * @param deviceDescription the SGr device specification
	 * @param httpClientFactory the SGr HTTP client factory
	 * @return a string
	 * @throws IOException when a communication error occurred
	 * @throws RestApiServiceCallException when the authentication failed
	 * @throws RestApiResponseParseException when the response could not be parsed
	 */
	String getAuthorizationHeaderValue(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws IOException, RestApiServiceCallException, RestApiResponseParseException;

	/**
	 * Tells if access tokens can be renewed instead of re-authenticating.
	 * Defaults to {@code false}.
	 * @return a boolean
	 */
	default boolean isTokenRenewalSupported() { return false; }

	/**
	 * Renews the access token.
	 * Defaults to an empty implementation.
	 * @param deviceDescription the SGr device specification
	 * @param httpClientFactory the SGr HTTP client factory
	 * @throws IOException when a communication error occurred
	 * @throws RestApiServiceCallException when the renewal failed
	 * @throws RestApiResponseParseException when the response could not be parsed
	 */
	default void renewToken(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws IOException, RestApiServiceCallException, RestApiResponseParseException {}
}
