package com.smartgridready.communicator.common.api.dto;

/**
 * Defines the supported types of communication interfaces.
 */
public enum InterfaceType {
    /** Modbus */
    MODBUS,

    /** REST/HTTP */
    RESTAPI,

    /** Messaging */
    MESSAGING,

    /** Digital contacts */
    CONTACT,

    /** Generic */
    GENERIC,

    /** Unknown */
    UNKNOWN
}
