package com.smartgridready.driver.api.messaging.model.authentication;

/**
 * Represents a message broker authentication configuration using user name and password.
 */
@SuppressWarnings("unused")
public class MessageBrokerAuthenticationBasic {

    private final String username;
    private final String password;

    /**
     * Construct.
     * @param username the user name
     * @param password the password
     */
    public MessageBrokerAuthenticationBasic(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user name.
     * @return a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password
     * @return a string
     */
    public String getPassword() {
        return password;
    }
}
