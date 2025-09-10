package com.smartgridready.driver.api.modbus;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines Modbus exception types.
 */
public enum ModbusExceptionType {

    /** Placeholder for OK. */
    NO_ERROR,
    /** Illegal Function (01). */
    ILLEGAL_FUNCTION,
    /** Illegal Data Address (02). */
    ILLEGAL_DATA_ADDRESS,
    /** Illegal Data Value (03). */
    ILLEGAL_DATA_VALUE,
    /** Slave Device Failure (04). */
    SLAVE_DEVICE_FAILURE,
    /** Acknowledge (05). */
    ACKNOWLEDGE,
    /** Slave Device Busy (06). */
    SLAVE_DEVICE_BUSY,
    /** Memory Parity Error (08). */
    MEMORY_PARITY_ERROR,
    /** Gateway Path Unavailable (0A). */
    GATEWAY_PATH_UNAVAILABLE,
    /** Gateway Target Device Failed to Respond (0B). */
    GATEWAY_TARGET_ERROR,
    /** All other non-standard exceptions. */
    UNDEFINED_ERROR;

    private static final Map<Integer, ModbusExceptionType> CODE_MAP = new HashMap<>();
    static {
        CODE_MAP.put(0x00, NO_ERROR);
        CODE_MAP.put(0x01, ILLEGAL_FUNCTION);
        CODE_MAP.put(0x02, ILLEGAL_DATA_ADDRESS);
        CODE_MAP.put(0x03, ILLEGAL_DATA_VALUE);
        CODE_MAP.put(0x04, SLAVE_DEVICE_FAILURE);
        CODE_MAP.put(0x05, ACKNOWLEDGE);
        CODE_MAP.put(0x06, SLAVE_DEVICE_BUSY);
        CODE_MAP.put(0x08, MEMORY_PARITY_ERROR);
        CODE_MAP.put(0x0A, GATEWAY_PATH_UNAVAILABLE);
        CODE_MAP.put(0x0B, GATEWAY_TARGET_ERROR);
    }

    /**
     * Gets the appropriate exception type depending on the code.
     * @param code the exception code
     * @return an instance of {@link ModbusExceptionType}
     */
    public static ModbusExceptionType fromCode(int code) {
        return CODE_MAP.getOrDefault(code, UNDEFINED_ERROR);
    }
}
