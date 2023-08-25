package utils;

import com.smartgridready.ns.v0.V0Package;

import java.util.HashMap;
import java.util.Map;

public class SGrGDPTypeToNameMapper {

    static final Map<Integer, String> SGR_MODBUS_TYPE_TO_NAME = new HashMap<>();
    static {

        // Modbus IF types
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__ENUM2BITMAP_INDEX, "ENUM2BITMAP_INDEX");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__BOOLEAN, "BOOL");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT8, "INT8");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT16, "INT16");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT32, "INT32");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT64, "INT64");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT8_U, "INT8_U");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT16_U, "INT16_U");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT32_U, "INT32_U");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__INT64_U, "INT64_U");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__FLOAT32, "FLOAT32");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__FLOAT64, "FLOAT64");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__ENUM, "ENUM");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__DATE_TIME, "DATE_TIME");
        SGR_MODBUS_TYPE_TO_NAME.put(V0Package.MODBUS_DATA_TYPE__STRING, "STRING");
    }

    static final Map<Integer, String> SGR_GENERIC_TYPE_TO_NAME = new HashMap<>();
    static {
        // Generic IF types
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT8, "INT8");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT8_U, "INT8U");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT16, "INT16");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT16_U, "INT16U");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT32, "INT32");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT32_U, "INT32U");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT64, "INT64");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__INT64_U, "INT64U");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__FLOAT32, "FLOAT32");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__FLOAT64, "FLOAT64");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__ENUM, "ENUM");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__STRING, "STRING");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__ENUM2BITMAP_INDEX, "ENUM2BITMAP_INDEX");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE__DATE_TIME, "DATE_TIME");
        SGR_GENERIC_TYPE_TO_NAME.put(V0Package.DATA_TYPE_FEATURE_COUNT, "FEATURE_COUNT");
    }


    public static String getModbusName(int aGDPType) {
        return SGR_MODBUS_TYPE_TO_NAME.get(aGDPType);
    }

    public static String getGenericName(int aGenType) {
        return SGR_GENERIC_TYPE_TO_NAME.get(aGenType);
    }
}