package com.smartgridready.communicator.rest.http.authentication;

import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.RestApiBasic;
import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.driver.api.http.GenHttpClientFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * Implements an HTTP authenticator for basic authentication.
 */
public class BasicAuthenticator implements Authenticator {

    BasicAuthenticator() {}

    @Override
    public String getAuthorizationHeaderValue(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) {

        StringBuilder headerValue = new StringBuilder("Basic ");

        Optional.ofNullable(deviceDescription.getInterfaceList().getRestApiInterface())
                .flatMap(restApiInterface -> Optional.ofNullable(restApiInterface.getRestApiInterfaceDescription()))
                .flatMap(restApiInterfaceDescription -> Optional.ofNullable(restApiInterfaceDescription.getRestApiBasic()))
                .ifPresent(restApiBasic -> headerValue.append(encodeUsernameAndPassword(restApiBasic)));

        return headerValue.toString();
    }

    @Override
    public boolean isTokenRenewalSupported() {
        return Authenticator.super.isTokenRenewalSupported();
    }

    @Override
    public void renewToken(DeviceFrame deviceDescription, GenHttpClientFactory httpClientFactory) throws IOException, RestApiServiceCallException, RestApiResponseParseException {
        Authenticator.super.renewToken(deviceDescription, httpClientFactory);
    }

    private String encodeUsernameAndPassword(RestApiBasic restApiBasic) {
        String plainLogin = restApiBasic.getRestBasicUsername() + ":" + restApiBasic.getRestBasicPassword();
        return String.valueOf(Base64.getEncoder().encodeToString(plainLogin.getBytes(StandardCharsets.UTF_8)));
    }
}
