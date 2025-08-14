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

package com.smartgridready.communicator.modbus.helper;

import com.smartgridready.ns.v0.RegisterType;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;

/**
 * Implements Modbus read commands.
 */
public class ModbusReader {

    /**
     * Reads from Modbus transport.
     * @param drv4Modbus the Modbus driver instance
     * @param unitIdentifier the slave identifier
     * @param regType the type of Modbus register
     * @param regAddr the starting address
     * @param isFirstRegAddrOne address offset of one when true
     * @param length the number of elements to read
     * @return an instance of {@link ModbusReaderResponse}
     * @throws GenDriverException when any other error occurred
     * @throws GenDriverModbusException when a protocol error occurred
     * @throws GenDriverSocketException when a network error occurred
     */
    public static ModbusReaderResponse read(GenDriverAPI4Modbus drv4Modbus,
                                            short unitIdentifier,
                                            RegisterType regType,
                                            int regAddr,
                                            boolean isFirstRegAddrOne,
                                            int length)
            throws GenDriverException, GenDriverModbusException, GenDriverSocketException {

        ModbusReaderResponse response = new ModbusReaderResponse();

        if (isFirstRegAddrOne) {
            regAddr = regAddr - 1;
        }
        if (RegisterType.HOLD_REGISTER == regType) {
            response.setMbregresp(drv4Modbus.readHoldingRegisters(unitIdentifier, regAddr, length));
        } else if (RegisterType.INPUT_REGISTER == regType) {
            response.setMbregresp(drv4Modbus.readInputRegisters(unitIdentifier, regAddr, length));
        } else if (RegisterType.DISCRETE_INPUT == regType) {
            response.setMbbitresp(drv4Modbus.readDiscreteInputs(unitIdentifier, regAddr, length));
        } else if (RegisterType.COIL == regType) {
            response.setMbbitresp(drv4Modbus.readCoils(unitIdentifier, regAddr, length));
        } else {
            throw new GenDriverException("ModbusReader, unhandled register type requested.");
        }

        return response;
    }
}
