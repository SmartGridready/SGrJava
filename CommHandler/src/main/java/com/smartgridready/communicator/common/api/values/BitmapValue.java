package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.BitmapEntryProduct;
import com.smartgridready.ns.v0.BitmapProduct;
import com.smartgridready.communicator.modbus.helper.ConversionHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BitmapValue extends Value {

    private final List<BitmapRecord> value;
    private BigInteger modbusValue;

    private BitmapValue() {
        this.value = new LinkedList<>();
    }

    private BitmapValue(List<BitmapRecord> value) {
        this.value = value;
    }

    @Override
    public byte getInt8() {
        checkInt8(modbusValue.longValue());
        return (byte)modbusValue.shortValue();
    }

    @Override
    public short getInt8U() {
        checkInt8U(modbusValue.longValue());
        return modbusValue.shortValue();
    }

    @Override
    public short getInt16() {
        checkInt16(modbusValue.longValue());
        return modbusValue.shortValue();
    }

    @Override
    public int getInt16U() {
        checkInt16U(modbusValue.longValue());
        return modbusValue.intValue();
    }

    @Override
    public int getInt32() {
        checkInt32(modbusValue.longValue());
        return modbusValue.intValue();
    }

    @Override
    public long getInt32U() {
        checkInt32U(modbusValue.longValue());
        return modbusValue.longValue();
    }

    @Override
    public long getInt64() {
        checkInt64(modbusValue);
        return modbusValue.longValue();
    }

    @Override
    public BigInteger getInt64U() {
        checkInt64U(modbusValue);
        return modbusValue;
    }

    @Override
    public float getFloat32() {
        throw new UnsupportedOperationException("Cannot convert bitmap to float32.");
    }

    @Override
    public double getFloat64() {
        throw new UnsupportedOperationException("Cannot convert bitmap to float64.");
    }

    @Override
    public String getString() {
        return getBitmap().keySet().stream()
                .map(key -> key + "=" + getBitmap().get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public boolean getBoolean() {
        throw new UnsupportedOperationException("Cannot convert bitmap to boolean.");
    }

    @Override
    public EnumRecord getEnum() {
        throw new UnsupportedOperationException("Cannot convert bitmap to enum.");
    }

    @Override
    public Map<String, Boolean> getBitmap() {
        Map<String, Boolean> res = new LinkedHashMap<>();
        value.forEach( bitmapRecord -> res.put(bitmapRecord.getLiteral(), bitmapRecord.getValue()));
        return res;
    }

    @Override
    public String getJson() {
        // TODO use actual JSON nodes in the future
        return getBitmap().keySet().stream()
                .map(key -> "\"" + key + "\":" + getBitmap().get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    @Override
    public void absValue() {
        // not required
    }

    @Override
    public void roundToInt() {
        // not required
    }


    // Factory method used to write bitmaps
    public static BitmapValue of(Map<String, Boolean> bitmap) {
        List<BitmapRecord> bitmapRecords = new LinkedList<>();
        bitmap.forEach( (literal, value) -> bitmapRecords.add(new BitmapRecord(literal, value, null)));
        return new BitmapValue(bitmapRecords);
    }

    // Factory method used when reading bitmaps from modbus
    public static BitmapValue of(int[] registers, BitmapProduct bitMapping) {
        BitmapValue bitmapValue = new BitmapValue();
        ByteBuffer byteBuffer = ConversionHelper.byteBufFromRegisters(registers);

        // initialize modbus value
        bitmapValue.modbusValue = new BigInteger(byteBuffer.array());

        // initialize bitmap records
        bitMapping.getBitmapEntry().forEach(
                mappingEntry -> createBitmapRecordsFromModbusValue(bitmapValue, mappingEntry));

        return bitmapValue;
    }

    private static void createBitmapRecordsFromModbusValue(BitmapValue bitmapValue, BitmapEntryProduct mappingEntry) {

        BigInteger mask = new BigInteger(mappingEntry.getHexMask());
        BitmapRecord bitmapRecord = new BitmapRecord(
                mappingEntry.getLiteral(),
                bitmapValue.modbusValue.abs().and(mask.abs()).compareTo(BigInteger.ZERO)!=0,
                mappingEntry.getDescription());

        bitmapValue.value.add(bitmapRecord);
    }

    public int[] toModbusRegisters(int modbusSize, BitmapProduct bitMapping) {

        Map<BitmapRecord, BitmapEntryProduct> recordMapping = new LinkedHashMap<>();

        // validate bitmapRecords against bitMapping (no unknown literals allowed)
        for (BitmapRecord bitmapRecord : value) {
            BitmapEntryProduct bitmapEntry = bitMapping.getBitmapEntry().stream()
                    .filter(mappingRecord -> mappingRecord.getLiteral().equals(bitmapRecord.getLiteral()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Literal %s is invalid for the given data point.", bitmapRecord.getLiteral())));

            recordMapping.put(bitmapRecord, bitmapEntry);
        }

        // Convert to modbus register
        byte[] registerBytes = new byte[modbusSize*2];
        modbusValue = new BigInteger(registerBytes);
        for( Map.Entry<BitmapRecord, BitmapEntryProduct> mapping : recordMapping.entrySet()) {
            if (mapping.getKey().getValue()) {
                BigInteger mask = new BigInteger(mapping.getValue().getHexMask());
                modbusValue = modbusValue.abs().or(mask.abs());
            }
        }

        byte[] modbusBytes = modbusValue.toByteArray();
        System.arraycopy(modbusBytes, 0, registerBytes, registerBytes.length - modbusBytes.length, modbusBytes.length);
        return ConversionHelper.bytesToRegisters(registerBytes);
    }

    @Override
    public String toString() {
        return value.stream()
                .map( bitmapRecord -> String.format("%s=%b : %s", bitmapRecord.getLiteral(), bitmapRecord.getValue(), bitmapRecord.getDescription()))
                .collect(Collectors.joining("; ", "{", "}"));
    }

    @Override
    public BitmapValue[] asArray() {
        return new BitmapValue[]{this};
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        BitmapValue that = (BitmapValue) o;

        return new EqualsBuilder().append(modbusValue, that.modbusValue).isEquals()
                && EqualsBuilder.reflectionEquals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).append(modbusValue).toHashCode();
    }
}
