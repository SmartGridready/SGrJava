package communicator.http.authentication;

import java.io.IOException;

import org.apache.hc.client5.http.fluent.Request;

import com.smartgridready.ns.v0.SGrRestAPIDeviceFrame;

public interface Authenticator {

	Request provideAuthenicationHeader (SGrRestAPIDeviceFrame deviceDescription, Request requestBuilder) throws IOException;
}
