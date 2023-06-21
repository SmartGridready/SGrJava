package utils;

import com.smartgridready.ns.v0.V0Package;

import java.util.HashMap;
import java.util.Map;

public class SGrGDPTypeToNameMapper {

    static final Map<Integer, String> SGR_GDP_TYPE_TO_NAME = new HashMap<>();
    static {
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__ENUM2BITMAP_INDEX, "ENUM2BITMAP_INDEX");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__BOOLEAN, "BOOL");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT8, "INT8");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT16, "INT16");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT32, "INT32");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT64, "INT64");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT8_U, "INT8_U");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT16_U, "INT16_U");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT32_U, "INT32_U");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__INT64_U, "INT64_U");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__FLOAT32, "FLOAT32");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__FLOAT64, "FLOAT64");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__ENUM, "ENUM");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__DATE_TIME, "DATE_TIME");
        SGR_GDP_TYPE_TO_NAME.put(V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE__STRING, "STRING");
    }

    public static String getName(int aGDPType) {
        return SGR_GDP_TYPE_TO_NAME.get(aGDPType);
    }
}
