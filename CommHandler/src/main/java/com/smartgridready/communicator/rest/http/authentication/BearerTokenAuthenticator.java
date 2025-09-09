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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartgridready.driver.api.http.GenHttpResponse;
import com.smartgridready.communicator.rest.http.client.RestServiceClient;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.ResponseQuery;
import com.smartgridready.ns.v0.ResponseQueryType;
import com.smartgridready.ns.v0.RestApiInterfaceDescription;
import com.smartgridready.ns.v0.RestApiServiceCall;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.driver.api.http.GenHttpClientFactory;
import com.smartgridready.communicator.rest.http.client.RestServiceClientUtils;
import io.burt.jmespath.Expression;
import io.burt.jmespath.JmesPath;
import io.burt.jmespath.jackson.JacksonRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Implements an HTTP authenticator using JWT bearer tokens.
 */
public class BearerTokenAuthenticator implements Authenticator {
    
    private static final Logger LOG = LoggerFactory.getLogger(BearerTokenAuthenticator.class);

    private String bearerToken;

    BearerTokenAuthenticator() {}
    
    @Override
    public String getAuthorizationHeaderValue(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory)
            throws IOException, RestApiServiceCallException{
        
        if (bearerToken == null) {
            authenticate(deviceDescription, httpClientFactory);
        }
        return "Bearer " + bearerToken;
    }    
    
    @Override
    public boolean isTokenRenewalSupported() {
        return true;
    }
    
    @Override
    public void renewToken(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws IOException, RestApiServiceCallException {
        bearerToken = null;
        authenticate(deviceDescription, httpClientFactory);
    }
    
    private void authenticate(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws IOException, RestApiServiceCallException {
        
        RestApiInterfaceDescription ifDescription =
                deviceDescription.getInterfaceList().getRestApiInterface().getRestApiInterfaceDescription();

        String host = ifDescription.getRestApiUri();
        boolean verifyCertificate = (ifDescription.getRestApiVerifyCertificate() != null) ? Boolean.valueOf(ifDescription.getRestApiVerifyCertificate()) : true;

        RestServiceClient restServiceClient = RestServiceClient.of(host, verifyCertificate, ifDescription.getRestApiBearer().getRestApiServiceCall(), httpClientFactory);

        requestBearerToken(restServiceClient);
    }
    
    private void requestBearerToken(RestServiceClient restServiceClient) throws IOException, RestApiServiceCallException {
        
        if (LOG.isInfoEnabled()) {
                LOG.debug("Calling REST service: {} - {}", 
                            restServiceClient.getBaseUri(),
                            RestServiceClientUtils.printServiceCall(restServiceClient.getRestServiceCall()));
        }
        
        GenHttpResponse result = restServiceClient.callService();
        if (result!=null && result.isOk()) {
            LOG.info("Received response: {}", result.getResponse());
            bearerToken = handleResponse(result.getResponse(), restServiceClient.getRestServiceCall());
            if (bearerToken != null) {
                LOG.debug("Received bearer token={}", bearerToken);
            } else {
                LOG.error("Bearer token missing in response.");
            }
            
        } else {
            LOG.error("Authenticate failed with http status={}", result != null ? result.getResponseCode() : -1);
            throw new RestApiServiceCallException(
                    result != null ? result : GenHttpResponse.of("", -1, "No response received"));
        }                
    }

    private String handleResponse(String result, RestApiServiceCall restApiServiceCall) throws IOException {

        Optional<String> queryOpt = Optional.ofNullable(restApiServiceCall.getResponseQuery())
                .filter(responseQuery -> responseQuery.getQueryType() != null
                        && responseQuery.getQueryType() == ResponseQueryType.JMES_PATH_EXPRESSION)
                .map(ResponseQuery::getQuery);

        if (queryOpt.isPresent()) {
            return parseResponse(result, queryOpt.get());
        }
        return result;
    }
    
    private String parseResponse(String jsonResp, String jmesPath) throws IOException {
        
        JmesPath<JsonNode> path = new JacksonRuntime();
        Expression<JsonNode> expression = path.compile(jmesPath);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResp);        
        JsonNode res = expression.search(jsonNode);
        
        if (res != null) {
            return res.asText();
        }
        
        LOG.error("Unable to extract bearer token from http response.");
        return null;
    }
}
