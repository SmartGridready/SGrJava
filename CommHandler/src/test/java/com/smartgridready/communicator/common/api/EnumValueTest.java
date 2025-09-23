package com.smartgridready.communicator.common.api;

import com.smartgridready.ns.v0.EnumEntryProductRecord;
import com.smartgridready.ns.v0.EnumMapProduct;
import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Int32Value;
import com.smartgridready.communicator.common.api.values.Int64Value;
import com.smartgridready.communicator.common.api.values.Value;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumValueTest {

    @Test
    void enumToOrdinalValue() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Correct mapping
        Value testVal = EnumValue.of("ENUM_2");
        Value resVal  = testVal.enumToOrdinalValue(enumMap);
        assertEquals(2, resVal.getInt32());
        assertEquals(BigInteger.valueOf(2), resVal.getInt64U());

        testVal = EnumValue.of(1);
        resVal = testVal.enumToOrdinalValue(enumMap);
        assertEquals((byte)1, resVal.getInt8());
        assertEquals((byte)1, (byte)resVal.getInt8U());
        assertEquals((byte)1, (byte)resVal.getInt16());
        assertEquals((byte)1, (byte)resVal.getInt16U());
        assertEquals((byte)1, (byte)resVal.getInt32());
        assertEquals((byte)1, (byte)resVal.getInt32U());
        assertEquals((byte)1, (byte)resVal.getInt64());
        assertEquals((byte)1, (byte)resVal.getInt64U().intValue());
        assertEquals((byte)1, (byte)resVal.getFloat32());
        assertEquals((byte)1, (byte)resVal.getFloat64());
    }

    @Test
    void enumToOrdinalValue_InvalidEnum() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Invalid enum value
        Value testValEx1 = EnumValue.of("ENUM_3");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class, () -> testValEx1.enumToOrdinalValue(enumMap));
        assertEquals("Invalid literal=ENUM_3 provided for enum.", ex.getMessage());

        Value testValEx2 = EnumValue.of(0);
        ex = assertThrows(
                IllegalArgumentException.class, () -> testValEx2.enumToOrdinalValue(enumMap));
        assertEquals("Invalid ordinal=0 provided for enum.", ex.getMessage());

        Value testValEx3 = EnumValue.of(256).enumToOrdinalValue(enumMap);

        ex = assertThrows(
                IllegalArgumentException.class, testValEx3::getInt8);
        assertEquals("Cannot convert value 256 to int8. Value out of range", ex.getMessage());
    }

    @Test
    void enumFromOrdinalValueNoMask() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Correct mapping
        Value testVal = Int64Value.of(2);
        Value resVal = EnumValue.of(testVal.getInt64(), enumMap);
        assertEquals(2, resVal.getEnum().getOrdinal());
        assertEquals("ENUM_2", resVal.getEnum().getLiteral());
        assertEquals("ENUM_2", resVal.getString());
        assertEquals( "ENUM_2:2 | Description of ENUM_2", resVal.toString());
    }

    @Test
    void enumFromOrdinalValueNoMask_InvalidOrdinal() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Invalid enum values returned
        Value testVal = Int64Value.of(32);
        Value resVal  = EnumValue.of(testVal.getInt64(), enumMap);
        assertEquals(EnumRecord.UNDEFINED_LITERAL, resVal.getEnum().getLiteral());
        assertEquals(EnumRecord.UNDEFINED_ORDINAL, resVal.getEnum().getOrdinal());
        assertEquals("Invalid enumeration ordinal=32.",  resVal.getEnum().getDescription());
    }

    @Test
    void enumFromOrdinalValueWithMask() {

        // Correct mapping with small mask
        EnumMapProduct enumMap = createEnumMap(new byte[]{0x01, 0x00});
        Value testVal = Int64Value.of(0xFFFF);
        Value resVal = EnumValue.of(testVal.getInt64(), enumMap);
        assertEquals("ENUM_256", resVal.getEnum().getLiteral());
        assertEquals("ENUM_256", resVal.getString());

        enumMap = createEnumMap(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00});
        testVal = Int64Value.of(0xFFFF);
        resVal = EnumValue.of(testVal.getInt64(), enumMap);
        assertEquals("ENUM_256", resVal.getEnum().getLiteral());
    }

    @Test
    void enumFromOrdinalValueWithMask_InvalidOrdinal() {

        EnumMapProduct enumMap = createEnumMap(new byte[]{0x01, (byte)0xFF});
        Value testVal = Int32Value.of(0xFFFF);
        Value resVal = EnumValue.of(testVal.getInt64(), enumMap);
        assertEquals(EnumRecord.UNDEFINED_LITERAL, resVal.getEnum().getLiteral());
        assertEquals(EnumRecord.UNDEFINED_ORDINAL, resVal.getEnum().getOrdinal());
        assertEquals("Invalid enumeration ordinal=511.", resVal.getEnum().getDescription());
    }

    @Test
    void enumFromLiteralValueNoMask() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Correct mapping
        Value resVal = EnumValue.of("ENUM_2", enumMap);
        assertEquals(2, resVal.getEnum().getOrdinal());
        assertEquals("ENUM_2", resVal.getEnum().getLiteral());
        assertEquals("ENUM_2", resVal.getString());
        assertEquals( "ENUM_2:2 | Description of ENUM_2", resVal.toString());
    }

    @Test
    void enumFromLiteralValueNoMask_InvalidLiteral() {

        EnumMapProduct enumMap = createEnumMap(null);

        // Invalid enum values returned
        Value resVal = EnumValue.of("ENUM_32", enumMap);
        assertEquals(EnumRecord.UNDEFINED_LITERAL, resVal.getEnum().getLiteral());
        assertEquals(EnumRecord.UNDEFINED_ORDINAL, resVal.getEnum().getOrdinal());
        assertEquals("Invalid enumeration literal=ENUM_32.",  resVal.getEnum().getDescription());
    }

    @Test
    void enumFromLiteralValueWithMask() {

        // Correct mapping with small mask
        EnumMapProduct enumMap = createEnumMap(new byte[]{0x01, 0x00});
        Value resVal = EnumValue.of("ENUM_256", enumMap);
        assertEquals("ENUM_256", resVal.getEnum().getLiteral());
        assertEquals("ENUM_256", resVal.getString());
        assertEquals(256, resVal.getEnum().getOrdinal());

        enumMap = createEnumMap(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00});
        resVal = EnumValue.of("ENUM_256", enumMap);
        assertEquals("ENUM_256", resVal.getEnum().getLiteral());
        assertEquals(256, resVal.getEnum().getOrdinal());
    }

    @Test
    void enumFromLiteralValueWithMask_InvalidLiteral() {

        EnumMapProduct enumMap = createEnumMap(new byte[]{0x01, (byte)0xFF});
        Value resVal = EnumValue.of("ENUM_32", enumMap);
        assertEquals(EnumRecord.UNDEFINED_LITERAL, resVal.getString());
        assertEquals(EnumRecord.UNDEFINED_ORDINAL, resVal.getEnum().getOrdinal());
        assertEquals("Invalid enumeration literal=ENUM_32.", resVal.getEnum().getDescription());
    }

    private EnumMapProduct createEnumMap(byte[] hexMask) {
        EnumMapProduct retVal = new EnumMapProduct();
        List<EnumEntryProductRecord> enumEntries = retVal.getEnumEntry();
        enumEntries.add(createEnumRecord("ENUM_1", 1));
        enumEntries.add(createEnumRecord("ENUM_2", 2));
        enumEntries.add(createEnumRecord("ENUM_255", 255));
        enumEntries.add(createEnumRecord("ENUM_256", 256));
        retVal.setHexMask(hexMask);
        return retVal;
    }

    private EnumEntryProductRecord createEnumRecord(String literal, int ordinal) {
        EnumEntryProductRecord enumRecord = new EnumEntryProductRecord();
        enumRecord.setLiteral(literal);
        enumRecord.setOrdinal(ordinal);
        enumRecord.setDescription("Description of " + literal);
        return enumRecord;
    }
}
