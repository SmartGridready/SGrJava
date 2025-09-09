package com.smartgridready.communicator.common.api.values;

import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.EmptyType;
import com.smartgridready.ns.v0.ModbusDataType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Defines extended information for SGr data types.
 * 
 */
public class DataTypeInfo {

    private final DataType type;
    private final List<Value> range;

    private final Function<DataTypeProduct, Object> getGenValMethod;
    private final Function<ModbusDataType, Object> getModbusValMethod;
    private final BiConsumer<DataTypeProduct, EmptyType> setGenValMethod;
    private final BiConsumer<ModbusDataType, EmptyType> setModbusValMethod;

    /**
     * Constructs a new instance.
     * @param type the data type enumeration
     * @param getGenValMethod the method to get the correct data type from specification
     * @param getModbusValMethod the method to get the correct Modbus data type from specification
     * @param setGenValMethod the method to set the correct data type in specification
     * @param setModbusValMethod the method to set the correct Modbus data type in specification
     * @param range a list of {@link Value} defining the valid range of values
     */
    public DataTypeInfo(
            DataType type,
            Function<DataTypeProduct, Object> getGenValMethod,
            Function<ModbusDataType, Object> getModbusValMethod,
            BiConsumer<DataTypeProduct, EmptyType> setGenValMethod,
            BiConsumer<ModbusDataType, EmptyType> setModbusValMethod,
            List<Value> range) {
        this.type = type;
        this.getGenValMethod = getGenValMethod;
        this.getModbusValMethod = getModbusValMethod;
        this.setGenValMethod = setGenValMethod;
        this.setModbusValMethod = setModbusValMethod;
        this.range = range;
    }

    /**
     * Gets the function that gets the correct data type from specification.
     * @return a function
     */
    public Function<DataTypeProduct, Object> getGetGenValMethod() {
        return getGenValMethod;
    }

    /**
     * Gets the function that gets the correct Modbus data type from specification.
     * @return a function
     */
    public Function<ModbusDataType, Object> getGetModbusValMethod() {
        return getModbusValMethod;
    }

    /**
     * Gets the function that sets the correct data type in specification.
     * @return a function
     */
    public BiConsumer<DataTypeProduct, EmptyType> getSetGenValMethod() {
        return setGenValMethod;
    }

    /**
     * Gets the function that sets the correct Modbus data type in specification.
     * @return a function
     */
    public BiConsumer<ModbusDataType, EmptyType> getSetModbusValMethod() {
        return setModbusValMethod;
    }

    /**
     * Gets the data type enumeration.
     * @return an instance of {@link DataType}
     */
    public DataType getType() {
        return type;
    }

    /**
     * Gets the data type name.
     * @return a string
     */
    public String getTypeName() {
        return type.name();
    }

    /**
     * Gets the range of allowed values.
     * @return a list of {@link Value}
     */
    public List<Value> getRange() {
        return range;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        DataTypeInfo that = (DataTypeInfo) o;

        return new EqualsBuilder()
            .append(type, that.type)
            .append(range, that.range)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(type)
            .append(range)
            .toHashCode();
    }
}