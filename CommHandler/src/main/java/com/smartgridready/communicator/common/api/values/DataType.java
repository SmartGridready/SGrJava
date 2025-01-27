package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.ModbusDataType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum DataType {

    BOOLEAN,
    INT8,
    INT16,
    INT32,
    INT64,
    INT8U,
    INT16U,
    INT32U,
    INT64U,
    FLOAT32,
    FLOAT64,
    STRING,
    DATE_TIME,
    ENUM,
    BITMAP,
    JSON,
    UNKNOWN;

    private static final Map<DataType, DataTypeInfo> TYPE_MAP = new EnumMap<>(DataType.class);
    static {
        TYPE_MAP.put(BOOLEAN, new DataTypeInfo(BOOLEAN, DataTypeProduct::getBoolean, ModbusDataType::getBoolean, DataTypeProduct::setBoolean, null, List.of(BooleanValue.of(true), BooleanValue.of(false))));
        TYPE_MAP.put(INT8, new DataTypeInfo(INT8, DataTypeProduct::getInt8, ModbusDataType::getInt8, DataTypeProduct::setInt8, ModbusDataType::setInt8, List.of(Int8Value.of(Byte.MIN_VALUE), Int8Value.of(Byte.MAX_VALUE))));
        TYPE_MAP.put(INT16, new DataTypeInfo(INT16, DataTypeProduct::getInt16, ModbusDataType::getInt16, DataTypeProduct::setInt16, ModbusDataType::setInt16, List.of(Int16Value.of(Short.MIN_VALUE), Int16Value.of(Short.MAX_VALUE))));
        TYPE_MAP.put(INT32, new DataTypeInfo(INT32, DataTypeProduct::getInt32, ModbusDataType::getInt32, DataTypeProduct::setInt32, ModbusDataType::setInt32, List.of(Int32Value.of(Integer.MIN_VALUE), Int32Value.of(Integer.MAX_VALUE))));
        TYPE_MAP.put(INT64, new DataTypeInfo(INT64, DataTypeProduct::getInt64, ModbusDataType::getInt64, DataTypeProduct::setInt64, ModbusDataType::setInt64, List.of(Int64Value.of(Long.MIN_VALUE), Int64Value.of(Long.MAX_VALUE))));
        TYPE_MAP.put(INT8U, new DataTypeInfo(INT8U, DataTypeProduct::getInt8U, ModbusDataType::getInt8U, DataTypeProduct::setInt8U, ModbusDataType::setInt8U, List.of(Int8UValue.of((short) 0), Int8UValue.of((short) 255))));
        TYPE_MAP.put(INT16U, new DataTypeInfo(INT16U, DataTypeProduct::getInt16U, ModbusDataType::getInt16U, DataTypeProduct::setInt16U, ModbusDataType::setInt16U, List.of(Int16UValue.of(0), Int16UValue.of(65535))));
        TYPE_MAP.put(INT32U,new DataTypeInfo(INT32U, DataTypeProduct::getInt32U, ModbusDataType::getInt32U, DataTypeProduct::setInt32U, ModbusDataType::setInt32U, List.of(Int32UValue.of(0), Int32UValue.of(4294967295L))));
        TYPE_MAP.put(INT64U, new DataTypeInfo(INT64U, DataTypeProduct::getInt64U, ModbusDataType::getInt64U, DataTypeProduct::setInt64U, ModbusDataType::setInt64U, List.of(Int64UValue.of(BigInteger.ZERO), Int64UValue.of(new BigInteger("18446744073709551615")))));
        TYPE_MAP.put(FLOAT32, new DataTypeInfo(FLOAT32, DataTypeProduct::getFloat32, ModbusDataType::getFloat32, DataTypeProduct::setFloat32, ModbusDataType::setFloat32, List.of(Float32Value.of(-Float.MAX_VALUE), Float32Value.of(Float.MAX_VALUE))));
        TYPE_MAP.put(FLOAT64, new DataTypeInfo(FLOAT64, DataTypeProduct::getFloat64, ModbusDataType::getFloat64, DataTypeProduct::setFloat64, ModbusDataType::setFloat64, List.of(Float64Value.of(-Double.MAX_VALUE), Float64Value.of(Double.MAX_VALUE))));
        TYPE_MAP.put(STRING, new DataTypeInfo(STRING, DataTypeProduct::getString, ModbusDataType::getString, DataTypeProduct::setString, ModbusDataType::setString, List.of()));
        TYPE_MAP.put(DATE_TIME, new DataTypeInfo(DATE_TIME, DataTypeProduct::getDateTime, ModbusDataType::getDateTime, DataTypeProduct::setDateTime, ModbusDataType::setDateTime, List.of()));
        TYPE_MAP.put(ENUM, new DataTypeInfo(ENUM, DataTypeProduct::getEnum, ModbusDataType::getEnum, null, null, List.of()));
        TYPE_MAP.put(BITMAP, new DataTypeInfo(BITMAP, DataTypeProduct::getBitmap, ModbusDataType::getBitmap, null, null, List.of()));
        TYPE_MAP.put(JSON, new DataTypeInfo(JSON, DataTypeProduct::getJson, ModbusDataType::getString, DataTypeProduct::setString, ModbusDataType::setString, List.of()));
        TYPE_MAP.put(UNKNOWN, new DataTypeInfo(UNKNOWN, val -> null, val -> null, null, null, List.of()));
    }

    public static Optional<DataTypeInfo> getDataTypeInfo(DataType dataType) {
        return Optional.ofNullable(TYPE_MAP.get(dataType));
    }

    public static Optional<DataTypeInfo> getDataTypeInfo(DataTypeProduct dataTypeProduct) {
        if (dataTypeProduct == null) {
            return Optional.empty();
        }
        // Retrieve the value that is set within dataTypeProduct
        Optional<DataTypeInfo> dataTypeOpt = TYPE_MAP.values().stream().filter(en -> en.getGetGenValMethod().apply(dataTypeProduct) != null).findFirst();

        // Handle ENUM and BITMAP datatypes, since their range is not static and defined within the device
        // specific EI-XML.
        if (dataTypeOpt.isPresent() && dataTypeOpt.get().getType() == ENUM) {
            // Clone the ENUM datatype and enhance it with the range given by the datapoint read from the EI-XML
            dataTypeOpt = Optional.of(cloneUsingRange(dataTypeOpt.get(), getEnumRange(dataTypeProduct)));
        } else if (dataTypeOpt.isPresent() && dataTypeOpt.get().getType() == BITMAP) {
            // Clone the BITMAP datatype and enhance it with the range given by datapoint read from the EI-XML
            dataTypeOpt = Optional.of(cloneUsingRange(dataTypeOpt.get(), getBitmapRange(dataTypeProduct)));
        }
        return dataTypeOpt;
    }

    public static Optional<DataTypeInfo> getDataTypeInfo(ModbusDataType modbusDataType) {
        if (modbusDataType == null) {
            return Optional.empty();
        }
        return TYPE_MAP.values().stream().filter(en -> en.getGetModbusValMethod().apply(modbusDataType) != null).findFirst();
    }

    public static String getGenDataTypeName(DataTypeProduct dataTypeProduct) {
        return getDataTypeInfo(dataTypeProduct).map(DataTypeInfo::getTypeName).orElse(DataType.UNKNOWN.name());
    }

    public static String getModbusDataTypeName(ModbusDataType modbusDataType) {
        return getDataTypeInfo(modbusDataType).map(DataTypeInfo::getTypeName).orElse(DataType.UNKNOWN.name());
    }

    private static List<Value> getEnumRange(DataTypeProduct dataTypeProduct) {

        List<Value> definedValues = new ArrayList<>();
        Optional.ofNullable(dataTypeProduct)
                .ifPresent(dataTypeProd -> dataTypeProd.getEnum().getEnumEntry()
                        .forEach(entry -> definedValues.add(
                                EnumValue.of(
                                        entry.getLiteral(),
                                        (entry.getOrdinal() != null) ? entry.getOrdinal().longValue() : null,
                                        entry.getDescription()))));
        return definedValues;
    }

    private static List<Value> getBitmapRange(DataTypeProduct dataTypeProduct) {
        List<Value> definedValues = new ArrayList<>();
        Optional.ofNullable(dataTypeProduct)
                .ifPresent(dataTypeProd -> dataTypeProd.getBitmap().getBitmapEntry()
                        .forEach(entry -> definedValues.add(StringValue.of(entry.getLiteral()))));
        return definedValues;
    }

    private static DataTypeInfo cloneUsingRange(DataTypeInfo dataTypeInfo, List<Value> range) {
        return new DataTypeInfo(dataTypeInfo.getType(), dataTypeInfo.getGetGenValMethod(), dataTypeInfo.getGetModbusValMethod(), dataTypeInfo.getSetGenValMethod(), dataTypeInfo.getSetModbusValMethod(), range);
    }
}
