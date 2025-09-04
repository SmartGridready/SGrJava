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

package com.smartgridready.communicator.rest.impl;

import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.HttpHeaders;
import com.smartgridready.driver.api.http.HttpStatus;
import com.smartgridready.communicator.rest.http.client.RestServiceClient;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.ResponseQuery;
import com.smartgridready.ns.v0.ResponseQueryType;
import com.smartgridready.ns.v0.RestApiAuthenticationMethod;
import com.smartgridready.ns.v0.RestApiDataPoint;
import com.smartgridready.ns.v0.RestApiDataPointConfiguration;
import com.smartgridready.ns.v0.RestApiFunctionalProfile;
import com.smartgridready.ns.v0.RestApiInterface;
import com.smartgridready.ns.v0.RestApiInterfaceDescription;
import com.smartgridready.ns.v0.RestApiServiceCall;
import com.smartgridready.ns.v0.RestApiValueMapping;
import com.smartgridready.ns.v0.ValueMapping;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.JsonHelper;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.rest.api.GenDeviceApi4Rest;
import com.smartgridready.communicator.rest.exception.RestApiAuthenticationException;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.communicator.rest.http.authentication.Authenticator;
import com.smartgridready.communicator.rest.http.authentication.AuthenticatorFactory;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.communicator.rest.http.client.RestServiceClientUtils;
import jakarta.xml.bind.JAXBElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Implements a device interface for HTTP / REST APIs.
 */
public class SGrRestApiDevice extends SGrDeviceBase<
		DeviceFrame,
		RestApiFunctionalProfile,
		RestApiDataPoint> implements GenDeviceApi4Rest {
	
	private static final Logger LOG = LoggerFactory.getLogger(SGrRestApiDevice.class);
	
	private final DeviceFrame deviceDescription;
	private final Authenticator httpAuthenticator;
	private final GenHttpClientFactory httpClientFactory;

	private boolean isConnected;

	/**
	 * Constructs a new instance.
	 * @param deviceDescription the EID description
	 * @param httpClientFactory the HTTP client factory implementation
	 * @throws RestApiAuthenticationException on HTTP authentication error
	 */
	public SGrRestApiDevice(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws RestApiAuthenticationException {
		super(deviceDescription);
		this.deviceDescription = deviceDescription;
		this.httpClientFactory = httpClientFactory;
		this.isConnected = false;

		RestApiAuthenticationMethod authMethod = getRestApiInterfaceDescription().getRestApiAuthenticationMethod();
		this.httpAuthenticator = AuthenticatorFactory.getAuthenticator(authMethod);
	}

	@Override
	public void connect() throws GenDriverException {
		try {
			authenticate();
		} catch (Exception e) {
			throw new GenDriverException("Error authenticating", e);
		}
	}

	@Override
	public void disconnect() throws GenDriverException {
        this.isConnected = false;
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}
	
	// TODO make private after removal from interface
	@Override
	public void authenticate() throws RestApiAuthenticationException, IOException, RestApiServiceCallException, RestApiResponseParseException {
		httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, httpClientFactory);
		this.isConnected = true;
	}

	@Override
	public Value getVal(String profileName, String dataPointName)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {

		RestApiDataPoint dataPoint = findProfileDataPoint(profileName, dataPointName);
		checkReadWritePermission(dataPoint, RwpDirections.READ);
		return doReadWriteVal(dataPoint, null, null);
	}

	@Override
	public Value getVal(String profileName, String dataPointName, Properties parameters)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {

		RestApiDataPoint dataPoint = findProfileDataPoint(profileName, dataPointName);
		checkReadWritePermission(dataPoint, RwpDirections.READ);
		return doReadWriteVal(dataPoint, null, parameters);
	}

	@Override
	public void setVal(String profileName, String dataPointName, Value value)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {

		RestApiDataPoint dataPoint = findProfileDataPoint(profileName, dataPointName);
		checkReadWritePermission(dataPoint, RwpDirections.WRITE);
		doReadWriteVal(dataPoint, value, null);
	}

	private Value doReadWriteVal(RestApiDataPoint dataPoint, Value value, Properties parameters)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {
		
		String host = getRestApiInterfaceDescription().getRestApiUri();
		boolean verifyCertificate = (getRestApiInterfaceDescription().getRestApiVerifyCertificate() != null) ? Boolean.valueOf(getRestApiInterfaceDescription().getRestApiVerifyCertificate()) : true;

		Optional<RestApiDataPointConfiguration> dpDescriptionOpt
				= Optional.ofNullable(dataPoint.getRestApiDataPointConfiguration());

		Properties substitutions = new Properties();
		if (dpDescriptionOpt.isPresent()) {

			RwpDirections rwpDirection = (value != null) ? RwpDirections.WRITE : RwpDirections.READ;

			RestApiDataPointConfiguration dpDescription = dpDescriptionOpt.get();
			RestApiServiceCall serviceCall = evaluateRestApiServiceCall(dpDescription, rwpDirection);

			if (value != null) {
				checkOutOfRange(new Value[]{value}, dataPoint);
				value = applyUnitConversion(dataPoint, value, SGrDeviceBase::divide);

				// substitute value mappings generic -> device
				substitutions.put(
					"value",
					(serviceCall.getValueMapping() != null) ? getMappedDeviceValue(value.getString(), serviceCall.getValueMapping()) : value.getString()
				);
			}
			// substitute default values of request parameters
			if (null != dataPoint.getDataPoint().getParameterList()) {
				dataPoint.getDataPoint().getParameterList().getParameterListElement().forEach(p -> {
					String pVal = (null != p.getDefaultValue()) ? p.getDefaultValue() : "";
					substitutions.put(p.getName(), pVal);
				});
			}
			// substitute actual request parameters
			if (parameters != null) {
				substitutions.putAll(parameters);
			}

			RestServiceClient restServiceClient = RestServiceClient.of(host, verifyCertificate, serviceCall, httpClientFactory, substitutions);
			String response = handleServiceCall(restServiceClient, httpAuthenticator.isTokenRenewalSupported());

			if (value == null) {
				value = handleServiceResponse(serviceCall, response);
				return applyUnitConversion(dataPoint, value, SGrDeviceBase::multiply);
			}

			return StringValue.of(response);
		}
		throw  new GenDriverException("Missing 'restAPIDataPoint' description in device description XML file");
	}	

	private String handleServiceCall(RestServiceClient serviceClient, boolean tryTokenRenewal) throws IOException, RestApiServiceCallException, RestApiResponseParseException {

		if (httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, httpClientFactory) != null) {
			serviceClient.addHeader(HttpHeaders.AUTHORIZATION,
					httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, httpClientFactory));
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Calling REST service: {} - {}", 
						serviceClient.getBaseUri(), 
						RestServiceClientUtils.printServiceCall(serviceClient.getRestServiceCall()));
		}

		GenHttpResponse result = serviceClient.callService();
		if (result.isOk()) {
			LOG.debug("Received response: {}", result.getResponse());
			return result.getResponse();
		} else if (tryTokenRenewal && result.getResponseCode() == HttpStatus.UNAUTHORIZED) {
			LOG.info("Authorisation error received. Trying with token renewal");
			httpAuthenticator.renewToken(deviceDescription, httpClientFactory);
			serviceClient.addHeader(HttpHeaders.AUTHORIZATION, httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, httpClientFactory));
			// recurse into handleServiceCall, set tryTokenRenewal always to false here!
			return handleServiceCall(serviceClient, false); 
		} else {
			throw new RestApiServiceCallException(result);
		}
	}

	private Value handleServiceResponse(RestApiServiceCall restApiServiceCall, String response) throws GenDriverException {

		if (restApiServiceCall.getResponseQuery() != null) {
			ResponseQuery responseQuery = restApiServiceCall.getResponseQuery();
			if (responseQuery.getQueryType() != null && ResponseQueryType.JMES_PATH_EXPRESSION == responseQuery.getQueryType()) {
				return JsonHelper.parseJsonResponse(responseQuery.getQuery(), response);
			} else if (responseQuery.getQueryType() != null && ResponseQueryType.JMES_PATH_MAPPING == responseQuery.getQueryType()) {
				return JsonHelper.mapJsonResponse(responseQuery.getJmesPathMappings(), response);
			}
		}

		// return plain response
		
		// substitute value mappings device -> generic
		return StringValue.of(
			(restApiServiceCall.getValueMapping() != null) ? getMappedGenericValue(response, restApiServiceCall.getValueMapping()) : response
		);
	}

	private RestApiServiceCall evaluateRestApiServiceCall(RestApiDataPointConfiguration dataPointConfiguration, RwpDirections rwpDirections)
		throws GenDriverException {

		for(JAXBElement<?> element : dataPointConfiguration.getContent()) {

			if ("restApiServiceCall".equals(element.getName().getLocalPart())) {
				return (RestApiServiceCall) element.getValue();
			}

			if (element.getName().getLocalPart().equals("restApiReadServiceCall") && RwpDirections.READ == rwpDirections) {
				return (RestApiServiceCall) element.getValue();
			}

			if(element.getName().getLocalPart().equals("restApiReadServiceCall1") && RwpDirections.READ == rwpDirections) {
				return (RestApiServiceCall) element.getValue();
			}

			if (element.getName().getLocalPart().equals("restApiWriteServiceCall") && RwpDirections.WRITE == rwpDirections) {
				return (RestApiServiceCall) element.getValue();
			}

			if (element.getName().getLocalPart().equals("restApiWriteServiceCall1") && RwpDirections.WRITE == rwpDirections) {
				return (RestApiServiceCall) element.getValue();
			}
		}
		throw new GenDriverException(
				"No suitable service call found for " + rwpDirections.name() + " operation. Check the EI-XML of your device");
	}

	private RestApiDataPoint findProfileDataPoint(String profileName, String dataPointName) throws GenDriverException {
		
		Optional<RestApiFunctionalProfile> profile = findProfile(profileName);
		if (profile.isPresent()) {
			Optional<RestApiDataPoint> dataPoint = findDataPointForProfile(profile.get(), dataPointName);
			if (dataPoint.isPresent()) {
				return dataPoint.get();
			}
		}
		throw new GenDriverException(String.format("DataPoint profile=%s name=%s not found", profileName, dataPointName));
	}

	/**
	 * Finds a functional profile.
	 * @param profileName the functional profile name
	 * @return an optional {@link RestApiFunctionalProfile}
	 */
	protected Optional<RestApiFunctionalProfile> findProfile(String profileName) {
		return getRestApiInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream().filter(
				restApiProfileFrame -> restApiProfileFrame.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
				.findFirst();
	}

	/**
	 * Finds a data point.
	 * @param aProfile the functional profile
	 * @param aDataPointName the data point name
	 * @return an optional {@link RestApiFunctionalProfile}
	 */
	protected Optional<RestApiDataPoint> findDataPointForProfile(RestApiFunctionalProfile aProfile,
			String aDataPointName) {
		return aProfile.getDataPointList().getDataPointListElement().stream()
				.filter(dataPoint -> dataPoint.getDataPoint().getDataPointName().equals(aDataPointName))
				.findFirst();				
	}

	private RestApiInterface getRestApiInterface() {
		return deviceDescription.getInterfaceList().getRestApiInterface();
	}

	private RestApiInterfaceDescription getRestApiInterfaceDescription() {
		return getRestApiInterface().getRestApiInterfaceDescription();
	}

	private String getMappedDeviceValue(String genericValue, RestApiValueMapping valueMapping) {
		for (ValueMapping mapping: valueMapping.getMapping()) {
			if (genericValue.equals(mapping.getGenericValue())) {
				return mapping.getDeviceValue();
			}
		}

		return genericValue;
	}

	private String getMappedGenericValue(String deviceValue, RestApiValueMapping valueMapping) {
		for (ValueMapping mapping: valueMapping.getMapping()) {
			if (deviceValue.equals(mapping.getDeviceValue())) {
				return mapping.getGenericValue();
			}
		}

		return deviceValue;
	}
}
