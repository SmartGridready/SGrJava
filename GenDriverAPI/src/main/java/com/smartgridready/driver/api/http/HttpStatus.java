package com.smartgridready.driver.api.http;

/**
 * Defines HTTP status code constants.
 */
public class HttpStatus {
    
    /** 200 OK. */
    public static final int OK = 200;
    /** 400 Bad Request. */
    public static final int BAD_REQUEST = 400;
    /** 401 Unauthorized. */
    public static final int UNAUTHORIZED = 401;
    /** 403 Forbidden. FIXME status code */
    public static final int FORBIDDEN = 403;
    /** 500 Internal Server Error. */
    public static final int INTERNAL_SERVER_ERROR = 500;

    private HttpStatus() {}
}
