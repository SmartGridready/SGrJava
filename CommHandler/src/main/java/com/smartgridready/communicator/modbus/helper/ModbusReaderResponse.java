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

import java.util.Arrays;

/**
 * Implements a Modbus read command response.
 */
public class ModbusReaderResponse {

    private int[] mbregresp = new int[256];
    private boolean[] mbbitresp = new boolean[64];
    private boolean bGotRegisters = false;
    private boolean bGotDiscrete = false;

    /** Constructs a new instance. */
    ModbusReaderResponse() {}

    /**
     * Gets all register values as array of int.
     * @return an array of int
     */
    public int[] getMbregresp() {
        return mbregresp;
    }

    /**
     * Gets some register values as array of int.
     * @param offset the start offset
     * @param len the number of registers
     * @return an array of int
     */
    public int[] getMbregresp(int offset, int len) {    
        len = Math.min(mbregresp.length - offset, len); // will cut if offset + len > than array size
        return Arrays.copyOfRange(mbregresp, offset, offset + len );
    }

    /**
     * Sets the register values.
     * @param mbregresp the register values to set
     */
    public void setMbregresp(int[] mbregresp) {
        this.mbregresp = mbregresp;
        this.bGotRegisters = true;
    }            

    /**
     * Gets all discrete inputs a boolean array.
     * @return an array of boolean
     */
    public boolean[] getMbbitresp() {
        return mbbitresp;
    }

    /**
     * Gets some discrete inputs a boolean array.
     * @param offset the start offset
     * @param len the number of values
     * @return an array of boolean
     */
    public boolean[] getMbbitresp(int offset, int len) {    	
        len = Math.min(mbregresp.length - offset, len); // will cut if offset + len > than array size    	
        return Arrays.copyOfRange(mbbitresp, offset, offset + len);
    }    

    /**
     * Sets the discrete inputs.
     * @param mbbitresp the boolean values to set
     */
    public void setMbbitresp(boolean[] mbbitresp) {
        this.mbbitresp = mbbitresp;
        this.bGotDiscrete = true;
    }

    /**
     * Tells if the reponse contains register values.
     * @return a boolean
     */
    public boolean isbGotRegisters() {
        return bGotRegisters;
    }

    /**
     * Tells if the reponse contains discrete inputs.
     * @return a boolean
     */
    public boolean isbGotDiscrete() {
        return bGotDiscrete;
    }

    @Override
    public String toString() {
        if (mbregresp != null) {
            return Arrays.toString(mbregresp);
        } else if (mbbitresp != null) {
            return Arrays.toString(mbbitresp);
        } else{
            return "Empty modbus response.";
        }
    }
}
