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


public abstract class SGrDeviceBase<
        D extends DeviceFrame,
        F extends FunctionalProfileBase,
        P extends DataPointBase> implements GenDeviceApi {

    protected final D device;

    public enum RwpDirections {
        READ(Stream.of(DataDirectionProduct.R, DataDirectionProduct.RW, DataDirectionProduct.RWP, DataDirectionProduct.C).collect(Collectors.toSet())),
        WRITE(Stream.of(DataDirectionProduct.W, DataDirectionProduct.RW, DataDirectionProduct.RWP).collect(Collectors.toSet())),
        CONST(Collections.singleton(DataDirectionProduct.C));

        private final Set<DataDirectionProduct> opAllowedTypes;
        RwpDirections(Set<DataDirectionProduct> opAllowedTypes) {
            this.opAllowedTypes = opAllowedTypes;
        }
    }

    public enum Comparator {
        MIN(((val, lim) -> val.compareTo(lim) < 0)),
        MAX(((val, lim) -> val.compareTo(lim) > 0));

        private final BiPredicate<Double, Double> cmpFunc;

        Comparator(BiPredicate<Double,Double> cmpFunc) {
           this.cmpFunc = cmpFunc;
        }

        BiPredicate<Double,Double> getCmpFunc() {
            return cmpFunc;
        }
    }

    protected SGrDeviceBase(D device) {
        this.device = device;
    }

    protected abstract Optional<F> findProfile(String profileName);

    protected abstract Optional<P> findDataPointForProfile(F functionalProfile, String datapointName);

    protected P findDatapoint(String profileName, String datapointName) throws GenDriverException {
        Optional<F> functionalProfile = findProfile(profileName);
        if (functionalProfile.isPresent()) {
            Optional<P> dataPoint = findDataPointForProfile(functionalProfile.get(), datapointName);
            return dataPoint.orElseThrow(() ->
                    new GenDriverException("Data-point with name " + datapointName + " not found"));
        } else {
            throw new GenDriverException("Functional profile with name " + profileName + " not found");
        }
    }

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

    public void checkReadWritePermission(DataPointBase dataPoint, RwpDirections direction) throws GenDriverException {

        DataDirectionProduct dRWPType = dataPoint.getDataPoint().getDataDirection();
        if (!direction.opAllowedTypes.contains(dRWPType)) {
            throw new GenDriverException(String.format(
                    "Operation %s not allowed on datapoint %s",
                    direction.name(),
                    dataPoint.getDataPoint().getDataPointName()));
        }
    }

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

        var dataPoints = FunctionalProfileWithDatapoints.of(functionalProfile).getDataPoints();
        if (dataPoints != null) {
            for (var dataPoint : dataPoints) {
                result.add(getDataPoint(functionalProfileName, dataPoint.getDataPoint().getDataPointName()));
            }
        }
        return result;
    }

    @Override
    public DataPoint getDataPoint(String functionalProfileName, String dataPointName) throws GenDriverException {

        var dataPointElem =  findDatapoint(functionalProfileName, dataPointName);
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

    protected static <P extends DataPointBase> Value applyUnitConversion(P dataPoint, Value value, DoubleBinaryOperator conversionFunction) {

		if (dataPoint.getDataPoint().getUnitConversionMultiplicator() != null
				&& isNumeric(value)
				&& dataPoint.getDataPoint().getUnitConversionMultiplicator() != 0.0) {
			return Float64Value.of(conversionFunction.applyAsDouble(value.getFloat64(), dataPoint.getDataPoint().getUnitConversionMultiplicator()));
		}
		return value;
	}

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

	protected static double divide(double dividend, double divisor) {
		return  dividend / divisor;
	}

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
