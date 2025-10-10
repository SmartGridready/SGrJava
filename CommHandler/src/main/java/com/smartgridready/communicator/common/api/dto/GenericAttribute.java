package com.smartgridready.communicator.common.api.dto;

import com.smartgridready.communicator.common.api.values.DataType;
import com.smartgridready.communicator.common.api.values.DataTypeInfo;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.ns.v0.GenericAttributeListProductEnd;
import com.smartgridready.ns.v0.GenericAttributeProduct;
import com.smartgridready.ns.v0.GenericAttributeProductEnd;
import com.smartgridready.ns.v0.Units;
import com.smartgridready.communicator.common.api.values.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements a generic attribute facade.
 */
public class GenericAttribute {

    private final String name;
    private final Value value;
    private final DataTypeInfo dataType;
    private final Units unit;
    private final List<GenericAttribute> children;

    /**
     * Construct a new instance.
     * @param name the attribute name
     * @param value the attribute value
     * @param dataType the attribute value's data type
     * @param unit the attribute value's unit of measurement
     * @param children a list of child attributes
     */
    public GenericAttribute(
            String name,
            Value value,
            DataTypeInfo dataType,
            Units unit,
            List<GenericAttribute> children) {
        this.name = name;
        this.value = value;
        this.dataType = dataType;
        this.unit = unit;
        this.children = children;
    }

    /**
     * Gets the attribute name.
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the attribute value.
     * @return a value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Gets the value's data type.
     * @return a data type info
     */
    public DataTypeInfo getDataType() {
        return dataType;
    }

    /**
     * Gets the value's unit of measurement.
     * @return an instance of {@link Units}
     */
    public Units getUnit() {
        return unit;
    }

    /**
     * Gets the attribute's sub-attributes.
     * @return a list of {@link GenericAttribute}
     */
    public List<GenericAttribute> getChildren() {
        return children;
    }

    /**
     * Creates a new instance.
     * @param genAttribute the attribute specification
     * @return a new instance of {@link GenericAttribute}
     */
    public static GenericAttribute of(GenericAttributeProduct genAttribute) {

        List<GenericAttribute> children = Optional.ofNullable(genAttribute.getGenericAttributeList())
                .map(GenericAttributeListProductEnd::getGenericAttributeListElement)
                .map(GenericAttribute::mapGenericAttributes).orElse(new ArrayList<>());

        DataTypeInfo dt = DataType.getDataTypeInfo(genAttribute.getDataType()).orElse(null);
        Value v = (genAttribute.getValue() != null)
            ? Value.fromString(genAttribute.getDataType(), genAttribute.getValue())
            : null;

        return new GenericAttribute(
                genAttribute.getName(),
                v,
                dt,
                genAttribute.getUnit(),
                children
        );
    }

    /**
     * Creates a new instance.
     * @param genAttributeChild the attribute specification
     * @return a new instance of {@link GenericAttribute}
     */
    public static GenericAttribute of(GenericAttributeProductEnd genAttributeChild) {

        DataTypeInfo dt = DataType.getDataTypeInfo(genAttributeChild.getDataType()).orElse(null);
        Value v = (genAttributeChild.getValue() != null) ? Value.fromString(genAttributeChild.getDataType(), genAttributeChild.getValue()) : null;

        return new GenericAttribute(
                genAttributeChild.getName(),
                v,
                dt,
                genAttributeChild.getUnit(),
                null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString("\t"));

        Optional.ofNullable(children).ifPresent(childList ->
                childList.forEach(child -> sb.append("\t\t").append(child.toString("\t\t"))));

        return sb.toString();
    }

    private String toString(String insertTabs) {
        return "\n" + insertTabs +
                        ("name: ") + name +
                        (value!=null ?    " | value: " + value.getString() : "") +
                        (dataType!=null ? " | type : " + dataType:"") +
                        (unit!=null ?     " | unit : " + unit.name():"");
    }

    /**
     * Maps a list of generic attributes.
     * @param genericAttributeProducts the attribute specifications
     * @return a list of {@link GenericAttribute}
     */
    public static List<GenericAttribute> mapGenericAttributes(List<GenericAttributeProductEnd> genericAttributeProducts) {
        ArrayList<GenericAttribute> genericAttributes = new ArrayList<>();
        genericAttributeProducts.forEach(genericAttributeProduct ->
                genericAttributes.add(GenericAttribute.of(genericAttributeProduct)));

        return genericAttributes.stream()
                .sorted(java.util.Comparator.comparing(GenericAttribute::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;

        if (o==null || getClass()!=o.getClass()) return false;

        GenericAttribute that = (GenericAttribute) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(value, that.value)
                .append(dataType, that.dataType)
                .append(unit, that.unit)
                .isEquals() && EqualsBuilder.reflectionEquals(children, that.children);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(value)
                .append(dataType)
                .append(unit)
                .append(children)
                .toHashCode();
    }
}
