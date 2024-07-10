/**
 *Copyright(c) 2022 Verein SmartGridready Switzerland
 * @generated NOT
 *
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

package communicator.modbus.helper;

import communicator.common.runtime.DataBits;
import communicator.common.runtime.GenDriverAPI4Modbus;
import communicator.common.runtime.GenDriverException;
import communicator.common.runtime.Parity;
import communicator.common.runtime.StopBits;

import java.nio.IntBuffer;

public class GenDriverAPI4ModbusRTUMock implements GenDriverAPI4Modbus {

	private static final int[] REGISTER_INT_VAL = new int[]{0x00000000, 0x00000005};

	private static final int[] REGISTER_FLOAT_VAL = new int[]{0x0000435c, 0x000051ec};

	private boolean returnInteger;
	private boolean isConnected = false;

	public void setIsIntegerType(boolean returnInteger) {
		this.returnInteger = returnInteger;
	}
	
	@Override
	public int[] ReadInputRegisters(int startingAddress, int quantity) {
		return prepareReturnValue(quantity);
	}

	@Override
	public int[] ReadHoldingRegisters(int startingAddress, int quantity) {
		return prepareReturnValue(quantity);
	}

	@Override
	public boolean initTrspService(String comPort) throws GenDriverException {
		return connect();
	}

	@Override
	public boolean initTrspService(String comPort, int baudRate) throws GenDriverException {
		return connect();
	}

	@Override
	public boolean initTrspService(String comPort, int baudRate, Parity parity) throws GenDriverException {
		return connect();
	}

	@Override
	public boolean initTrspService(String comPort, int baudRate, Parity parity, DataBits dataBits) throws GenDriverException {
		return connect();
	}

	@Override
	public boolean initTrspService(String comPort, int baudRate, Parity parity, DataBits dataBits, StopBits stopBits) throws GenDriverException {
		return connect();
	}

	@Override
	public void initDevice(String ipAddress, int port) throws GenDriverException {
		connect();
   	}

	@Override
	public void disconnect() {
		isConnected = false;
	}

	@Override
	public boolean[] ReadDiscreteInputs(int startingAddress, int quantity) {
		throw new UnsupportedOperationException("mocking not implemented yet");
	}

	@Override
	public boolean[] ReadCoils(int startingAddress, int quantity) {
		throw new UnsupportedOperationException("mocking not implemented yet");
	}

	@Override
	public void WriteMultipleCoils(int startingAdress, boolean[] values) {
		// implementation not required yet
	}
	

	@Override
	public void WriteSingleCoil(int startingAdress, boolean value) {
		// implementation not required yet
	}

	@Override
	public void WriteMultipleRegisters(int startingAdress, int[] values) {
		// implementation not required yet
	}

	@Override
	public void WriteSingleRegister(int startingAdress, int value) {
		// implementation not required yet
	}

	private boolean connect() throws GenDriverException {
		if (isConnected) {
			throw new GenDriverException("Do not connect twice");
		}

		isConnected = true;
		return isConnected;
	}

	private int[] prepareReturnValue(int quantity) {

		int[] registers = returnInteger ? REGISTER_INT_VAL:REGISTER_FLOAT_VAL;

		int[] result;
		if (quantity==1) {
			result = new int[1];
			System.arraycopy(registers, 1, result, 0, 1);
			return result;
		} else {
			IntBuffer buffer = IntBuffer.allocate(quantity);
			for (int i=0; i<quantity/2; i++) {
				buffer.put(registers);
			}
			return buffer.array();
		}
	}
}

