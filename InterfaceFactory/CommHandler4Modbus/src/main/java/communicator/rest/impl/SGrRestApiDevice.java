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

package communicator.rest.impl;

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
import communicator.common.api.Float64Value;
import communicator.common.api.StringValue;
import communicator.common.api.Value;
import communicator.common.helper.JsonHelper;
import communicator.common.impl.SGrDeviceBase;
import communicator.common.runtime.GenDriverException;
import communicator.rest.api.GenDeviceApi4Rest;
import communicator.rest.exception.RestApiAuthenticationException;
import communicator.rest.exception.RestApiResponseParseException;
import communicator.rest.exception.RestApiServiceCallException;
import communicator.rest.http.authentication.Authenticator;
import communicator.rest.http.authentication.AuthenticatorFactory;
import communicator.rest.http.client.RestServiceClient;
import communicator.rest.http.client.RestServiceClientFactory;
import communicator.rest.http.client.RestServiceClientUtils;
import io.vavr.control.Either;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.DoubleBinaryOperator;

public class SGrRestApiDevice extends SGrDeviceBase<
		DeviceFrame,
		RestApiFunctionalProfile,
		RestApiDataPoint> implements GenDeviceApi4Rest {
	
	private static final Logger LOG = LoggerFactory.getLogger(SGrRestApiDevice.class);
	
	private final DeviceFrame deviceDescription;
	private final Authenticator httpAuthenticator;
	private final RestServiceClientFactory restServiceClientFactory;
	
	public SGrRestApiDevice(DeviceFrame deviceDescription, RestServiceClientFactory restServiceClientFactory) throws RestApiAuthenticationException {
		super(deviceDescription);
		this.deviceDescription = deviceDescription;
		this.restServiceClientFactory = restServiceClientFactory;

		RestApiAuthenticationMethod authMethod = getRestApiInterfaceDescription().getRestApiAuthenticationMethod();
		this.httpAuthenticator = AuthenticatorFactory.getAuthenticator(authMethod);
	}	
	
	public void authenticate() throws RestApiAuthenticationException, IOException, RestApiServiceCallException, RestApiResponseParseException {
		httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, restServiceClientFactory);
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
	public void setVal(String profileName, String dataPointName, communicator.common.api.Value value)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {

		RestApiDataPoint dataPoint = findProfileDataPoint(profileName, dataPointName);
		checkReadWritePermission(dataPoint, RwpDirections.WRITE);
		doReadWriteVal(dataPoint, value, null);
	}

	private Value doReadWriteVal(RestApiDataPoint dataPoint, Value value, Properties parameters)
			throws IOException, RestApiServiceCallException, RestApiResponseParseException, GenDriverException {
		
		String host = getRestApiInterfaceDescription().getRestApiUri();

		Optional<RestApiDataPointConfiguration> dpDescriptionOpt
				= Optional.ofNullable(dataPoint.getRestApiDataPointConfiguration());

		Properties substitutions = new Properties();
		if (dpDescriptionOpt.isPresent()) {

			if (value != null) {
				checkOutOfRange(new Value[]{value}, dataPoint);
				value = applyUnitConversion(dataPoint, value, this::divide);
				substitutions.put("value", value.getString());
			}
			if (parameters != null) {
				substitutions.putAll(parameters);
			}

			RestApiDataPointConfiguration dpDescription = dpDescriptionOpt.get();
			RestApiServiceCall serviceCall = dpDescription.getRestApiServiceCall();
			RestServiceClient restServiceClient = restServiceClientFactory.create(host, serviceCall, substitutions);
			String response = handleServiceCall(restServiceClient, httpAuthenticator.isTokenRenewalSupported());

			if (value == null) {
				value = handleServiceResponse(dpDescription, response);
				return applyUnitConversion(dataPoint, value, this::multiply);
			}

			return StringValue.of(response);
		}
		throw  new GenDriverException("Missing 'restAPIDataPoint' description in device description XML file");
	}	

	private String handleServiceCall(RestServiceClient serviceClient, boolean tryTokenRenewal) throws IOException, RestApiServiceCallException, RestApiResponseParseException {
		
		serviceClient.addHeader(HttpHeaders.AUTHORIZATION, httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, restServiceClientFactory));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Calling REST service: {} - {}", 
						serviceClient.getBaseUri(), 
						RestServiceClientUtils.printServiceCall(serviceClient.getRestServiceCall()));
		}
		
		Either<HttpResponse, String> result = serviceClient.callService();		
		if (result.isRight()) {
			LOG.debug("Received response: {}", result.get());
			return result.get();
		} else if (tryTokenRenewal && result.getLeft().getCode() == HttpStatus.SC_UNAUTHORIZED) {
			LOG.info("Authorisation error received. Trying with token renewal");
			httpAuthenticator.renewToken(deviceDescription, restServiceClientFactory);
			serviceClient.addHeader(HttpHeaders.AUTHORIZATION, httpAuthenticator.getAuthorizationHeaderValue(deviceDescription, restServiceClientFactory));
			// recurse into handleServiceCall, set tryTokenRenewal always to false here!
			return handleServiceCall(serviceClient, false); 
		} else {
			throw new RestApiServiceCallException(result.getLeft());
		}
	}

	private Value handleServiceResponse(RestApiDataPointConfiguration dpDescription, String response) throws GenDriverException, RestApiResponseParseException{

		Optional<ResponseQuery> queryOpt = Optional.ofNullable(dpDescription.getRestApiServiceCall())
				.map(RestApiServiceCall::getResponseQuery);

		if (queryOpt.isPresent()) {
			ResponseQuery responseQuery = queryOpt.get();
			if (responseQuery.isSetQueryType() && ResponseQueryType.JMES_PATH_EXPRESSION == responseQuery.getQueryType()) {
				return JsonHelper.parseJsonResponse(responseQuery.getQuery(), response);
			} else if (responseQuery.isSetQueryType() && ResponseQueryType.JMES_PATH_MAPPING == responseQuery.getQueryType()) {
				return JsonHelper.mapJsonResponse(responseQuery.getJmesPathMappings(), response);
			} else if (responseQuery.isSetQueryType()) {
			}
		}
		// return plain response
		return StringValue.of(response);
	}

	private RestApiDataPoint findProfileDataPoint(String profileName, String dataPointName) throws GenDriverException {
		
		Optional<RestApiFunctionalProfile> profile = findProfile(profileName);
		if (profile.isPresent()) {
			Optional<RestApiDataPoint> dataPoint = findDataPointForProfile(profile.get(), dataPointName);
			if (dataPoint.isPresent()) {
				return dataPoint.get();
			}
		}
		throw new GenDriverException(String.format("Datapoint profile=%s name=%s not found", profileName, dataPointName));
	}
	
	protected Optional<RestApiFunctionalProfile> findProfile(String profileName) {
		return getRestApiInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream().filter(
				restApiProfileFrame -> restApiProfileFrame.getFunctionalProfile().getFunctionalProfileName().equals(profileName))
				.findFirst();
	}

	protected Optional<RestApiDataPoint> findDataPointForProfile(RestApiFunctionalProfile aProfile,
			String aDataPointName) {
		return aProfile.getDataPointList().getDataPointListElement().stream()
				.filter(datapoint -> datapoint.getDataPoint().getDataPointName().equals(aDataPointName))
				.findFirst();				
	}

	private RestApiInterface getRestApiInterface() {
		return deviceDescription.getInterfaceList().getRestApiInterface();
	}

	private RestApiInterfaceDescription getRestApiInterfaceDescription() {
		return getRestApiInterface().getRestApiInterfaceDescription();
	}

	private Value applyUnitConversion(RestApiDataPoint dataPoint, Value value, DoubleBinaryOperator conversionFunction) {

		if (dataPoint.getDataPoint().isSetUnitConversionMultiplicator()
				&& isNumeric(value)
				&& dataPoint.getDataPoint().getUnitConversionMultiplicator() != 0.0) {
			return Float64Value.of(conversionFunction.applyAsDouble(value.getFloat64(), dataPoint.getDataPoint().getUnitConversionMultiplicator()));
		}
		return value;
	}

	private boolean isNumeric(Value value) {
		if (value == null) {
			return false;
		}

		try {
			value.getFloat64();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private double divide(double dividend, double divisor) {
		return  dividend / divisor;
	}

	private double multiply(double factor1, double factor2) {
		return  factor1 * factor2;
	}
}
