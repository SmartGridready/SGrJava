package com.smartgridready.communicator.modbus.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConversionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ConversionHelper.class);

    private ConversionHelper() {
        // helper class
    }

    public static int[] bytesToRegisters(byte[] bytes) {
        ByteBuffer bbuf = ByteBuffer.wrap(bytes);
        IntBuffer ibuf =  IntBuffer.allocate(bytes.length/2);
        for( int i=0; i<ibuf.capacity(); i++) {
            ibuf.put(bbuf.getShort() & 0x0000ffff);
        }
        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("Convert bytes to register int[]: ");
            for(int val : ibuf.array()) {
                sb.append(String.format("%04x ", val));
            }
            LOG.debug(sb.toString());
        }
        return ibuf.array();
    }

    public static ByteBuffer byteBufFromRegisters(int[] intArr) {
        ByteBuffer bbuf = ByteBuffer.allocate(intArr.length*2);
        for (int j : intArr) {
            bbuf.putShort((short) (j & 0x0000ffff));
        }
        bbuf.rewind(); // to get values from...
        return bbuf;
    }

    public static int[] floatToRegisters(float fVal) {
        int[] res = new int[2];
        ByteBuffer bbuf = ByteBuffer.allocate(4);
        bbuf.putFloat(fVal);
        bbuf.rewind();
        res[0] = bbuf.getShort() & 0x0000ffff;
        res[1] = bbuf.getShort() & 0x0000ffff;
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert floatToRegisters double: %f, hex: %04x %04x", fVal, res[0], res[1]));
        }
        return res;
    }

    public static int[] doubleToRegisters(double dVal) {

        int[] res = new int[4];
        ByteBuffer bbuf = ByteBuffer.allocate(8);
        bbuf.putDouble(dVal);
        bbuf.rewind();
        for(int i=0; i < res.length; i++) {
            res[i] = (bbuf.getShort() & 0x0000ffff);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert doubleToRegisters double: %f, hex: %04x %04x %04x %04x", dVal, res[0], res[1], res[2], res[3]));
        }
        return res;
    }

    public static int[] longToRegisters(long lVal) {
        int[] res = new int[4];

        res[0] = ((short) (lVal >> 48) & 0xffff);
        res[1] = ((short) (lVal >> 32) & 0xffff);
        res[2] = ((short) (lVal >> 16) & 0xffff);
        res[3] = ((short) (lVal) & 0xffff);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert longToRegisters long: %d, hex: %04x %04x %04x %04x", lVal, res[0], res[1], res[2], res[3]));
        }
        return res;
    }

    public static int[] unsignedLongToRegister(BigInteger value) {
        byte[] res = new byte[8];
        byte[] inBytes = value.toByteArray();
        // write array from behind
        for (int i=res.length-1, j=inBytes.length-1; i>=0 && j>=0; i--, j--) {
            res[i] = inBytes[j];
        }
        return bytesToRegisters(res);
    }


    public static int[] intToRegisters(int value) {
        int[] res = new int[2];
        res[0] = ((short) (value >> 16) & 0x0000ffff);
        res[1] = ((short) value & 0x0000ffff);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert intToRegisters int: %d, hex: %04x %04x", value, res[0], res[1]));
        }
        return res;
    }

    public static int[] uintToRegisters(long value) {
        int[] res = intToRegisters((int)value);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert uintToRegisters uint: %d, hex: %04x %04x", value, res[0], res[1]));
        }
        return res;
    }

    public static int[] shortToRegister(short value) {
        int resVal = value & 0x0000ffff;
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Convert shortToRegisters short: %d, hex: %04x", value, resVal));
        }
        return new int[]{resVal};
    }

    /**
     * Converts 16 - Bit Register values to String
     *
     * @param registers    Register array received via Modbus
     * @param offset       First Register containing the String to convert
     * @param stringLength number of characters in String (must be even)
     * @return Converted String
     */
    public static String convRegistersToString(int[] registers, int offset, int stringLength) {
        byte[] result = new byte[stringLength];
        byte[] registerResult;

        for (int i = 0; i < stringLength / 2 + stringLength % 2; i++) {
            registerResult = toByteArray(registers[offset + i]);
            result[i * 2] = registerResult[1];
            if (i*2 + 1 < stringLength) {
                result[i * 2 + 1] = registerResult[0];
            }
        }
        return new String(result).trim();
    }

    /**
     * Converts a String to 16 - Bit Registers
     *
     * @param stringToConvert String to Convert>
     * @return Converted String
     */
    public static int[] convStringToRegisters(String stringToConvert) {
        byte[] array = stringToConvert.getBytes(StandardCharsets.US_ASCII);
        int[] returnarray = new int[stringToConvert.length() / 2 + stringToConvert.length() % 2];
        for (int i = 0; i < returnarray.length; i++) {
            returnarray[i] = array[i * 2] << 8;
            if (i * 2 + 1 < array.length) {
                returnarray[i] = returnarray[i] | (array[i * 2 + 1]);
            }
        }

        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(returnarray).forEach(iVal ->
                    sb.append(String.format(" %08x", iVal))
            );
            LOG.debug("ASCII String HEX: {}", sb);
        }
        return returnarray;
    }

    public static byte[] toByteArray(int value) {
        byte[] result = new byte[2];
        result[1] = (byte) (value >> 8);
        result[0] = (byte) (value);
        return result;
    }

    public static boolean[] byteArrToBooleanArr(byte[] bytes) {
        boolean[] bools = new boolean[bytes.length];
        for (int i=0; i<bools.length; i++) {
            bools[i] = bytes[i]!=0;
        }
        return bools;
    }
}
