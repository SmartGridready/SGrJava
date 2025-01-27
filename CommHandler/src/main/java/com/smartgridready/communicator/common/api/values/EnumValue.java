package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.EnumEntryProductRecord;
import com.smartgridready.ns.v0.EnumMapProduct;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Map;

public class EnumValue extends Value {


    private final EnumRecord enumRecord;

    private EnumValue(EnumRecord enumRecord) {
        this.enumRecord = enumRecord;
    }

    @Override
    public EnumValue[] asArray() {
        return new EnumValue[]{this};
    }


    @Override
    public Int64Value enumToOrdinalValue(EnumMapProduct enumMapProduct) {

        if (enumRecord.getOrdinal() != null) {
            // validate ordinal
            EnumEntryProductRecord enumEntry = enumMapProduct.getEnumEntry().stream()
                    .filter(rec -> rec.getOrdinal().longValue() == enumRecord.getOrdinal().longValue())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Invalid ordinal=%d provided for enum.", enumRecord.getOrdinal())));

            return Int64Value.of(enumEntry.getOrdinal().longValue());
        }

        if (enumRecord.getLiteral() != null) {
            // validate against enumMap and convert
            EnumEntryProductRecord enumEntry = enumMapProduct.getEnumEntry().stream()
                    .filter(rec -> rec.getLiteral().equals(enumRecord.getLiteral()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Invalid literal=%s provided for enum.", enumRecord.getLiteral())));

            return Int64Value.of((enumEntry.getOrdinal() != null) ? enumEntry.getOrdinal().longValue() : EnumRecord.UNDEFINED_ORDINAL);
        }
        throw new IllegalArgumentException( String.format("Invalid literal=%s provided for enum.", enumRecord.getLiteral()));
    }

    private static EnumValue enumFromOrdinalValue(long ordinalValue, EnumMapProduct enumMapProduct) {

        byte[] hexMask = enumMapProduct.getHexMask();

        if (hexMask == null) {
            hexMask = new byte[]{
                    (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF
            };
        } else if (hexMask.length < 8) {
            byte[] int64Mask = new byte[8];
            System.arraycopy(hexMask, 0, int64Mask, 8 - hexMask.length, hexMask.length);
            hexMask = int64Mask;
        } else if (hexMask.length > 8) {
            byte[] int64Mask = new byte[8];
            System.arraycopy(hexMask, hexMask.length - 8, int64Mask, 0, 8);
            hexMask = int64Mask;
        }

        ordinalValue &= ByteBuffer.wrap(hexMask).getLong();

        for (EnumEntryProductRecord rec : enumMapProduct.getEnumEntry()) {
            long ordVal = (rec.getOrdinal() != null) ? rec.getOrdinal().longValue() : EnumRecord.UNDEFINED_ORDINAL;
            if (ordVal == ordinalValue) {
                return EnumValue.of(rec.getLiteral(), ordVal, rec.getDescription());
            }
        }

        return EnumValue.of(EnumRecord.UNDEFINED_LITERAL, EnumRecord.UNDEFINED_ORDINAL, String.format("Invalid enumeration ordinal=%d returned from modbus.", ordinalValue));
    }

    @Override
    public byte getInt8() {
        if (enumRecord.getOrdinal() != null) {
            checkInt8(enumRecord.getOrdinal());
            return enumRecord.getOrdinal().byteValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public short getInt8U() {
        if (enumRecord.getOrdinal() != null) {
            checkInt8U(enumRecord.getOrdinal());
            return enumRecord.getOrdinal().shortValue();
        }
        return (short)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public short getInt16() {
        if (enumRecord.getOrdinal() != null) {
            checkInt16(enumRecord.getOrdinal());
            return enumRecord.getOrdinal().shortValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public int getInt16U() {
        if (enumRecord.getOrdinal() != null) {
            checkInt16U(enumRecord.getOrdinal());
            return enumRecord.getOrdinal().intValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public int getInt32() {
        if (enumRecord.getOrdinal() != null) {
            checkInt32(enumRecord.getOrdinal().longValue());
            return enumRecord.getOrdinal().intValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public long getInt32U() {
        if (enumRecord.getOrdinal() != null) {
            checkInt32U(enumRecord.getOrdinal().longValue());
            return enumRecord.getOrdinal().longValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public long getInt64() {
        if (enumRecord.getOrdinal() != null) {
            return enumRecord.getOrdinal().longValue();
        }
        return (byte)EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public BigInteger getInt64U() {
        if (enumRecord.getOrdinal() != null) {
            return BigInteger.valueOf(enumRecord.getOrdinal().longValue());
        }
        return BigInteger.valueOf(EnumRecord.UNDEFINED_ORDINAL);
    }

    @Override
    public float getFloat32() {
        if (enumRecord.getOrdinal() != null) {
            return enumRecord.getOrdinal().longValue();
        }
        return EnumRecord.UNDEFINED_ORDINAL;
    }

    @Override
    public double getFloat64() {
        return getFloat32();
    }

    @Override
    public String getString() {
        return enumRecord.getLiteral();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public EnumRecord getEnum() {
        return enumRecord;
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        throw new UnsupportedOperationException("Cannot convert from an enum value to a bitmap value.");
    }

    @Override
    public String getJson() {
        // pure JSON string should be enclosed in double-quotes
        return "\"" + enumRecord.getLiteral() + "\"";
    }

    @Override
    public void absValue() {
        // not available
    }

    @Override
    public void roundToInt() {
        // not available
    }

    public static EnumValue of(String literal) {
        return of(literal, null, null);
    }

    public static EnumValue of(long ordinal) {
        return of(null, ordinal, null);
    }

    public static EnumValue of(String literal, Long ordinal, String description) {
        return new EnumValue(new EnumRecord(literal, ordinal, description));
    }

    public static EnumValue of(Long ordinal, EnumMapProduct enumMapProduct) {
        return enumFromOrdinalValue(ordinal, enumMapProduct);
    }

    @Override
    public String toString() {
        return enumRecord.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        EnumValue enumValue = (EnumValue) o;

        return new EqualsBuilder().append(enumRecord, enumValue.enumRecord).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(enumRecord).toHashCode();
    }
}
