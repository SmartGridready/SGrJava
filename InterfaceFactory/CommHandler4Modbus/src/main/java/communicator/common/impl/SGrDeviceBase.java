package communicator.common.impl;

import com.smartgridready.ns.v0.DataDirectionProduct;
import com.smartgridready.ns.v0.DataPointBase;
import com.smartgridready.ns.v0.DataPointDescription;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.FunctionalProfileBase;
import communicator.common.api.GenDeviceApi;
import communicator.common.api.GenericAtributeLevel;
import communicator.common.api.GenericAttribute;
import communicator.common.api.Value;
import communicator.common.runtime.GenDriverException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SGrDeviceBase<
        D extends DeviceFrame,
        F extends FunctionalProfileBase,
        P extends DataPointBase> implements GenDeviceApi {

    protected final D device;

    public enum RwpDirections {
        READ(Stream.of(DataDirectionProduct.R, DataDirectionProduct.RW, DataDirectionProduct.RWP).collect(Collectors.toSet())),
        WRITE(Stream.of(DataDirectionProduct.W, DataDirectionProduct.RW, DataDirectionProduct.RWP).collect(Collectors.toSet()));

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

    @Override
    public List<GenericAttribute> getGenAttributes(String profileName, String dataPointName) throws GenDriverException {

        List<GenericAttribute> result = new ArrayList<>();

        // Add additional attributes that are set on device level
        Optional.ofNullable(device.getGenericAttributeList()).flatMap(attrList ->
                Optional.ofNullable(attrList.getGenericAttributeListElement())).ifPresent(attrListElem ->
                attrListElem.forEach(attr ->
                        result.add(GenericAttribute.of(attr, GenericAtributeLevel.DEVICE))));

        // Add additional attributes that are set on functional profile level
        if (profileName!=null) {
            findProfile(profileName).flatMap(profile -> Optional.ofNullable(profile.getGenericAttributeList()))
                    .flatMap(attrList -> Optional.ofNullable(attrList.getGenericAttributeListElement())).ifPresent(attrListElem -> attrListElem.forEach(attr ->
                            result.add(GenericAttribute.of(attr, GenericAtributeLevel.FUNCTIONAL_PROFILE))));
        }

        // Attributes that are set on device level
        if (profileName!=null && dataPointName!=null) {
            Optional.ofNullable(findDatapoint(profileName, dataPointName))
                    .flatMap(dataPoint -> Optional.ofNullable(dataPoint.getGenericAttributeList())).flatMap(attrList -> Optional.ofNullable(attrList.getGenericAttributeListElement())).ifPresent(attrListElem -> attrListElem.forEach(attr ->
                            result.add(GenericAttribute.of(attr, GenericAtributeLevel.DATA_POINT))));
        }

        return result;
    }

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

        if (dp.isSetMaximumValue()) {
            checkOutOfRange(values, dp.getMaximumValue(), Comparator.MAX);
        }

        if (dp.isSetMinimumValue()) {
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



}
