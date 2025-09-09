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
package com.smartgridready.communicator.modbus.api;
import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.values.NumberValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;

/**
 * The API defines read and write operations for SmartGridReady devices.
 * The values can be provided as {@link String} or {@link NumberValue}
 * The implementing device will convert the data to their Modbus format.
 */
public interface GenDeviceApi4Modbus extends GenDeviceApi {

    /**
     * Read a value from the modbus device in its generic data point representation (see {@link Value}).
     *
     * @param profileName The name of the functional profile.
     * @param dataPointName The name of the data point.
     * @return The value converted to {@link java.lang.String}
     * @throws GenDriverException If a general exception occurred.
     * @throws GenDriverSocketException In case of communication errors with the modbus device.
     * @throws GenDriverModbusException If the modbus command could not be interpreted/executed on the device.
     *
     * @see Value
     */
    Value getVal(String profileName, String dataPointName)
            throws GenDriverException, GenDriverSocketException, GenDriverModbusException;

    /**
     * Write a value to the modbus device using the generic data point representation (see {@link Value}).
     * The value is converted from the format given within the {@code value} parameter to the value required
     * by the modbus device (as defined within the external interface XML of the device).
     *
     * @param profileName The name of the functional profile.
     * @param dataPointName The name of the data point.
     * @param value The value as {@link Value}
     * @throws GenDriverException If a general exception occurred.
     * @throws GenDriverSocketException In case of communication errors with the modbus device.
     * @throws GenDriverModbusException If the modbus command could not be interpreted/executed on the device.
     *
     * @see Value
     */
    void setVal(String profileName, String dataPointName, Value value)
            throws GenDriverException, GenDriverSocketException, GenDriverModbusException;
}
