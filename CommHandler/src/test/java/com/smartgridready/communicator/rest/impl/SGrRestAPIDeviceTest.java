package com.smartgridready.communicator.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.driver.api.http.GenHttpRequest;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.driver.api.http.GenUriBuilder;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.ResponseQuery;
import com.smartgridready.ns.v0.RestApiServiceCall;
import com.smartgridready.communicator.common.api.values.Float32Value;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Int32UValue;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.communicator.rest.api.GenDeviceApi4Rest;
import com.smartgridready.driver.api.http.GenHttpClientFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class SGrRestAPIDeviceTest {

	private static final Logger LOG = LoggerFactory.getLogger(SGrRestAPIDeviceTest.class);

	@Mock
	GenHttpClientFactory httpClientFactory;

	@Mock
	GenHttpRequest httpRequest;

	@Mock
	GenHttpResponse httpResponse;

	@Mock
	GenUriBuilder uriBuilder;
	
	@Mock
	RestApiServiceCall restServiceCall;

	ArgumentCaptor<String> headerNameCaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> paramNameCaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> paramValueCaptor = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> httpBodyCaptor = ArgumentCaptor.forClass(String.class);
	
	static DeviceFrame deviceFrame;
			
	private static final String CLEMAP_AUTH_RESP = "{\r\n"
			+ "    \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6ImFjY2VzcyJ9.eyJ1c2VySWQiOiI2MzM0MzI5MWVjZjJjZjAxM2ExZTVhOWQiLCJpYXQiOjE2NjgwOTI1NzMsImV4cCI6MTY2ODE3ODk3MywiYXVkIjoiaHR0cHM6Ly95b3VyZG9tYWluLmNvbSIsImlzcyI6ImZlYXRoZXJzIiwic3ViIjoiNjMzNDMyOTFlY2YyY2YwMTNhMWU1YTlkIiwianRpIjoiNTU2NmU2M2QtNTVmOC00MDEyLWJlYTUtOTJjYWE0NDIwYzQ3In0.xVOrspfi1E61Jb0BXBt37wgqGcnkPueMcHh1_zI-xXM\",\r\n"
			+ "    \"user\": {\r\n"
			+ "        \"_id\": \"63343291ecf2cf013a1e5a9d\",\r\n"
			+ "        \"email\": \"hfurrer@ergonomics.ch\",\r\n"
			+ "        \"groups\": [\r\n"
			+ "            \"owner_smartgrid\",\r\n"
			+ "            \"owner_smartfactory\"\r\n"
			+ "        ],\r\n"
			+ "        \"roles\": [\r\n"
			+ "            \"default\"\r\n"
			+ "        ]\r\n"
			+ "    }\r\n"
			+ "}";
	
	private static final String CLEMAP_METER_RESP = "[{\"sensor_id\":\"63343431ecf2cf013a1e5a9f\",\"opm\":{\"value\":1,\"last_updt\":\"2022-11-07T15:19:54.560Z\"},\"ten_sec\":{\"p_l1\":1.5,\"p_l2\":2,\"p_l3\":4.0,\"q_l1\":2.3,\"q_l2\":0,\"q_l3\":4,\"last_upd\":\"2022-11-07T15:21:50.000Z\"},\"one_min\":{\"p_l1\":0.003,\"p_l2\":0,\"p_l3\":0,\"q_l1\":-0.002,\"q_l2\":0,\"q_l3\":0,\"avg_energy_l1\":0,\"avg_energy_l2\":0,\"avg_energy_l3\":0,\"v_l1\":227.869,\"v_l2\":0,\"v_l3\":0,\"i_l1\":0.001,\"i_l2\":0,\"i_l3\":0,\"s_l1\":0.173,\"s_l2\":0,\"s_l3\":0,\"pf_l1\":0.085,\"pf_l2\":1,\"pf_l3\":1,\"last_update\":\"2022-11-07T15:19:47.579Z\"}}]";

	private static final URI TEST_URI = URI.create("");
	
	@BeforeAll
	static void initDeviceFrame() {
		deviceFrame = createSGrRestAPIDeviceFrame();
	}
	
	
	@Test
	void testGetValSuccessWithBearerAuthentication() throws Exception {

		// given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP),
			GenHttpResponse.of(CLEMAP_METER_RESP)
		);

		// when
		GenDeviceApi4Rest device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();
		Value res = device.getVal("ActivePowerAC", "ActivePowerACtot");
		
		// then		
		assertEquals("0.0075", String.format("%.4f",res.getFloat32()));
	}

	@Test
	void testGetValSuccessWithTokenRenewal() throws Exception {
		
		// given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(
				GenHttpResponse.of(CLEMAP_AUTH_RESP),	// authentication response on connect()
				GenHttpResponse.of("", 401, "Needs token renewal."), // webservice coll to query datapoint
				GenHttpResponse.of(CLEMAP_METER_RESP)); // response of read datapoint result
		
		// when
		SGrRestApiDevice device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();
		Value res = device.getVal("ActivePowerAC", "ActivePowerACtot");
		
		// then		
		assertEquals("0.0075", String.format("%.4f", res.getFloat32()));
		
	}

	@Test
	void testGetValSuccessWithParameters() throws Exception {

		// given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(URI.create("https://clemap.io/authentication"));
		when(httpRequest.execute()).thenReturn(
				GenHttpResponse.of(CLEMAP_AUTH_RESP),
				GenHttpResponse.of("{ \"value\" : \"on\" }"));

		// when
		GenDeviceApi4Rest device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();

		when(uriBuilder.build()).thenReturn(
			URI.create("https://clemap.io/authentication"),
			URI.create("https://clemap.io/digitaltwins?sensor_id=1234&pin=2")
		);

		var parameters = new Properties();
		parameters.put("sensor_id", "1234");
		var res = device.getVal("GPIO", "ContactRW", parameters);
		assertEquals("on", res.getString());

		when(uriBuilder.build()).thenReturn(
			URI.create("https://clemap.io/authentication"),
			URI.create("https://clemap.io/digitaltwins?sensor_id=5678&pin=2")
		);

		// modify the parameters
		parameters.put("sensor_id", "5678");
		res = device.getVal("GPIO", "ContactRW", parameters);
		assertEquals("on", res.getString());
	}

	@ParameterizedTest
	@ValueSource(strings = {"ContactR","ContactRW"})
	void testGetValSuccessWithReadServiceCallV2(String dataPointName) throws Exception {

		// given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP),
			GenHttpResponse.of("{ \"value\" : \"on\" }")
		);

		// when
		GenDeviceApi4Rest device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();
		Value res = device.getVal("GPIO", dataPointName);

		// then
		assertEquals("on", res.getString());
	}

	@ParameterizedTest
	@ValueSource(strings = {"ContactW", "ContactRW", "IORegister"})
	void testSetValWithWriteServiceCallV2(String dataPointName) throws Exception {

		// given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
		Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
		when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP),
			GenHttpResponse.of("")
		);

		// when
		SGrRestApiDevice device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();

		assertDoesNotThrow(() -> device.setVal("GPIO", dataPointName, StringValue.of("on")));

		verify(httpRequest, times(5)).addHeader(headerNameCaptor.capture(), headerValueCaptor.capture());
		var headerKeys = headerNameCaptor.getAllValues();
		assertEquals("Accept", headerKeys.get(0));
		assertEquals("Content-Type", headerKeys.get(1));
		assertEquals("Accept", headerKeys.get(2));
		assertEquals("Content-Type", headerKeys.get(3));
		assertEquals("Authorization", headerKeys.get(4));

		var headerValues = headerValueCaptor.getAllValues();
		assertEquals("application/json", headerValues.get(0));
		assertEquals("application/json", headerValues.get(1));
		assertEquals("application/json", headerValues.get(2));
		switch (dataPointName) {
			case "ContactW":
			case "ContactRW":
				assertEquals("application/json", headerValues.get(3));
				break;
			case "IORegister":
				assertEquals("application/x-www-form-urlencoded", headerValues.get(3));
				break;
			default:
				fail("Unhandled dataPoint: " + dataPointName);
		}
		assertTrue(headerValues.get(4).startsWith("Bearer "));

		switch (dataPointName) {
			case "ContactW":
			case "ContactRW":
				verify(httpRequest, times(2)).setBody(httpBodyCaptor.capture());
				break;
			case "IORegister":
				verify(httpRequest, times(1)).addFormParam(paramNameCaptor.capture(), paramValueCaptor.capture());
				break;
			default:
				fail("Unhandled dataPoint: " + dataPointName);
		}
		
		// verify request body
		String expectedBody = null;
		switch (dataPointName) {
			case "ContactW":
				expectedBody = "{ \"pin\" : 1, \"value\" : \"on\" }"; // Json body
				break;
			case "ContactRW":
				expectedBody = "{ \"pin\" : 2, \"value\" : \"on\" }"; // Json body
				break;
			case "IORegister":
				// form parameters, not body
				break;
			default:
				fail("Unhandled dataPoint: " + dataPointName);
		}
		if (expectedBody != null) {
			assertEquals(expectedBody, httpBodyCaptor.getValue());
		}

		// verify form parameters
		switch (dataPointName) {
			case "ContactW":
			case "ContactRW":
				// body, not form parameters
				break;
			case "IORegister":
				var paramKeys = paramNameCaptor.getAllValues();
				assertEquals("pins=1..7", paramKeys.get(0));
				var paramValues = paramValueCaptor.getAllValues();
				assertEquals("124", paramValues.get(0));
				break;
			default:
				fail("Unhandled dataPoint: " + dataPointName);
		}
	}

	@Test
	void testSetVal() throws Exception {
		
		//given
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP), // auth-response on connect.
			GenHttpResponse.of(CLEMAP_AUTH_RESP), // auth-response when trying to get cached token, however token is expired
			GenHttpResponse.of("OK")
		);
		
		// when
		SGrRestApiDevice device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();

		assertDoesNotThrow(() -> device.setVal("ActivePowerAC", "ActivePowerACtot", Int32UValue.of(100)));
	}


	@ParameterizedTest
	@CsvSource({
			"0.004, Values [0.004] out of range. MIN value=0.005",
			"101, Values [101] out of range. MAX value=100.0"})
	void testSetValOutOfRange(String value, String expectedResponse) throws Exception {

		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		when(httpRequest.execute()).thenReturn(GenHttpResponse.of(CLEMAP_AUTH_RESP));

		SGrRestApiDevice device = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		device.connect();

		Exception exception = assertThrows(GenDriverException.class, () -> device.setVal("ActivePowerAC", "ActivePowerACtot", StringValue.of(value)));
		assertEquals(expectedResponse, exception.getMessage());
	}


	private static Stream<Arguments> rwPermissionChecks() {

		// VoltageL1 = R, VoltageL2 = W, VoltageL3 = RW
		return Stream.of(
				Arguments.of("write on RW DP", "ActivePowerACL1", true,  null),
				Arguments.of("write on R  DP", "ActivePowerACL2", true,  "Operation WRITE not allowed on datapoint ActivePowerACL2"),
				Arguments.of("write on W  DP", "ActivePowerACL3", true,  null),
				Arguments.of("read  on RW DP", "ActivePowerACL1", false, null),
				Arguments.of("read  on R  DP", "ActivePowerACL2", false, null),
				Arguments.of("read  on W  DP", "ActivePowerACL3", false, "Operation READ not allowed on datapoint ActivePowerACL3")
		);
	}

	@ParameterizedTest
	@MethodSource("rwPermissionChecks")
	void writePermissionCheckRest(String testName, String dataPointName, boolean isWrite, String expectedErrorMsg) throws Exception {

		LOG.info("Testing writePermissionCheckRest: {}", testName);

		Mockito.lenient().when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		Mockito.lenient().when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.build()).thenReturn(TEST_URI);

		SGrRestApiDevice restApiDevice = new SGrRestApiDevice(deviceFrame, httpClientFactory);

		Mockito.lenient().when(httpRequest.execute()).thenReturn(GenHttpResponse.of("{}"));

		ResponseQuery query = new ResponseQuery();
		query.setQuery("token");
		Mockito.lenient().when(restServiceCall.getResponseQuery()).thenReturn(query);

		if (expectedErrorMsg == null) {
			if(isWrite) {
				assertDoesNotThrow(() -> restApiDevice.setVal("ActivePowerAC", dataPointName, Float32Value.of(380)));
			} else {
				assertDoesNotThrow(() -> restApiDevice.getVal("ActivePowerAC", dataPointName));
			}
		} else {
			GenDriverException e = isWrite ?
					assertThrows(GenDriverException.class, () -> restApiDevice.setVal("ActivePowerAC", dataPointName, Float64Value.of(380)))
					: assertThrows(GenDriverException.class, () -> restApiDevice.getVal("ActivePowerAC", dataPointName));
			assertEquals(expectedErrorMsg, e.getMessage());
		}
	}

	@Test
	void unitConversionWrite() throws Exception {

		// Write conversion
		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		Mockito.lenient().when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP),
			GenHttpResponse.of(CLEMAP_METER_RESP)
		);

		SGrRestApiDevice restApiDevice = new SGrRestApiDevice(deviceFrame, httpClientFactory);

		restApiDevice.setVal("ActivePowerAC", "ActivePowerACtot", Float32Value.of(0.099f));
		verify(httpRequest, times(2)).setBody(httpBodyCaptor.capture());

		var objectMapper = new ObjectMapper();
		var jsonBody = objectMapper.readTree(httpBodyCaptor.getValue());
		var convertedValue = Math.round(jsonBody.get("value").asDouble());
		assertEquals(99, convertedValue );

	}

	@Test
	void unitConversionRead() throws Exception {

		when(httpClientFactory.createHttpRequest(anyBoolean())).thenReturn(httpRequest);
		when(httpClientFactory.createUriBuilder(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addPath(any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.addQueryParameter(any(), any())).thenReturn(uriBuilder);
        Mockito.lenient().when(uriBuilder.setQueryString(any())).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(TEST_URI);
		Mockito.lenient().when(httpRequest.execute()).thenReturn(
			GenHttpResponse.of(CLEMAP_AUTH_RESP),
			GenHttpResponse.of(CLEMAP_METER_RESP)
		);

		SGrRestApiDevice restApiDevice = new SGrRestApiDevice(deviceFrame, httpClientFactory);
		var result = restApiDevice.getVal("ActivePowerAC", "ActivePowerACtot");

		assertEquals("0.0075", String.format("%.4f", result.getFloat32()));
	}


	private static DeviceFrame createSGrRestAPIDeviceFrame() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL devDescUrl = classloader.getResource("SGr_04_0018_CLEMAP_EIcloudEnergyMonitorV0.2.1.xml");

		var properties = new Properties();
		properties.put("baseUri", "https://clemap.io");

		DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
		return loader.load("", Optional.ofNullable(devDescUrl).map(URL::getPath).orElse(""), properties);
	}
}
