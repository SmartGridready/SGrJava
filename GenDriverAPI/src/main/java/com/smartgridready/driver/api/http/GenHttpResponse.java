package com.smartgridready.driver.api.http;

/**
 * Implements a HTTP response.
 */
public class GenHttpResponse {

    final private String response;
    final private int responseCode;
    final private String reason;

    /**
     * Construct.
     * @param response the response body
     * @param responseCode the HTTP status code
     * @param reason an optional status message
     */
    private GenHttpResponse(String response, int responseCode, String reason) {
        this.response = response;
        this.responseCode = responseCode;
        this.reason = reason;
    }

    /**
     * Gets the response body.
     * @return the response body as string
     */
    public String getResponse() {
        return response;
    }

    /**
     * Gets the HTTP status code.
     * @return the numeric status code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Gets the status message.
     * @return a string
     */
    public String getReason() {
        return reason;
    }

    /**
     * Checks if the response is successful,
     * depending on the status code.
     * @return true if successful, false otherwise
     */
    public boolean isOk() {
        return (responseCode >= HttpStatus.OK) && (responseCode < HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates a new instance.
     * @param response the response body
     * @return a new instance of {@code GenHttpResponse}
     */
    public static GenHttpResponse of(String response) {
        return new GenHttpResponse(response, HttpStatus.OK, "");
    }

    /**
     * Creates a new instance.
     * @param response the response body
     * @param responseCode the HTTP status code
     * @param reason an optional status message
     * @return a new instance of {@code GenHttpResponse}
     */
    public static GenHttpResponse of(String response, int responseCode, String reason) {
        return new GenHttpResponse(response, responseCode, reason);
    }
}
