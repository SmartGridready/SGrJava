/**
Copyright(c) 2021 Verein SmartGridready Switzerland
 
This Open Source Software is BSD 3 clause licensed:
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in 
   the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from 
   this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.

This Module includes automatically generated code, generated from SmartGridready Modus XML Schema definitions
check for "EI-Modbus" and "Generic" directories in our Namespace http://www.smartgridready.ch/ns/SGr/V0/

*/
package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.communicator.common.api.values.ArrayValue;
import com.smartgridready.ns.v0.BitOrder;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.ModbusDataPoint;
import com.smartgridready.ns.v0.ModbusDataPointConfiguration;
import com.smartgridready.ns.v0.ModbusDataType;
import com.smartgridready.ns.v0.ModbusFunctionalProfile;
import com.smartgridready.ns.v0.ModbusInterface;
import com.smartgridready.ns.v0.ModbusInterfaceDescription;
import com.smartgridready.ns.v0.ModbusLayer6Deviation;
import com.smartgridready.ns.v0.RegisterType;
import com.smartgridready.ns.v0.ScalingFactor;
import com.smartgridready.ns.v0.TimeSyncBlockNotification;
import com.smartgridready.communicator.common.api.values.BitmapValue;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Int64UValue;
import com.smartgridready.communicator.common.api.values.NumberValue;
import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.impl.SGrDeviceBase;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.communicator.modbus.api.GenDeviceApi4Modbus;
import com.smartgridready.communicator.modbus.api.ModbusGatewayRegistry;
import com.smartgridready.communicator.modbus.api.ModbusGateway;
import com.smartgridready.communicator.modbus.helper.CacheRecord;
import com.smartgridready.communicator.modbus.helper.ConversionHelper;
import com.smartgridready.communicator.modbus.helper.EndiannessConversionHelper;
import com.smartgridready.communicator.modbus.helper.ModbusReader;
import com.smartgridready.communicator.modbus.helper.ModbusReaderResponse;
import com.smartgridready.communicator.modbus.helper.ModbusTransportUtil;
import com.smartgridready.communicator.modbus.helper.ModbusType;
import com.smartgridready.communicator.modbus.helper.ModbusUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import jakarta.annotation.PreDestroy;

import static com.smartgridready.communicator.common.impl.SGrDeviceBase.RwpDirections.READ;

/**
 * 
 * @author furrer / IBT,cb
 *
 */
public class SGrModbusDevice extends SGrDeviceBase<DeviceFrame, ModbusFunctionalProfile, ModbusDataPoint>
		implements GenDeviceApi4Modbus {
	
	private static final Logger LOG = LoggerFactory.getLogger(SGrModbusDevice.class);

	private final String devObjKey;
	private final ModbusGatewayRegistry drvRegistry;
	private final ModbusGateway drv4ModbusGateway;

	private final DeviceFrame myDeviceDescription;

	private final Map<TimeSyncBlockNotification, CacheRecord<ModbusReaderResponse>> myTimeSyncBlockReadCache = new HashMap<>();

	/**
	 * Construct with pre-defined shared Modbus gateway registry.
	 * @param aDeviceDescription the EID description
	 * @param aDriverFactory the Modbus gateway factory
	 * @param aGatewayRegistry the shared Modbus gateway registry
	 * @throws GenDriverException when gateway cannot be created
	 */
	public SGrModbusDevice(
		DeviceFrame aDeviceDescription,
		GenDriverAPI4ModbusFactory aDriverFactory,
		ModbusGatewayRegistry aGatewayRegistry
	) throws GenDriverException {
		super(aDeviceDescription);
		myDeviceDescription = aDeviceDescription;
		devObjKey = UUID.randomUUID().toString();

		if (
			(aGatewayRegistry != null) &&
			(ModbusUtil.getModbusType(getModbusInterfaceDescription()) == ModbusType.RTU ||
				ModbusUtil.getModbusType(getModbusInterfaceDescription()) == ModbusType.RTU_ASCII)
		) {
			// only use shared registry with RTU transports
			drvRegistry = aGatewayRegistry;
			drv4ModbusGateway = drvRegistry.attachGateway(getModbusInterfaceDescription(), aDriverFactory, devObjKey);
		} else {
			drvRegistry = null;
			drv4ModbusGateway = new ModbusGateway(
				ModbusUtil.getModbusGatewayIdentifier(getModbusInterfaceDescription()),
				getModbusInterfaceDescription(),
				ModbusTransportUtil.createTransport(getModbusInterfaceDescription(), aDriverFactory)
			);
		}
	}

	/**
	 * Construct with custom Modbus gateway factory.
	 * @param aDeviceDescription the EID description
	 * @param aDriverFactory the Modbus gateway factory
	 * @throws GenDriverException when gateway cannot be created
	 */
	public SGrModbusDevice(
		DeviceFrame aDeviceDescription,
		GenDriverAPI4ModbusFactory aDriverFactory
	) throws GenDriverException {
		this(aDeviceDescription, aDriverFactory, null);
	}

	/**
	 * Construct with pre-defined transport (legacy method).
	 * Transport must be managed externally.
	 * @param aDeviceDescription the EID description
	 * @param aTransport the Modbus transport
	 */
	public SGrModbusDevice(DeviceFrame aDeviceDescription, GenDriverAPI4Modbus aTransport) {
		super(aDeviceDescription);
		myDeviceDescription = aDeviceDescription;
		drvRegistry = null;
		devObjKey = UUID.randomUUID().toString();
		drv4ModbusGateway = new ModbusGateway(
			"",
			getModbusInterfaceDescription(),
			aTransport
		);
	}

	@PreDestroy
	private void onDestroy() {
		try {
			// remove shared transport if object is destroyed before disconnect() was called
			if (drvRegistry != null) {
				drvRegistry.detachGateway(
					getModbusInterfaceDescription(),
					devObjKey
				);
			}
			disconnect();
		} catch (GenDriverException e) {}
	}

	@Override
	public void connect() throws GenDriverException {
		drv4ModbusGateway.connect(devObjKey);
	}

	@Override
	public void disconnect() throws GenDriverException {
		drv4ModbusGateway.disconnect(devObjKey);
	}

	@Override
	public boolean isConnected() {
		return drv4ModbusGateway.isConnected(devObjKey);
	}

	@Override
	public Value getVal(String sProfileName, String sDataPointName)
			throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
		Optional<ModbusFunctionalProfile> profile = findProfile(sProfileName);

		if (profile.isPresent()) {
			Optional<ModbusDataPoint> dataPoint = findDataPointForProfile(profile.get(), sDataPointName);
			if (dataPoint.isPresent()) {
				return getVal(dataPoint.get());
			}
		}
		throw new GenDriverException(String.format("Functional profile '%s' / datapoint '%s' not found.", sProfileName, sDataPointName));
	}

	@Override
    public Value getVal(String sProfileName, String sDataPointName, Properties parameters)
			throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
        // parameters not supported, just return getVal()
        return getVal(sProfileName, sDataPointName);
    }

	// Read a single value
	private Value getVal(ModbusDataPoint aDataPoint)
			throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
		
		if (aDataPoint.getBlockCacheIdentification() != null) {
			return getBlockVal(aDataPoint);
		}

		if (aDataPoint.getDataPoint().getArrayLength() != null) {
			int arrLen = aDataPoint.getDataPoint().getArrayLength();
			return ArrayValue.of(getValArr(aDataPoint, arrLen));
		} else {
			return getValArr(aDataPoint, 1)[0];
		}
	}
	
	private Value getBlockVal(ModbusDataPoint aDataPoint)
			throws GenDriverException, GenDriverSocketException, GenDriverModbusException {

			// Check read/write permission
			checkReadWritePermission(aDataPoint, READ);

			Optional<TimeSyncBlockNotification> blockNotificationTypeOpt = findTimeSyncBlockNotificationType(aDataPoint.getBlockCacheIdentification());
			if (!blockNotificationTypeOpt.isPresent()) {
				throw new GenDriverException("Could not find timeSyncBlockNotification entry with name=" + aDataPoint.getBlockCacheIdentification());
			}
			
			TimeSyncBlockNotification blockNotificationType = blockNotificationTypeOpt.get();
			BigInteger blockAddress = blockNotificationType.getFirstAddress();
		
			BigInteger mbRegRef = aDataPoint.getModbusDataPointConfiguration().getAddress();
			BigInteger addrDiff = mbRegRef.subtract(blockAddress);
			if (addrDiff.signum() < 0) {
				throw new GenDriverException("Error in EI-XML, datapoint address must be >= timeSyncBlock address");
			}
			
			ModbusInterfaceDescription modbusInterfaceDesc = myDeviceDescription.getInterfaceList().getModbusInterface().getModbusInterfaceDescription();
					
			CacheRecord<ModbusReaderResponse> mbCacheRecord = myTimeSyncBlockReadCache.get(blockNotificationType);
			ModbusReaderResponse mbResponse;
			if (mbCacheRecord == null || mbCacheRecord.isExpired(blockNotificationType.getTimeToLiveMs())) {
				LOG.debug("Reading time sync block from modbus device");
				short unitIdentifier = ModbusUtil.getModbusSlaveId(modbusInterfaceDesc);

				checkConnection();

				mbResponse = ModbusReader.read(
						drv4ModbusGateway.getTransport(),
						unitIdentifier,
						blockNotificationType.getRegisterType(),
						blockAddress.intValue(),
						modbusInterfaceDesc.isFirstRegisterAddressIsOne(),
						blockNotificationType.getSize());
				
				if (blockNotificationType.getTimeToLiveMs() != 0) {
					myTimeSyncBlockReadCache.put(blockNotificationType, new CacheRecord<>(mbResponse, Instant.now()));
				}
			} else {
				LOG.debug("Reading time sync block from cache.");
				mbResponse = mbCacheRecord.getValue();
			}			

			// pick the correct value from the received block			
			int size = aDataPoint.getModbusDataPointConfiguration().getNumberOfRegisters();
			int[] mbRegResp = mbResponse.getMbregresp(addrDiff.intValue(), size);
			boolean[] mbBitResp = mbResponse.getMbbitresp(addrDiff.intValue(), size);


			// do conversion of the read data
			return doReadConversion(
					aDataPoint,
					modbusInterfaceDesc, 
					mbRegResp, // response register int[]
					mbBitResp, // response bits[]
					mbResponse.isbGotRegisters(),
					size,		// number of bytes read
					0);			// array index/offset
	}

	// Read an array of values
	private Value[] getValArr(
		ModbusDataPoint aDataPoint, int arrayLen)
		throws GenDriverException, GenDriverSocketException, GenDriverModbusException {

		// Check if read is allowed
		checkReadWritePermission(aDataPoint, READ);

		ModbusInterfaceDescription modbusInterfaceDesc = getModbusInterfaceDescription();
		ModbusDataPointConfiguration mbRegRef = aDataPoint.getModbusDataPointConfiguration();

		boolean bMBfirstRegOne = modbusInterfaceDesc.isFirstRegisterAddressIsOne();

		LOG.debug("Reading value from modbus device.");

		int size = aDataPoint.getModbusDataPointConfiguration().getNumberOfRegisters();
		short unitIdentifier = ModbusUtil.getModbusSlaveId(modbusInterfaceDesc);

		checkConnection();

		ModbusReaderResponse mbResponse = ModbusReader.read(
				drv4ModbusGateway.getTransport(),
				unitIdentifier,
				mbRegRef.getRegisterType(),
				mbRegRef.getAddress().intValue(),
				bMBfirstRegOne,
				size * arrayLen);

		// modbus OSI Layer 6 to generic OSI layer 6 conversion
		List<Value> resultList = new ArrayList<>();
		for (int arrIdx = 0; arrIdx < arrayLen; arrIdx++) {
			resultList.add(
				doReadConversion(
					aDataPoint,
					modbusInterfaceDesc,
					mbResponse.getMbregresp(), // response register int[]
					mbResponse.getMbbitresp(), // response bits[]
					mbResponse.isbGotRegisters(),
					size,
					arrIdx));
		}

		return resultList.toArray(new Value[0]);
	}

	private Value doReadConversion(ModbusDataPoint aDataPoint,
								   ModbusInterfaceDescription modbusInterfaceDesc,
								   final int[] mbregrespSrc,
								   final boolean[] mbbitrespSrc,
								   boolean bGotRegisters,
								   int size, int arrOffset) throws GenDriverException {

		int mul = 1;
		int l6dev;
		int pwof10 = 0;
		// Register return value calculation

		int[] mbregresp = Arrays.copyOfRange(mbregrespSrc, arrOffset*size, (arrOffset+1)*size);
		boolean[] mbbitresp = Arrays.copyOfRange(mbbitrespSrc, arrOffset*size, (arrOffset+1)*size);

		BitOrder bitOrder = modbusInterfaceDesc.getBitOrder();

		Value retVal;
		if (bGotRegisters) {

			retVal = convertFromRegisters(aDataPoint, size, mul, pwof10, mbregresp, bitOrder);
		} else {
			ModbusDataType mbType  = aDataPoint.getModbusDataPointConfiguration().getModbusDataType();
			retVal = Value.fromDiscreteInput(mbType, mbbitresp);
		}
		return retVal;				
	}

	private Value convertFromRegisters(ModbusDataPoint aDataPoint,
									   int size,
									   int mul,
									   int pwof10,
									   int[] mbregresp,
									   BitOrder bitOrder) throws GenDriverException {

		Value retVal;
		ModbusLayer6Deviation l6dev;

		if (bitOrder!=BitOrder.BIG_ENDIAN) {
			mbregresp = convertEndians(bitOrder, mbregresp, size);
		}

		if (aDataPoint.getModbusAttributes()!=null) {   // there are Modbus attributes available

			// TODO unit-test this code
			if ((aDataPoint.getModbusAttributes().getSunssf() == null)
					&& (aDataPoint.getModbusAttributes().getScalingFactor() != null)) {
				ScalingFactor attrScaling = aDataPoint.getModbusAttributes().getScalingFactor();
				mul = attrScaling.getMultiplicator();
				pwof10 = attrScaling.getPowerof10();
			}

			if (aDataPoint.getModbusAttributes().getLayer6Deviation() != null) {   // do we have Layer 6 deviations ?
				l6dev = aDataPoint.getModbusAttributes().getLayer6Deviation();
				mbregresp = manageLayer6deviation(l6dev, mbregresp, size);
			}
		}

		ModbusDataType mbType  = aDataPoint.getModbusDataPointConfiguration().getModbusDataType();
		DataTypeProduct genType = aDataPoint.getDataPoint().getDataType();
		// Most significant int as returned from modbus can have the wrong sign:
		// - after change byte order
		// - if the modbus value is an unsigned number and MSB is set
		mbregresp[0] = adjustSign(mbType, mbregresp[0]);

		retVal = Value.fromModbusRegister(mbType, mbregresp);
		if (genType.getEnum()!=null) {
			retVal = EnumValue.of(retVal.getInt64(), genType.getEnum());
		} else if (genType.getBitmap()!=null) {
			retVal = BitmapValue.of(mbregresp, genType.getBitmap());
		} else if (retVal instanceof NumberValue) {
			retVal = ((NumberValue<?>) retVal).scaleUp(mul, pwof10);
		} else if (retVal instanceof Int64UValue) {
			retVal = ((Int64UValue) retVal).scaleUp(mul, pwof10);
		} else if (retVal instanceof StringValue) {
			((StringValue) retVal).scaleUp(mul, pwof10);
		}

		float unitConversionFactor = getUnitConversionFactor(aDataPoint);
		if (unitConversionFactor != 1.0f) {
			retVal = Float64Value.of(retVal.getFloat64() * unitConversionFactor);
		}

		return retVal;
	}

	private int[] manageLayer6deviation(ModbusLayer6Deviation mBlayer6Scheme, int[] mbregresp, int size) throws GenDriverException {
		
		int[] mbregconv = new int[10];
		long lv;

		mbregconv[0] = 0;

		switch (mBlayer6Scheme) {
			case VALUE_2:	// H2L
				if (size==2) {
					lv = ((long) mbregresp[0]) * 1000;
					lv = lv + mbregresp[1];
					mbregconv[1] = (int) (lv & 0xffff);
					mbregconv[0] = (int) ((lv >> 16) & 0xffff);
					mbregresp = mbregconv;
				}
				break;
			case VALUE_1:	// L2H
				if (size==2) {
					lv = ((long) mbregresp[1]) * 1000;
					lv = lv + mbregresp[0];
					mbregconv[1] = (int) (lv & 0xffff);
					mbregconv[0] = (int) ((lv >> 16) & 0xffff);
					mbregresp = mbregconv;
				}
				break;
			default:
				throw new GenDriverException(String.format("Unhandled layer6deviation: conversionScheme=%s", mBlayer6Scheme.name()));
		}
		return mbregresp;
	}

	private int[] convertEndians(BitOrder mBconvScheme, int[] mbregresp, int size) throws GenDriverException{

		try {
			switch (mBconvScheme) {
				case CHANGE_BIT_ORDER:
					throw new GenDriverException("CHANGE_BIT_ORDER is not supported yet. Check EID-XML.");
				case CHANGE_BYTE_ORDER:
					return EndiannessConversionHelper.changeByteOrder(mbregresp, size);
				case CHANGE_WORD_ORDER:
					return EndiannessConversionHelper.changeWordOrder(mbregresp, size);
				case CHANGE_D_WORD_ORDER:
					return EndiannessConversionHelper.changeDWordOrder(mbregresp, size);
				default:
					throw new GenDriverException("Unsupported ByteOrder value. Unable to convert endianness.");

			}
		} catch (IllegalArgumentException e1) {
			LOG.error("***IllegalArgumentException: {}", e1.toString());
			return mbregresp;
		}
	}

	@Override
	public void setVal(String sProfileName, String sDataPointName, Value value)
			throws GenDriverException, GenDriverSocketException, GenDriverModbusException {

		ModbusDataPoint dataPoint = findDatapoint(sProfileName, sDataPointName);

		if (value instanceof ArrayValue) {
			validateArrayLength(dataPoint, value);
		}

		setValArr(dataPoint, value.asArray());
	}

	private void setValArr(
			ModbusDataPoint aDataPoint,
			Value[] sgrValues) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {


		int[] mbregsnd;
		boolean[] mbbitsnd;
		boolean bRegisterCMDs = false;
		boolean bDiscreteCMDs = false;
		boolean isSetAccessProt = false;
		int mul = 1;
		ModbusLayer6Deviation l6dev;
		int powof10 = 0;

		ModbusInterfaceDescription modbusInterfaceDesc = getModbusInterfaceDescription();

		// Handle generic attributes:
		// Data Direction ctrl
		checkReadWritePermission(aDataPoint, RwpDirections.WRITE);

		// Value range check
		checkOutOfRange(sgrValues, aDataPoint);

		if (aDataPoint.getModbusAttributes() != null) { // there are Modbus attributes available
			if ((aDataPoint.getModbusAttributes().getSunssf() == null)
					&& (aDataPoint.getModbusAttributes().getScalingFactor() != null)) {
				ScalingFactor attrScaling = aDataPoint.getModbusAttributes().getScalingFactor();
				mul = attrScaling.getMultiplicator();
				powof10 = attrScaling.getPowerof10();
			}
			if (aDataPoint.getModbusAttributes().getLayer6Deviation() != null) {
				// for future implementations
				l6dev = aDataPoint.getModbusAttributes().getLayer6Deviation();
			}
			if (aDataPoint.getModbusAttributes().getAccessProtection() != null) {
				isSetAccessProt = true; // for future use
			}
		}


		ModbusDataPointConfiguration modbusDataPointConfiguration = aDataPoint.getModbusDataPointConfiguration();
		BigInteger regad = modbusDataPointConfiguration.getAddress();

		boolean bMBfirstRegOne = modbusInterfaceDesc.isFirstRegisterAddressIsOne();
		if (bMBfirstRegOne) {
			regad = regad.subtract(BigInteger.ONE);
		}

		int mbsize = aDataPoint.getModbusDataPointConfiguration().getNumberOfRegisters();

		// fill values & booleans, apply conversion scheme
		if (modbusDataPointConfiguration.getRegisterType()==RegisterType.HOLD_REGISTER)
			bRegisterCMDs = true;
		if (modbusDataPointConfiguration.getRegisterType()==RegisterType.COIL)
			bDiscreteCMDs = true;

		IntBuffer mbRegBuf = IntBuffer.allocate(1024);
		ByteBuffer mbByteBuf = ByteBuffer.allocate(1024);

		for (Value sgrValue : sgrValues) {
			doWriteConversion(
					mbRegBuf,
					mbByteBuf,
					sgrValue,
					bRegisterCMDs,
					mul,
					powof10,
					aDataPoint);
		}

		int nrRegisters = aDataPoint.getModbusDataPointConfiguration().getNumberOfRegisters();
		mbregsnd = Arrays.copyOfRange(mbRegBuf.array(), 0, nrRegisters * sgrValues.length);
		mbbitsnd = Arrays.copyOfRange(ConversionHelper.byteArrToBooleanArr(mbByteBuf.array()), 0, nrRegisters * sgrValues.length);

		checkConnection();
		
		short unitIdentifier = ModbusUtil.getModbusSlaveId(modbusInterfaceDesc);

		writeToModbus(unitIdentifier, mbregsnd, mbbitsnd, bRegisterCMDs, bDiscreteCMDs, regad, mbsize);
	}

	private void writeToModbus(short unitIdentifier, int[] mbregsnd, boolean[] mbbitsnd, boolean bRegisterCMDs, boolean bDiscreteCMDs, BigInteger regad, int mbsize) throws GenDriverException, GenDriverSocketException, GenDriverModbusException {
		GenDriverAPI4Modbus drv4Modbus = drv4ModbusGateway.getTransport();
		if (bRegisterCMDs) {
			if (mbsize > 1) {
				drv4Modbus.writeMultipleRegisters(unitIdentifier, regad.intValue(), mbregsnd);
			} else {
				drv4Modbus.writeSingleRegister(unitIdentifier, regad.intValue(), mbregsnd[0]);
			}
		} else if (bDiscreteCMDs) {
			if (mbsize > 1) {
				drv4Modbus.writeMultipleCoils(unitIdentifier, regad.intValue(), mbbitsnd);
			} else {
				drv4Modbus.writeSingleCoil(unitIdentifier, regad.intValue(), mbbitsnd[0]);
			}
		}
	}

	private void doWriteConversion(
			IntBuffer   mbRegBufRes,
			ByteBuffer 	mbByteBuf,
			Value sgrValue,
			boolean bRegisterCMDs,
			int mul,
			int powof10,
			ModbusDataPoint dataPoint) throws GenDriverException {

		// Data format adaption
		ModbusDataType dMBType  = dataPoint.getModbusDataPointConfiguration().getModbusDataType();
		DataTypeProduct genType = dataPoint.getDataPoint().getDataType();
		int mbsize = dataPoint.getModbusDataPointConfiguration().getNumberOfRegisters();
		BitOrder bitOrder = getModbusInterfaceDescription().getBitOrder();
		IntBuffer mbRegBuf = IntBuffer.allocate(32);

		float unitConversionFactor = getUnitConversionFactor(dataPoint);
		if (unitConversionFactor != 1.0f) {
			sgrValue = Float64Value.of(sgrValue.getFloat64() / unitConversionFactor);
		}

		if (bRegisterCMDs) {
			if (sgrValue instanceof EnumValue) {
				sgrValue = sgrValue.enumToOrdinalValue(genType.getEnum());
				mbRegBuf.put(sgrValue.toModbusRegister(dMBType));
			} else if (sgrValue instanceof BitmapValue) {
				mbRegBuf.put(((BitmapValue) sgrValue).toModbusRegisters(mbsize, genType.getBitmap()));
			} else if(sgrValue instanceof NumberValue) {
				sgrValue = ((NumberValue<?>) sgrValue).scaleDown(mul, powof10);
				mbRegBuf.put(sgrValue.toModbusRegister(dMBType));
			} else if (sgrValue instanceof Int64UValue) {
				sgrValue = ((Int64UValue) sgrValue).scaleDown(mul, powof10);
				mbRegBuf.put(sgrValue.toModbusRegister(dMBType));
			} else if (sgrValue instanceof StringValue) {
				((StringValue) sgrValue).scaleDown(mul, powof10);
				mbRegBuf.put(sgrValue.toModbusRegister(dMBType));
			} else {
				mbRegBuf.put(sgrValue.toModbusRegister(dMBType));
			}
		} else {
			mbByteBuf.put(sgrValue.toModbusDiscreteVal(dMBType)[0]);
		}

		if (bitOrder != BitOrder.BIG_ENDIAN) {
			if (bRegisterCMDs) {
				int[] convStream = convertEndians(bitOrder, mbRegBuf.array(), mbsize);
				mbRegBufRes.put(Arrays.copyOfRange(convStream, 0, mbsize));
			}
			else {
				// TODO: add discrete data type management
			}
		} else {
			mbRegBufRes.put(Arrays.copyOfRange(mbRegBuf.array(), 0, mbsize));
		}
	}

	private Optional<TimeSyncBlockNotification> findTimeSyncBlockNotificationType(String aName) {
		return myDeviceDescription.getInterfaceList().getModbusInterface().getTimeSyncBlockNotification().stream()
				.filter(tSyncBlock -> tSyncBlock.getBlockCacheIdentification().equals(aName)).findFirst();
	}

	private static int adjustSign(ModbusDataType type, int register) {
		if ( (register & 0x8000) != 0) {
			if (isUnsignedType(type)) {
				return register & 0x0000FFFF;
			} else {
				return register | 0xFFFF0000;
			}
		}
		return register;
	}

	private static boolean isUnsignedType(ModbusDataType dataPointType) {
		return  (dataPointType.getInt64U() != null) ||
				(dataPointType.getInt32U() != null) ||
				(dataPointType.getInt16U() != null) ||
				(dataPointType.getInt8U()  != null);
	}

	@Override
	protected Optional<ModbusFunctionalProfile> findProfile(String aProfileName) {
		return myDeviceDescription.getInterfaceList().getModbusInterface().getFunctionalProfileList().getFunctionalProfileListElement().stream()
				.filter(modbusProfileFrame -> modbusProfileFrame.getFunctionalProfile().getFunctionalProfileName().equals(aProfileName))
				.findFirst();
	}

	protected Optional<ModbusDataPoint> findDataPointForProfile(ModbusFunctionalProfile aProfile,
																	 String aDataPointName) {
		return aProfile.getDataPointList().getDataPointListElement().stream()
				.filter(datapoint -> datapoint.getDataPoint().getDataPointName().equals(aDataPointName))
				.findFirst();
	}

	private ModbusInterface getModbusInterface() {
		return myDeviceDescription.getInterfaceList().getModbusInterface();
	}

	private ModbusInterfaceDescription getModbusInterfaceDescription() {
		return getModbusInterface().getModbusInterfaceDescription();
	}

	private float getUnitConversionFactor(ModbusDataPoint aDataPoint) {
		if (aDataPoint.getDataPoint().getUnitConversionMultiplicator() != null) {
			return aDataPoint.getDataPoint().getUnitConversionMultiplicator();
		}
		return 1.0f;
	}

	private void checkConnection() throws GenDriverModbusException {
		if ((drvRegistry != null) && (drv4ModbusGateway == null)) {
			throw new GenDriverModbusException("Modbus transport not connected");
		}
	}

	private void validateArrayLength(ModbusDataPoint dataPoint, Value value) throws GenDriverException {
		if (dataPoint.getDataPoint().getArrayLength() == null) {
			throw new GenDriverException("Cannot write array to modbus. Modbus data point is not defined as array");
		}

		int modbusArrayLen = dataPoint.getDataPoint().getArrayLength();
		if (dataPoint.getDataPoint().getArrayLength() < value.asArray().length) {
			throw new GenDriverException("Cannot write array value to modbus. " +
					"Modbus array length=" + modbusArrayLen +
					", value array length=" + value.asArray().length);
		}
	}
}
