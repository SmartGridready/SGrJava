package communicator.helper;
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

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import de.re.easymodbus.exceptions.ModbusException;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

public interface GenDriverAPI4Modbus {
	
	public default void setUnitIdentifier( short ident ) {};
	
	public default boolean initTrspService(String sCOM) {return false; };
	
    public int[] ReadInputRegisters(int startingAddress, int quantity) throws de.re.easymodbus.exceptions.ModbusException,    
    UnknownHostException, SocketException, IOException, SerialPortException, SerialPortTimeoutException;        
    
    public int[] ReadHoldingRegisters(int startingAddress, int quantity) throws de.re.easymodbus.exceptions.ModbusException,
    UnknownHostException, SocketException, IOException, SerialPortException, SerialPortTimeoutException;
    
    
    public boolean[] ReadDiscreteInputs(int startingAddress, int quantity) throws de.re.easymodbus.exceptions.ModbusException,
    UnknownHostException, SocketException, IOException, SerialPortException, SerialPortTimeoutException;
 

    public boolean[] ReadCoils(int startingAddress, int quantity) throws de.re.easymodbus.exceptions.ModbusException,
    UnknownHostException, SocketException, IOException, SerialPortException, SerialPortTimeoutException;
       
    public void  WriteMultipleCoils(int startingAdress, boolean[] values) throws UnknownHostException, SocketException, ModbusException, IOException, SerialPortException, SerialPortTimeoutException;
    
    public void  WriteSingleCoil(int startingAdress, boolean value) throws UnknownHostException, SocketException, ModbusException, IOException, SerialPortException, SerialPortTimeoutException;
     
    public void  WriteMultipleRegisters(int startingAdress, int[] values) throws UnknownHostException, SocketException, ModbusException, IOException, SerialPortException, SerialPortTimeoutException; 
     
    public void WriteSingleRegister(int startingAdress, int value) throws UnknownHostException, SocketException, ModbusException, IOException, SerialPortException, SerialPortTimeoutException;

    public void disconnect() throws UnknownHostException, IOException, SerialPortException, SerialPortTimeoutException;
}