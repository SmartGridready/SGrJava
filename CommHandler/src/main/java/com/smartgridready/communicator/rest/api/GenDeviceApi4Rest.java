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

package com.smartgridready.communicator.rest.api;

import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.communicator.rest.impl.SGrRestApiDevice;

import java.io.IOException;
import java.util.Properties;

/**
 * Defines the communication interface API for HTTP/REST devices.
 */
public interface GenDeviceApi4Rest extends GenDeviceApi {

    /**
     * Authenticates the REST API client.
     * Authenticate uses the authentication method and credentials given within the external interface description XML
     * of the device. The credentials may be given as parameters when loading the {@link SGrRestApiDevice}
     * using the properties parameter of the {@link DeviceDescriptionLoader}.
     *
     * @throws RestApiAuthenticationException If the authentication failed.
     * @throws IOException If the communication with the server failed.
     * @throws RestApiServiceCallException If the service call could not be executed on the remote side.
     * @throws RestApiResponseParseException If parsing of the service response failed.
     * @deprecated Since version 2.2.0
     *
     * @see SGrRestApiDevice
     * @see DeviceDescriptionLoader
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    void authenticate()
            throws RestApiAuthenticationException, IOException, RestApiServiceCallException, RestApiResponseParseException;

    /**
     * Reads a value in its string representation from the REST API device.
     *
     * @param profileName The name of the functional profile.
     * @param dataPointName The name of the data point within the functional profile.
     * @return The value read from the device.
     * @throws IOException If the communication with the server failed.
     * @throws RestApiServiceCallException If the service call could not be executed on the remote side.
     * @throws RestApiResponseParseException If parsing of the service response failed.
     * @throws GenDriverException If a common error occured.
     */
    Value getVal(String profileName, String dataPointName)
            throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException;

    /**
     * Reads a value in its string representation from the REST API device.
     *
     * @param profileName The name of the functional profile.
     * @param dataPointName The name of the data point within the functional profile.
     * @param parameters Key value pairs with request parameters
     * @return The value read from the device.
     * @throws IOException If the communication with the server failed.
     * @throws RestApiServiceCallException If the service call could not be executed on the remote side.
     * @throws RestApiResponseParseException If parsing of the service response failed.
     * @throws GenDriverException If a common error occurred.
     */
    Value getVal(String profileName, String dataPointName, Properties parameters)
            throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException;


    /**
     * Writes a value to the REST API device.
     * The value may be provided as:
     * <ul>
     *     <li>single string value</li>
     *     <li>http request body on json or XML</li>
     * </ul>
     * depending on the value parameter defined in the external interface XML.
     *
     * @param profileName The name of the functional profile.
     * @param dataPointName The name of the data point within the functional profile.
     * @param value The value to be written. Will replace the value tagged with {@code [[value]]} within the
     *              external interface {@link com.smartgridready.ns.v0.RestApiServiceCall} element for the given data point.
     * @throws IOException If the communication with the server failed.
     * @throws RestApiServiceCallException If the service call could not be executed on the remote side.
     * @throws RestApiResponseParseException If parsing of the service response failed.
     * @throws GenDriverException If a common error occurred.
     */
    void setVal(String profileName, String dataPointName, Value value)
            throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException;
}
