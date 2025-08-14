package com.smartgridready.communicator.modbus.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A helper class for conversion of endianness.
 */
public class EndiannessConversionHelper {

    private static final Logger LOG = LoggerFactory.getLogger(EndiannessConversionHelper.class);

    private EndiannessConversionHelper() {
        throw new UnsupportedOperationException("Helper class, cannot be instantiated.");
    }

    /**
     * Changes double-word order.
     * @param mbregresp the array of register values to convert
     * @param size the number of double-words
     * @return an array of int
     */
    public static int[] changeDWordOrder(int[] mbregresp, int size) {

        int[] mbregconv;
        int c;
        if ((size % 4) > 0) {
            LOG.info("CHANGE_D_WORD_ORDER: Input Array length does not match");
            //rem/cb: check is D_WORD change needed in case of (size % 4) > 0
            mbregconv = mbregresp;
        } else {   //rem/cb:  check array of WORDS: is DWORD change needed in case of size > 4
            mbregconv = new int[256];
            for (c = 0; c < size; c = c + 4) {
                mbregconv[c + 1] = mbregresp[c + 3];
                mbregconv[c    ] = mbregresp[c + 2];
                mbregconv[c + 3] = mbregresp[c + 1];
                mbregconv[c + 2] = mbregresp[c];
                for (int i = 0; i < 4; i++) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(String.format("CHANGE_DWORD_ORDER converted %d: %08x, %08x", i, mbregresp[i], mbregconv[i]));
                    }
                }
            }
        }
        return mbregconv;
    }

    /**
     * Changes word order.
     * @param mbregresp the array of register values to convert
     * @param size the number of words
     * @return an array of int
     */
    public static int[] changeWordOrder(int[] mbregresp, int size) {

        int[] mbregconv;
        int c;
        if (size==1) {
            mbregconv = mbregresp; // just one word, no conversion
        }
        if ((size % 2)==0) {
            mbregconv = new int[256];
            for (c = 0; c < size; c = c + 2) {
                mbregconv[c] = mbregresp[c + 1];
                mbregconv[c + 1] = mbregresp[c];
            }
        } else { // Q&A:CB NO swap here: is this really intended?
            mbregconv = mbregresp;
        }
        for (int i = 0; i < size - 1; i++) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("CHANGE_WORD_ORDER converted %d: %08x, %08x", i, mbregresp[i], mbregconv[i]));
            }
        }
        return mbregconv;
    }

    /**
     * Changes the byte order in registers.
     * @param mbregresp the array of register values to convert
     * @param size the number of registers
     * @return an array of int
     */
    public static int[] changeByteOrder(int[] mbregresp, int size) {

        int[] mbregconv = new int[256];
        int c;
        for (c = 0; c < size; c++) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("mbregresp: {} ", mbregresp);
                LOG.debug("mbregresp[0]: {} ", String.format("%08x", mbregresp[0]));
            }
            byte[] result = new byte[2];
            result[1] = (byte) (mbregresp[c] >> 8);
            result[0] = (byte) mbregresp[c];

            mbregconv[c] = Byte.toUnsignedInt(result[1])
                    + (Byte.toUnsignedInt(result[0]) << 8);

            if (LOG.isDebugEnabled()) {
                LOG.debug(String.format("CHANGE_BYTE_ORDER converted %d: %02x, %02x, %08x", c, result[1], result[0], mbregconv[c]));
                LOG.debug("mbregconv: {}", mbregconv);
                LOG.debug("mbregconv[0]: {} ", String.format("%08x", mbregconv[0]));
            }
        }
        return mbregconv;
    }
}
