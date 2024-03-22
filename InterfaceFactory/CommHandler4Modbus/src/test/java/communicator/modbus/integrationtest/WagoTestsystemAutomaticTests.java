package communicator.modbus.integrationtest;

import communicator.common.api.BitmapValue;
import communicator.common.api.BooleanValue;
import communicator.common.api.Float64Value;
import communicator.common.api.Value;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WagoTestsystemAutomaticTests extends TestDevice {

    private static final Logger LOG = LoggerFactory.getLogger(WagoTestsystemAutomaticTests.class);

    private static final int MAX_VOLTAGE = 24;
    private static final int MAX_RESOLUTION_INT_16 = 32767;
    private static final int AD_CONV_FAULT_TOLERANCE_INT_16 = 60;
    private static final float AD_CONV_FAULT_TOLERANCE_24V =  (float)AD_CONV_FAULT_TOLERANCE_INT_16 * MAX_VOLTAGE / MAX_RESOLUTION_INT_16;
    private static final int AD_DATAPOINT_MAX_INDEX = 8;

    @BeforeAll
    public void beforeAll() throws Exception {
        init();
    }

    @Test
    void analogInputOutput() throws Exception {

        // Check Connector2 - Connector8
        for (int i=1; i<=AD_DATAPOINT_MAX_INDEX; i++) {
            checkAnalogDatapoint(i, WagoTestsystemAutomaticTests::assertFloatValues);
        }
    }

    @Test
    void digitalRegisterIO() throws Exception {

        for (int i = 1; i <= 8; i++) {

            String relais = String.format("Relais_%d", i);
            String sensor = String.format("Sensor_%d", i);
            Map<String, Boolean> bitMap = new HashMap<>();
            bitMap.put(relais, true);
            BitmapValue bitmapValue = BitmapValue.of(bitMap);
            getTestSystem().setVal("DigitalRegister_M2_OUT_1", "Register", bitmapValue);

            // check the digital IN sensor (relais output shortcut to sensor input)
            Value res = getTestSystem().getVal("DigitalRegister_M2_IN_2", "Register");
            assertEquals(bitmapValue.getBitmap().get(relais), res.getBitmap().get(sensor), "Check sensor=" + sensor);

            res = getTestSystem().getVal("DigitalRegister_M2_OUT_1", "Register");
            assertEquals(bitmapValue.getBitmap().get(relais), res.getBitmap().get(relais), "Check relais=" + relais);


            // Read bit as discrete input
            String fpName = String.format("DigitalDiscrete_M2_IN_%d", i);
            res = getTestSystem().getVal(fpName, "Sensor");
            assertTrue(res.getBoolean(), "Check functional profile=" + fpName);

            // Reset all bits to false
            getTestSystem().setVal("DigitalRegister_M2_OUT_1", "Register", BitmapValue.of(new HashMap<>()));

            // Read bit as discrete input
            res = getTestSystem().getVal(fpName, "Sensor");
            assertFalse(res.getBoolean());
        }
    }

    @Test
    void digitalDiscreteIO() throws Exception {

        for (int i = 1; i <= 8; i++) {
            checkDiscreteIO(i, true);
        }

        for (int i = 1; i <= 8; i++) {
            checkDiscreteIO(i, false);
        }
    }

    @Test
    void readout_EI_XML() {

        List<DataPointDescriptor> dps = getDataPoints();
        assertFalse(dps.isEmpty());
        dps.forEach(System.out::println);
    }

    void checkAnalogDatapoint(int dataPointIndex, TriConsumer<String, Value, Value> assertFunction)
            throws Exception {

        for (int i = 0; i <= 48; i++) {

            String functionalProfileNameOut = String.format("VoltageDC_OUT_%d", dataPointIndex);
            String functionalProfileNameIn = String.format("VoltageDC_IN_%d", dataPointIndex);

            Float64Value voltConn1Out = Float64Value.of(i*0.5f);
            getTestSystem().setVal(functionalProfileNameOut, "VoltageDC", voltConn1Out);

            Value voltConn1In = getTestSystem().getVal(functionalProfileNameIn, "VoltageDC");
            assertFunction.accept(functionalProfileNameIn, voltConn1Out, voltConn1In);
        }
    }

    private static void assertFloatValues(String dataPointName, Value outValue, Value inValue) {

        float fOutValue = outValue.getFloat32();
        float fInValue =  inValue.getFloat32();

        LOG.info(String.format("DC voltage %s: set: %.6f \t-> get: %.6f", dataPointName, fOutValue, fInValue));
        String errorMessage = String.format(
                "%s: ABS(VoltageIN - VoltageOUT) > FAULT_TOLERANCE=%.6f, Vout=%.6f Vin=%.6f diff=%.6f",
                dataPointName,
                AD_CONV_FAULT_TOLERANCE_24V,
                fOutValue,
                fInValue,
                Math.abs(fInValue - fOutValue));

        assertTrue(Math.abs(fInValue - fOutValue) <= AD_CONV_FAULT_TOLERANCE_24V, errorMessage);
    }

    private void checkDiscreteIO(int i, boolean value) throws Exception {
        String sensor = String.format("DigitalDiscrete_M1_IN_%d", i);
        String relais = String.format("DigitalDiscrete_M1_OUT_%d", i);
        getTestSystem().setVal(relais, "Relais", BooleanValue.of(value));
        Value res = getTestSystem().getVal(sensor, "Sensor");
        assertEquals(value, res.getBoolean(), "Relais=" + relais + " Sensor=" + sensor);
    }
}
