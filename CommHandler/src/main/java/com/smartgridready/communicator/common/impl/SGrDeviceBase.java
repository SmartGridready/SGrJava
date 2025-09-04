package com.smartgridready.communicator.common.impl;

import com.smartgridready.communicator.common.api.dto.DynamicRequestParameter;
import com.smartgridready.communicator.common.api.dto.InfoText;
import com.smartgridready.communicator.common.api.values.DataType;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.ns.v0.ConfigurationList;
import com.smartgridready.ns.v0.ConfigurationListElement;
import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DataPointDescription;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.DynamicParameterDescription;
import com.smartgridready.ns.v0.DynamicParameterDescriptionListElement;
import com.smartgridready.ns.v0.FunctionalProfileBase;
import com.smartgridready.ns.v0.Language;

import io.vavr.control.Either;

import com.smartgridready.ns.v0.GenericAttributeListProduct;

import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.dto.ConfigurationValue;
import com.smartgridready.communicator.common.api.dto.DataPoint;
import com.smartgridready.communicator.common.api.dto.DataPointValue;
import com.smartgridready.communicator.common.api.dto.DeviceInfo;
import com.smartgridready.communicator.common.api.dto.FunctionalProfile;
import com.smartgridready.communicator.common.api.dto.GenericAttribute;
import com.smartgridready.communicator.common.api.dto.OperationEnvironment;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.common.GenDriverException;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;

/**
 * Base class for all kinds of device communication interfaces.
 * @param <D> The type of device specification.
 * @param <F> The type of functional profile.
 * @param <P> The type of data point.
 */
public abstract class SGrDeviceBase<
        D extends DeviceFrame,
        F extends FunctionalProfileBase,
        P extends DataPointBase> implements GenDeviceApi {

    /** The interface-specific device specification. */
    protected final D device;

    /**
     * Collection of access permissions.
     */
    public enum RwpDirections {
        /** Data point is readable. */
        READ(Stream.of(DataDirectionProduct.R, DataDirectionProduct.RW, DataDirectionProduct.RWP, DataDirectionProduct.C).collect(Collectors.toSet())),
        /** Data point is writable. */
        WRITE(Stream.of(DataDirectionProduct.W, DataDirectionProduct.RW, DataDirectionProduct.RWP).collect(Collectors.toSet())),
        /** Data point is constant. */
        CONST(Collections.singleton(DataDirectionProduct.C));

        private final Set<DataDirectionProduct> opAllowedTypes;

        RwpDirections(Set<DataDirectionProduct> opAllowedTypes) {
            this.opAllowedTypes = opAllowedTypes;
        }
    }

    /**
     * Comparators.
     */
    public enum Comparator {
        /** Minimum. */
        MIN(((val, lim) -> val.compareTo(lim) < 0)),
        /** Maximum. */
        MAX(((val, lim) -> val.compareTo(lim) > 0));

        private final BiPredicate<Double, Double> cmpFunc;

        Comparator(BiPredicate<Double,Double> cmpFunc) {
            this.cmpFunc = cmpFunc;
        }

        BiPredicate<Double,Double> getCmpFunc() {
            return cmpFunc;
        }
    }

    /**
     * Constructs a new instance.
     * @param device the generic device specification
     */
    protected SGrDeviceBase(D device) {
        this.device = device;
    }

    /**
     * Finds a functional profile by name.
     * @param profileName the name of the functional profile
     * @return an optional functional profile
     */
    protected abstract Optional<F> findProfile(String profileName);

    /**
     * Finds a data point of a given functional profile by name.
     * @param functionalProfile the functional profile
     * @param dataPointName the name of the data point to find
     * @return an optional data point
     */
    protected abstract Optional<P> findDataPointForProfile(F functionalProfile, String dataPointName);

    /**
     * Finds a data point by functional profile and data point name.
     * @param profileName the functional profile name
     * @param dataPointName the data point name
     * @return a generic data point
     * @throws GenDriverException if the device specification contains errors
     */
    protected P findDataPoint(String profileName, String dataPointName) throws GenDriverException {
        Optional<F> functionalProfile = findProfile(profileName);
        if (functionalProfile.isPresent()) {
            Optional<P> dataPoint = findDataPointForProfile(functionalProfile.get(), dataPointName);
            return dataPoint.orElseThrow(() ->
                    new GenDriverException("Data-point with name " + dataPointName + " not found"));
        } else {
            throw new GenDriverException("Functional profile with name " + profileName + " not found");
        }
    }

    /**
     * Tests an array of SGr values against upper and lower bounds of a given data point. 
     * @param values the values to test
     * @param dataPoint the data point to test against
     * @throws GenDriverException when one or more values are out of bounds
     */
    public void checkOutOfRange(Value[] values, DataPointBase dataPoint)
        throws GenDriverException {

        DataPointDescription dp = dataPoint.getDataPoint();

        if (dp.getMaximumValue() != null) {
            checkOutOfRange(values, dp.getMaximumValue(), Comparator.MAX);
        }

        if (dp.getMinimumValue() != null) {
            checkOutOfRange(values, dp.getMinimumValue(), Comparator.MIN);
        }
    }

    /**
     * Tests a given data point's read or write permissions.
     * @param dataPoint the data point to test
     * @param direction the permission to test against
     * @throws GenDriverException on generic error
     */
    public void checkReadWritePermission(DataPointBase dataPoint, RwpDirections direction) throws GenDriverException {

        DataDirectionProduct dRWPType = dataPoint.getDataPoint().getDataDirection();
        if (!direction.opAllowedTypes.contains(dRWPType)) {
            throw new GenDriverException(String.format(
                    "Operation %s not allowed on data point %s",
                    direction.name(),
                    dataPoint.getDataPoint().getDataPointName()));
        }
    }

    /**
     * Checks if a value is beyond its valid range.
     * @param values the values to test
     * @param limit the bounds
     * @param comparator the comparator function
     * @return an empty instance
     * @throws GenDriverException if at least one value is out of range
     */
    protected Optional<String> checkOutOfRange(Value[] values, double limit, Comparator comparator) throws GenDriverException {

        List<Value> outOfRangeValues = Arrays.stream(values)
                        .filter(val -> comparator.getCmpFunc().test(val.getFloat64(), limit))
                        .collect(Collectors.toList());

        if (!outOfRangeValues.isEmpty()) {
            throw new GenDriverException(
                    String.format("Values %s out of range. %s value=%s", Arrays.toString(outOfRangeValues.toArray()), comparator.name(), limit));
        }
        return Optional.empty();
    }

    @Override
    public DeviceInfo getDeviceInfo() throws GenDriverException {

        var deviceWithInterface = DeviceWithInterface.of(device);

        var genericAttributes = Optional.ofNullable(device.getGenericAttributeList())
                .map(GenericAttributeListProduct::getGenericAttributeListElement)
                .map(genericAttributeProducts -> genericAttributeProducts.stream()
                        .map(GenericAttribute::of)
                        .collect(Collectors.toList()));

        var versionNo = device.getDeviceInformation().getVersionNumber();
        var versionStr = versionNo != null ? String.format("%s.%s.%s",
                versionNo.getPrimaryVersionNumber(), versionNo.getSecondaryVersionNumber(), versionNo.getSubReleaseVersionNumber()) : "-";

        return new DeviceInfo(
                device.getDeviceName(),
                device.getManufacturerName(),
                versionStr,
                device.getDeviceInformation().getSoftwareRevision(),
                device.getDeviceInformation().getHardwareRevision(),
                device.getDeviceInformation().getDeviceCategory(),
                deviceWithInterface.getInterfaceType(),
                device.getDeviceInformation().isIsLocalControl() ? OperationEnvironment.LOCAL : OperationEnvironment.CLOUD,
                genericAttributes.orElse(new ArrayList<>()),
                getDeviceConfigurationInfo(),
                getFunctionalProfiles());
    }

    @Override
    public List<ConfigurationValue> getDeviceConfigurationInfo() {
        return Optional.ofNullable(device.getConfigurationList())
                .map(ConfigurationList::getConfigurationListElement).orElseGet(()
                        -> new ConfigurationList().getConfigurationListElement())
                .stream()
                .map(this::mapToConfigurationValue).collect(Collectors.toList());
    }

    private ConfigurationValue mapToConfigurationValue(ConfigurationListElement configurationListElement) {

        var descriptions = new EnumMap<Language, String>(Language.class);
        configurationListElement.getConfigurationDescription().forEach(description -> descriptions.put(description.getLanguage(), description.getTextElement()));

        // handle special case of enum
        Value v = (configurationListElement.getDefaultValue() != null)
            ? (configurationListElement.getDataType().getEnum() != null)
                ? EnumValue.of(configurationListElement.getDefaultValue())
                : Value.fromString(configurationListElement.getDataType(), configurationListElement.getDefaultValue())
            : null;

        return new ConfigurationValue(
                configurationListElement.getName(),
                v,
                DataType.getDataTypeInfo(configurationListElement.getDataType()).orElse(null),
                descriptions);
    }

    @Override
    public List<FunctionalProfile> getFunctionalProfiles() throws GenDriverException {

        List<FunctionalProfile> functionalProfiles = new ArrayList<>();

        var deviceWithInterface = DeviceWithInterface.of(device);
        for (var functionalProfile : deviceWithInterface.getFunctionalProfiles()) {
            functionalProfiles.add(getFunctionalProfile(functionalProfile.getFunctionalProfile().getFunctionalProfileName()));
        }
        return functionalProfiles;
    }

    @Override
    public FunctionalProfile getFunctionalProfile(String functionalProfileName) throws GenDriverException {

        var functionalProfile = findProfile(functionalProfileName)
                .orElseThrow(() -> new GenDriverException("Functional profile with name='" + functionalProfileName + "' not found"));

        var fp = functionalProfile.getFunctionalProfile();

        var genericAttributes = Optional.ofNullable(functionalProfile.getGenericAttributeList())
                .map(GenericAttributeListProduct::getGenericAttributeListElement)
                .map(genericAttributeProducts -> genericAttributeProducts.stream()
                        .map(GenericAttribute::of)
                        .collect(Collectors.toList()));

        return new FunctionalProfile(
                fp.getFunctionalProfileName(),
                fp.getFunctionalProfileIdentification().getFunctionalProfileType(),
                fp.getFunctionalProfileIdentification().getFunctionalProfileCategory(),
                genericAttributes.orElse(List.of()),
                getDataPoints(functionalProfileName));
    }

    @Override
    public List<DataPoint> getDataPoints(String functionalProfileName) throws GenDriverException {

        var functionalProfile = findProfile(functionalProfileName)
                .orElseThrow(() -> new GenDriverException("Functional profile with name='" + functionalProfileName + "' not found"));

        List<DataPoint
                > result = new ArrayList<>();

        var dataPoints = FunctionalProfileWithDataPoints.of(functionalProfile).getDataPoints();
        if (dataPoints != null) {
            for (var dataPoint : dataPoints) {
                result.add(getDataPoint(functionalProfileName, dataPoint.getDataPoint().getDataPointName()));
            }
        }
        return result;
    }

    @Override
    public DataPoint getDataPoint(String functionalProfileName, String dataPointName) throws GenDriverException {

        var dataPointElem =  findDataPoint(functionalProfileName, dataPointName);
        var dataPoint = dataPointElem.getDataPoint();

        var genericAttributes = Optional.ofNullable(dataPointElem.getGenericAttributeList())
                .map(GenericAttributeListProduct::getGenericAttributeListElement)
                        .map(genericAttributeProducts -> genericAttributeProducts.stream()
                                .map(GenericAttribute::of)
                                .collect(Collectors.toList()));

        var dynReqParameterList = Optional.ofNullable(dataPointElem.getDataPoint().getParameterList())
                .map(list -> Optional.ofNullable(list.getParameterListElement()).orElse(List.of()))
                .orElse(List.of());

        return new DataPoint(
                dataPointName,
                functionalProfileName,
                DataType.getDataTypeInfo(dataPoint.getDataType()).orElse(null),
                dataPoint.getUnit() != null ? dataPoint.getUnit() : null,
                dataPoint.getDataDirection() != null ? dataPoint.getDataDirection() : null,
                dataPoint.getMinimumValue() != null ? dataPoint.getMinimumValue() : null,
                dataPoint.getMaximumValue() != null ? dataPoint.getMaximumValue() : null,
                dataPoint.getArrayLength() != null ? dataPoint.getArrayLength() : null,
                genericAttributes.orElse(List.of()),
                mapToDynamicRequestParameters(dynReqParameterList),
                this );
    }

    @Override
    public List<DataPointValue> getValues() throws GenDriverException {
        List<DataPointValue> dataPointValues = new ArrayList<>();
        getFunctionalProfiles().forEach(functionalProfile -> dataPointValues.addAll(functionalProfile.getValues()));
        return dataPointValues;
    }

    @Override
    public boolean canSubscribe() {
        return false;
    }

    @Override
    public void subscribe(String profileName, String dataPointName, Consumer<Either<Throwable, Value>> callbackFunction) throws GenDriverException {
        throw new GenDriverException("Subscribe not allowed");
    }

    @Override
    public void unsubscribe(String profileName, String dataPointName) throws GenDriverException {
        throw new GenDriverException("Unsubscribe not allowed");
    }

    /**
     * Performs conversion of units of measurements on a data point.
     * @param <P> the generic type of data point
     * @param dataPoint the data point instance
     * @param value the value to convert
     * @param conversionFunction the converter function
     * @return the converted value
     */
    protected static <P extends DataPointBase> Value applyUnitConversion(P dataPoint, Value value, DoubleBinaryOperator conversionFunction) {

		if (dataPoint.getDataPoint().getUnitConversionMultiplicator() != null
				&& isNumeric(value)
				&& dataPoint.getDataPoint().getUnitConversionMultiplicator() != 0.0) {
			return Float64Value.of(conversionFunction.applyAsDouble(value.getFloat64(), dataPoint.getDataPoint().getUnitConversionMultiplicator()));
		}
		return value;
	}

    /**
     * Tells if a value contains a number.
     * @param value the SGr value to test
     * @return a boolean
     */
	protected static boolean isNumeric(Value value) {
		if (value == null) {
			return false;
		}

		try {
			value.getFloat64();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    /**
     * Divides two numbers.
     * @param dividend the dividend
     * @param divisor the divisor
     * @return the division result
     */
	protected static double divide(double dividend, double divisor) {
		return  dividend / divisor;
	}

    /**
     * Multiplies two numbers.
     * @param factor1 the first factor
     * @param factor2 the second factor
     * @return the multiplication result
     */
	protected static double multiply(double factor1, double factor2) {
		return  factor1 * factor2;
	}

    private List<DynamicRequestParameter> mapToDynamicRequestParameters(List<DynamicParameterDescriptionListElement> reqParamDescriptions ) {

        List<DynamicRequestParameter> requestParameters = new LinkedList<>();

        reqParamDescriptions.forEach(reqParamDesc ->
                requestParameters.add(
                        new DynamicRequestParameter(
                                reqParamDesc.getName(),
                                reqParamDesc.getDefaultValue(),
                                DataType.getDataTypeInfo(reqParamDesc.getDataType()).orElse(null),
                                mapToReqParameterDescriptions(reqParamDesc.getParameterDescription()))));

        return requestParameters;
    }

    private Map<Language, InfoText> mapToReqParameterDescriptions(List<DynamicParameterDescription> parameterDescriptions) {
        var descriptions = new EnumMap<Language, InfoText>(Language.class);
        parameterDescriptions.forEach( paramDesc ->
                descriptions.put(paramDesc.getLanguage(),
                        new InfoText(paramDesc.getLabel(), paramDesc.getTextElement())));
        return descriptions;
    }
}
