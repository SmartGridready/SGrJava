/**
*Copyright(c) 2022 Verein SmartGridready Switzerland
* @generated NOT
* 
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

author: IBT/cb

The purpose of this class is to support tests with EID's for Heat Pumps

 */

package com.smartgridready.communicator.modbus.impl;

import com.smartgridready.communicator.common.api.GenDeviceApi;
import com.smartgridready.communicator.common.api.SGrDeviceBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.smartgridready.communicator.rest.exception.RestApiResponseParseException;
import com.smartgridready.communicator.rest.exception.RestApiServiceCallException;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.modbus.GenDriverModbusException;
import com.smartgridready.driver.api.modbus.GenDriverSocketException;
import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.communicator.common.api.values.BooleanValue;
import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Value;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Properties;





@SuppressWarnings("java:S2925")
public class HeatPumpTester {

	private static final Logger LOG = LoggerFactory.getLogger(HeatPumpTester.class);


	private static final String XML_BASE_DIR = "../SGrSpecifications/XMLInstances/ExtInterfaces/";

	// we need static definitions for performance reason
	//------------------------------------------------------

	// Modbus TCP devices
	private static GenDeviceApi devTCP_IOP = null;
	private static GenDeviceApi devStiebelISG = null;
	private static GenDeviceApi devCTAoptiHeat = null;
	private static GenDeviceApi devHovalTCP = null;
	private static GenDeviceApi devHelioTCP = null;

	// test loop parameters
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private static int runtimeCnt = 0;
	// Exception Counters
	private static int devStiebel_ISGExcpetions = 0;
	private static int devCTAoptiHeat_Exceptions = 0;
	private static int devHovalTCP_Exceptions = 0;
	private static int devHelioTCP_Exceptions = 0;
	// device selection
	private static boolean devStiebelISGIsOn = false;
	private static boolean devCTAoptiHeatIsOn = false;
	private static boolean devHovalTCPIsOn = false;
	private static boolean devHelioTCPIsOn = true;

	// Set the mockModbusDriver to new GenDriverAPI4ModbusRTUMock() to mock the real devices.
	//private static GenDriverAPI4Modbus  mockModbusDriver = new GenDriverAPI4ModbusRTUMock();

	public static void main(String argv[]) {


		try {

			LOG.info(String.format("\n\n\n"));
			LOG.info("*************************  TEST INITIALISATION ***************************************");
			LOG.info("*  Time=" + dtf.format(LocalDateTime.now()) + "                                      *");
			LOG.info("**************************************************************************************");




			if (devStiebelISGIsOn) {
				LOG.info(" -init devStiebelISG @:" + dtf.format(LocalDateTime.now()) + " ");
				initStiebelISG(XML_BASE_DIR, "SGr_04_0015_xxxx_StiebelEltron_HeatPump_V1.0.0.xml");
			}
			if (devCTAoptiHeatIsOn) {
				LOG.info(" -init devCTAoptiHeat @:" + dtf.format(LocalDateTime.now()) + " ");
				initCTAoptiHeat(XML_BASE_DIR, "SGr_04_0033_0000_CTA_HeatPump_V1.0.0.xml");
			}
			if (devHovalTCPIsOn) {
				LOG.info(" -init devHovalTCP @:" + dtf.format(LocalDateTime.now()) + " ");
				initHovalTCP(XML_BASE_DIR, "SGr_04_0017_xxxx_HOVAL_HeatPump_V1.0.0.xml");
			}
			if (devHelioTCPIsOn) {
				LOG.info(" -init devHelioTCP @:" + dtf.format(LocalDateTime.now()) + " ")
				;
				initHelioTCP(XML_BASE_DIR, "SGr_04_0020_xxxx_Heliotherm_HeatPumpV0.2.1.xml");
			}



			LOG.info("############################   Start device Testing   ####################################");

			try {

				for (runtimeCnt = 0; runtimeCnt < 3000; runtimeCnt++) {
					// loop data & test reporting
					Thread.sleep(10000);  // show last block for ccc  milliseconds
					LOG.info(String.format("\n\n"));
					LOG.info(">>> Time=" + dtf.format(LocalDateTime.now()));
					LOG.info(String.format("------> LOOP=" + runtimeCnt));

					if (devStiebelISGIsOn && (devStiebel_ISGExcpetions != 0))
						LOG.info(String.format("  StiebelISG Excpetions =" + devStiebel_ISGExcpetions));
					if (devCTAoptiHeatIsOn && (devCTAoptiHeat_Exceptions != 0))
						LOG.info(String.format("  CTAoptiHeat Excpetions=" + devCTAoptiHeat_Exceptions));
					if (devHovalTCPIsOn && (devHovalTCP_Exceptions != 0))
						LOG.info(String.format("HovalTCP Excpetions=" + devHovalTCP_Exceptions));
					if (devHelioTCPIsOn && (devHelioTCP_Exceptions != 0))
						LOG.info(String.format("HelioTCP Excpetions=" + devHelioTCP_Exceptions));

					//Next loop 
					if (devStiebelISGIsOn) tstStiebelISG();
					if (devCTAoptiHeatIsOn) tstCTAoptiHeat();
					if (devHovalTCPIsOn) tstHovalTCP();
					if (devHelioTCPIsOn) tstHelioTCP();

				}

			} catch (Exception e) {
				LOG.info(String.format("Error reading value from device " + e));
				e.printStackTrace();
			}
		} catch (Exception e) {
			LOG.info(String.format("Error loading device description. " + e));
		}
	}


	// -----------------------------------------------------------------------------------------------------------------------------
	// Device testing frame
	// -----------------------------------------------------------------------------------------------------------------------------
	static void initHovalTCP(String aBaseDir, String aDescriptionFile) {

		try {

			Properties prop = new Properties();
			prop.put("tcp_address", "192.168.1.50");
			prop.put("tcp_port", "502");
			prop.put("SlaveID", "0");

			devHovalTCP = new SGrDeviceBuilder()
					//.useModbusGatewayFactory(new EasyModbusGatewayFactory())
					.properties(prop)
					.eid(Path.of(aBaseDir, aDescriptionFile))
					.build();

			devHovalTCP.connect();

		} catch (Exception e) {
			devHovalTCPIsOn = false;
			LOG.info(String.format("Error connecting device  " + e));
		}
	}


	static void tstHovalTCP() {

		float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0, fVal5 = (float) 0.0;
		//float dVal1 = 0.0, dVal2 = 0.0;
		int iVal1 = 0, iVal2 = 0, iVal3 = 0, iVal4 = 0;
		long lVal = 0;
		boolean bVal1 = false, bVal2 = false, bVal3 = false;
		String sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 = "0.0";
		DataTypeProduct gdtValue = new DataTypeProduct();

		try {
			LOG.info(String.format("HeatPump HovalTCP"));
			Thread.sleep(200);

			if (runtimeCnt == 2) {
				// Testing Setters
				DataTypeProduct hpCmd = new DataTypeProduct();

				float fValStpt = (float) 0.0;
				Value hpval = Float64Value.of(fValStpt);
				LOG.info(String.format("######################  setting new values ######################"));
				// testing setters: one setting for a test run only recommended
				// read the device manual carefully before testing any setpoint


				// -------------------------------  SGR LEVEL 2 SETPOINTS	------------------------
				// control HeatPumpBase by hovHPOpModeCmd enum
				// TODO:  check enumerations
				devHovalTCP.setVal("HeatPumpBase", "HPOpModeCmd", EnumValue.of("HP_AUTOMATIC"));
				LOG.info("  Setting  HeatPumpBase:hovHPOpModeCmd=HP_AUTOMATIC");

				// control HeatCoolCtrl_1 by enum HeatCoolCtrlOpModeCmd
				devHovalTCP.setVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
				LOG.info("  Setting  HeatCoolCtrl_1:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");

				// control HeatCoolCtrl_2 by enum HeatCoolCtrlOpModeCmd
				devHovalTCP.setVal("HeatCoolCtrl_2", "HeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
				LOG.info("  Setting  HeatCoolCtrl_2:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");

				// control HeatCoolCtrl_1 by enum HeatCoolCtrlOpModeCmd
				devHovalTCP.setVal("HeatCoolCtrl_3", "HeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
				LOG.info("  Setting  HeatCoolCtrl_3:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");

				// control DomHotWaterCtrl by  DomHotWaterOpModeCmd
				devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWaterOpModeCmd", EnumValue.of("DHW_ECO_OP"));
				LOG.info("  Setting  DomHotWaterCtrl:hovDomHotWOpModeCmd=DHW_ECO_OP");

				// control ReadyStates_bwp by enum SGReadyStateLv2Type
				devHovalTCP.setVal("SG-ReadyStates", "hovSGReadySrcSelect", EnumValue.of("SRC_src_CONTACTS"));
				LOG.info("  Setting  ReadyStates_bwp:hovSGReadySrcSelect=SRC_src_CONTACTS");
				devHovalTCP.setVal("SG-ReadyStates", "SGReadyOpModeCmd", EnumValue.of("HP_NORMAL"));
				LOG.info("  Setting  ReadyStates_bwp:SGReadyOpModeCmd=HP_NORMAL");

				// -------------------------------  SGR LEVEL 4 SETPOINTS -----------------------
				// TODO:  check logs, not correlating to constant value in setpoints
				// HeatCoolCtrl Setpoints
				fValStpt =  (float) 22.20f;
				devHovalTCP.setVal("HeatPumpBase", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting HeatPumpBase: SupplyWaterTempSetpoint" + fValStpt));

				// PowerCtrl new Setpoint in %
				/*devHovalTCP.setVal("PowerCtrl", "SpeedCtrlSetpoint", Float64Value.of(50.0f));
				LOG.info(String.format("  Setting PowerCtrl: PowerCtrlStp=" + fValStpt + "%%"));
                will only be supported with a next generation of controls */


				// HeatCoolCtrl_1 Setpoints
				fValStpt =  (float) 21.0f;
				devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt ));
				devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointOffsetCooling", Float64Value.of(fValStpt - (float) 0.2f));
				devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointOffsetHeating", Float64Value.of(fValStpt + (float) 0.3));
				devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointComfort", Float64Value.of(fValStpt + (float) 0.4f));
				devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEco", Float64Value.of(fValStpt + (float) 0.5f));
				LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempSetpoint=" + fValStpt + " °C , Cool=" + (fValStpt - (float) 0.2f) + " °C , Heat=" + (fValStpt + (float) 0.3f) + " °C , Comfort="+ (fValStpt + (float) 0.4f) +" °C , Eco="+ (fValStpt+(float) 0.5f)) + " °C");

				// HeatCoolCtrl_2 Setpoints
				fValStpt =  (float) 22.0f;
				devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt ));
				devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointOffsetCooling", Float64Value.of(fValStpt - (float) 0.2f));
				devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointOffsetHeating", Float64Value.of(fValStpt + (float) 0.3));
				devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointComfort", Float64Value.of(fValStpt + (float) 0.4f));
				devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEco", Float64Value.of(fValStpt + (float) 0.5f));
				LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempSetpoint=" + fValStpt + " °C , Cool=" + (fValStpt - (float) 0.2f) + " °C , Heat=" + (fValStpt + (float) 0.3f) + " °C , Comfort="+ (fValStpt + (float) 0.4f) +" °C , Eco="+ (fValStpt+(float) 0.5f)) + " °C");

				// HeatCoolCtrl_3 Setpoints
				fValStpt =  (float) 22.0f;
				devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt ));
				devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointOffsetCooling", Float64Value.of(fValStpt - (float) 0.2f));
				devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointOffsetHeating", Float64Value.of(fValStpt + (float) 0.3));
				devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointComfort", Float64Value.of(fValStpt + (float) 0.4f));
				devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointEco", Float64Value.of(fValStpt + (float) 0.5f));
				LOG.info(String.format("  Setting HeatCoolCtrl_3: SupplyWaterTempSetpoint=" + fValStpt + " °C , Cool=" + (fValStpt - (float) 0.2f) + " °C , Heat=" + (fValStpt + (float) 0.3f) + " °C , Comfort="+ (fValStpt + (float) 0.4f) +" °C , Eco="+ (fValStpt+(float) 0.5f)) + " °C");


				// BufferStorageCtrl Offset-Setpoint Heating
				fValStpt =  (float) 2.0f;
				devHovalTCP.setVal("BufferStorageCtrl", "HeatBufferTempSetpointOffset", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting BufferStorageCtrl: HeatBufferTempSetpointOffset=" + fValStpt + " °C"));

				// BufferStorageCtrl Offset-Setpoint Cooling
				fValStpt =  (float) 2.1f;
				devHovalTCP.setVal("BufferStorageCtrl", "CoolBufferTempSetpointOffset", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting BufferStorageCtrl: CoolBufferTempSetpointOffset=" + fValStpt + " °C"));


				// DomHotWaterCtrl  DomHotWaterTempSetpointOffset
				fValStpt =  (float) 1.1f;
				devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWaterTempSetpointOffset", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWaterTempSetpointOffset=" + fValStpt + " °C"));
				// DomHotWaterCtrl  DomHotWaterTempSetpointComfortt
				fValStpt =  (float) 44.1f;
				devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWaterTempSetpointComfort", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWaterTempSetpointComfort=" + fValStpt + " °C"));
				// DomHotWaterCtrl  DomHotWaterTempSetpointOffset
				fValStpt =  (float) 38.9f;
				devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWaterTempSetpointEco", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWaterTempSetpointEco=" + fValStpt + " °C"));
				LOG.info("\n");
			}

			// testing getter
			// used by unknown programmer setMockIntegerType(true);
			EnumRecord opModeVal = devHovalTCP.getVal("HeatPumpBase", "HPOpModeCmd").getEnum();
			EnumRecord opStateVal = devHovalTCP.getVal("HeatPumpBase", "HPOpStateAsEnum").getEnum();
			bVal1 = devHovalTCP.getVal("HeatPumpBase", "ErrorFlagSGr").getBoolean();
			fVal1 = devHovalTCP.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTempSetpoint").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTempSetpointFb").getFloat32();
			fVal4 = devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
			fVal5 = devHovalTCP.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();

			LOG.info(String.format(
					"  HeatPumpBase:    HPOpModeCmd=" + opModeVal.getLiteral() + "/" + opModeVal.getOrdinal()
							+ ",  HPOpStateAsEnum=" + opStateVal.getLiteral() + "/" + opStateVal.getOrdinal()
							+ ",  ErrorFlagSGr=" + bVal1 + ",  OutsideAirTemp=" + fVal1 + "°C"));

			LOG.info(String.format("    SupplyWaterTempSetpoint=" + fVal2 + " °C,  SupplyWaterTempSetpointFb=" + fVal3 + "°C, SupplyWaterTemp=" + fVal4 + "°C,  ReturnSupplyWaterTemp=" + fVal5 + " °C "));
			LOG.info(" ");

			// used by unknown programmer setMockIntegerType(true);
			EnumRecord hovHotWOpMode = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterOpModeCmd").getEnum();
			EnumRecord hovHotWOpState = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterOpState").getEnum();
			fVal1 = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointOffset").getFloat32();
			fVal2 = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointComfort").getFloat32();
			fVal3 = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointEco").getFloat32();
			fVal4 = devHovalTCP.getVal("DomHotWaterCtrl", "ActDomHotWTemp").getFloat32();
			fVal5 = devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointFb").getFloat32();

			LOG.info(String.format(
					"  DomHotWCtrl: DomHotWaterOpModeCmd=" + hovHotWOpMode.getLiteral() + "/" + hovHotWOpMode.getOrdinal()
							+ ",  DomHotWaterOpState=" + hovHotWOpState.getLiteral() + "/" + hovHotWOpState.getOrdinal().intValue()
							+ ", DomHotWaterTempSetpointOffset=" + fVal1 + " °C"));

			LOG.info(String.format("        DomHotWaterTempSetpointComfort=" + fVal2 + " °C,  DomHotWaterTempSetpointEco=" + fVal3 + " °C,   ActDomHotWaterTemp=" + fVal4 + " °C,  DomHotWaterTempSetpointFb=" + fVal5 + " °C "));
			LOG.info(" ");

			/*
			fVal1 = devHovalTCP.getVal("PowerCtrl", "SpeedCtrlSetpoint").getFloat32();
			fVal2 = devHovalTCP.getVal("PowerCtrl", "ActSpeed").getFloat32();
			fVal3 = devHovalTCP.getVal("BufferStorageCtrl", "CoolBufferTempStptOffs").getFloat32();
			fVal3 = devHovalTCP.getVal("PowerCtrl", "ActPowerACtot").getFloat32();
			LOG.info(String.format("  PowerCtrl: PowerCtrlStp =" + fVal1 + " %% , ActSpeed=" + fVal2 + " %% , ActPowerACtot=" + fVal3 + " kW"));
			LOG.info(" ");
			will only be supported with a next generation of controls */

			EnumRecord oEnumList = devHovalTCP.getVal("BufferStorageCtrl", "ActBufferState").getEnum();
			fVal1 = devHovalTCP.getVal("BufferStorageCtrl", "ActBufferTempStptFb").getFloat32();
			fVal2 = devHovalTCP.getVal("BufferStorageCtrl", "HeatBufferTempSetpointOffset").getFloat32();
			fVal3 = devHovalTCP.getVal("BufferStorageCtrl", "CoolBufferTempSetpointOffset").getFloat32();
			LOG.info(String.format("  BufferStorageCtrl: ActBufferState=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
					+ ",  ActBufferWaterTempStptFb=" + fVal1 + ",  HeatBufferTempSetpointOffset=" + fVal2 + " °C,  CoolBufferTempSetpointOffset=" + fVal3 + " °C"));
			fVal1 = devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
			fVal2 = devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTempUpper").getFloat32();
			fVal3 = devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTempLower").getFloat32();
			fVal4 = devHovalTCP.getVal("BufferStorageCtrl", "ActCoolBufferTempUpper").getFloat32();
			fVal5 = devHovalTCP.getVal("BufferStorageCtrl", "ActCoolBufferTempLower").getFloat32();
			LOG.info(String.format("      ActHeatBufferTemp=" + fVal1 + ",  ActHeatBufferTempUpper=" + fVal2 + " °C,  ActHeatBufferTempLower=" + fVal3 + " °C,  ActCoolBufferTempUpper=" + fVal4 + " °C,  ActCoolBufferTempLower=" + fVal5 + " °C "));
			LOG.info(" ");


			oEnumList = devHovalTCP.getVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd").getEnum();
			EnumRecord oEnumList1 = devHovalTCP.getVal("HeatCoolCtrl_1", "HeatCoolCtrlOpState").getEnum();
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpoint").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_1", "ReturnSupplyWaterTemp").getFloat32();
			LOG.info(String.format(
					"  HeatCoolCtrl_1:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
							+ " ,  HeatCoolCtrlOpState: " + oEnumList1.getLiteral() + "/" + oEnumList1.getOrdinal()
							+ " SupplyWaterTemp=" + fVal1 + " °C,  SupplyWaterTempSetpoint=" + fVal2
							+ " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "));
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointOffsetCooling").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointOffsetHeating").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointComfort").getFloat32();
			fVal4 = devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEco").getFloat32();
			LOG.info(String.format("         SupplyWaterTempSetpointOffsetCooling=" + fVal1 + " °C,  SupplyWaterTempSetpointOffsetHeating=" + fVal2 +
					" °C,  SupplyWaterTempSetpointComfort=" + fVal3 + " °C,  SupplyWaterTempSetpointEco=" + fVal4 + " °C "));
			LOG.info(" ");

			oEnumList = devHovalTCP.getVal("HeatCoolCtrl_2", "HeatCoolCtrlOpModeCmd").getEnum();
			oEnumList1 = devHovalTCP.getVal("HeatCoolCtrl_2", "HeatCoolCtrlOpState").getEnum();
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTemp").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpoint").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_2", "ReturnSupplyWaterTemp").getFloat32();
			LOG.info(String.format(
					"  HeatCoolCtrl_2:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
							+ " ,  HeatCoolCtrlOpState: " + oEnumList1.getLiteral() + "/" + oEnumList1.getOrdinal()
							+ " SupplyWaterTemp=" + fVal1 + " °C,  SupplyWaterTempSetpoint=" + fVal2
							+ " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "));
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointOffsetCooling").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointOffsetHeating").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointComfort").getFloat32();
			fVal4 = devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEco").getFloat32();
			LOG.info(String.format(
					"         SupplyWaterTempSetpointOffsetCooling=" + fVal1 + " °C,  SupplyWaterTempSetpointOffsetHeating=" + fVal2 +
					" °C,  SupplyWaterTempSetpointComfort=" + fVal3 + " °C,  SupplyWaterTempSetpointEco=" + fVal4 + " °C "));
			LOG.info(" ");


			oEnumList = devHovalTCP.getVal("HeatCoolCtrl_3", "HeatCoolCtrlOpModeCmd").getEnum();
			oEnumList1 = devHovalTCP.getVal("HeatCoolCtrl_3", "HeatCoolCtrlOpState").getEnum();
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTemp").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempSetpoint").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_3", "ReturnSupplyWaterTemp").getFloat32();
			LOG.info(String.format(
					"  HeatCoolCtrl_3:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
							+ " ,  HeatCoolCtrlOpState: " + oEnumList1.getLiteral() + "/" + oEnumList1.getOrdinal()
							+ " SupplyWaterTemp=" + fVal1 + " °C,  SupplyWaterTempSetpoint=" + fVal2
							+ " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "));
			fVal1 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointOffsetCooling").getFloat32();
			fVal2 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointOffsetHeating").getFloat32();
			fVal3 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointComfort").getFloat32();
			fVal4 = devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempSetpointEco").getFloat32();
			LOG.info(String.format(
					"         SupplyWaterTempSetpointOffsetCooling=" + fVal1 + " °C,  SupplyWaterTempSetpointOffsetHeating=" + fVal2 +
					" °C,  SupplyWaterTempSetpointComfort=" + fVal3 + " °C,  SupplyWaterTempSetpointEco=" + fVal4 + " °C "));
			LOG.info(" ");

			fVal1 = devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyTot").getFloat32();
			fVal2 = devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
			fVal3 = devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyCool").getFloat32();
			fVal4 = devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
			LOG.info(String.format("  EnergyMonitor: ThermalEnergyTot=" + fVal1 + " kWh, ThermalEnergyHeat=" + fVal2 + " kWh, ThermalEnergyCool=" + fVal3 + " kWh, ThermalEnergyDomHotWaterater=" + fVal4 + " kWh"));

			fVal1 = devHovalTCP.getVal("EnergyMonitor", "ActiveEnergyACtot").getFloat32();
			fVal2 = devHovalTCP.getVal("EnergyMonitor", "ActivePowerACtot").getFloat32();
			fVal3 = devHovalTCP.getVal("EnergyMonitor", "RuntimeCompressor").getFloat32();
			lVal = devHovalTCP.getVal("EnergyMonitor", "NoOfStartupsCompressor").getInt32U();
			LOG.info(String.format("         ActiveEnergyACtot=" + fVal1 + " kWh,  ActivePowerACtot=" + fVal2 + " kWh, RuntimeCompressor=" + fVal3 + " h,  NoOfStartupsCompressor=" + lVal + "  times"));
			LOG.info(" ");


			oEnumList = devHovalTCP.getVal("SG-ReadyStates", "SGReadyOpModeCmd").getEnum();
			oEnumList1 = devHovalTCP.getVal("SG-ReadyStates", "hovSGReadySrcSelect").getEnum();
			LOG.info(String.format("  SG-ReadyStates SGReadyOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
					+ ",   hovSGReadySrcSelect=" + oEnumList1.getLiteral() + "/" + oEnumList1.getOrdinal()));

		} catch (Exception  e) {
			 // unknown programmer used multi-catch statement
			LOG.info(String.format("Error reading value from device dev: " + e));
			e.printStackTrace();
			devHovalTCP_Exceptions++;
		}
	}


	// -----------------------------------------------------------------------------------------------------------------------------
	// Device testing frame Stiebel
	// -----------------------------------------------------------------------------------------------------------------------------
	static void initStiebelISG(String aBaseDir, String aDescriptionFile) {

		{
			try {

				Properties prop = new Properties();
				prop.put("tcp_address", "192.168.1.50");
				prop.put("tcp_port", "502");
				prop.put("SlaveID", "0");

				devStiebelISG = new SGrDeviceBuilder()
						//.useModbusGatewayFactory(new EasyModbusGatewayFactory())
						.properties(prop)
						.eid(Path.of(aBaseDir, aDescriptionFile))
						.build();

				devStiebelISG.connect();

			} catch (Exception e) {
				devStiebelISGIsOn = false;
				LOG.info(String.format("Error while connecting device. " + e));
			}

		}

	}

	static void tstStiebelISG() {
		float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0, fVal5 = (float) 0.0;
		double dVal1 = 0.0, dVal2 = 0.0;
		int iVal1, iVal2 = 0, iVal3 = 0, iVal4 = 0;
		Value bitmapVal1;
		Value bitmapVal2;
		long lVal = 0;
		boolean bVal1 = false, bVal2 = false, bVal3 = false;
		String sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 = "0.0";


		try {
			LOG.info(String.format("HeatPump Stiebel-Eltron"));
			Thread.sleep(25);

			if (runtimeCnt == 2) {
			/*
					DataTypeProduct  hpval = new DataTypeProduct();
				   LOG.info(String.format("######################  setting new values ######################"));
				   // testing setters: one setting for a test run only recommended
				   // read the device manual carefully before testing any setpoint
				  // control by HPOpMode enum
				  devStiebelISG.setVal("HeatPumpBase", "HPOpModeCmd", EnumValue.of("STI_WP_PROG_OP"));
				
				 
				  //Setpoints Comfort
				  devStiebelISG.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointComfort",Float64Value.of(29.1f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempSetpointComfort=" + fVal1));
				  devStiebelISG.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointComfort",Float64Value.of(29.2f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempSetpointComfort=" + fVal1));
				  devStiebelISG.setVal("DomHotWaterCtrl", "SupplyWaterTempSetpointComfort",Float64Value.of(28.3f));
				  LOG.info(String.format("  Setting DomHotWaterCtrl: SupplyWaterTempSetpointComfort=" + fVal1));
				 

				  //Setpoints Eco
				  devStiebelISG.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEco",Float64Value.of(25.1f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempSetpointEco=" + fVal1));
				  devStiebelISG.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEco",Float64Value.of(25.8));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempSetpointEco=" + fVal1));
				  devStiebelISG.setVal("DomHotWCtrl", "SupplyWaterTempSetpointEco",Float64Value.of(26.2f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempSetpointEco=" + fVal1));

				
				// enable or disable SG-Ready
				LOG.info("  Setting ReadyStates_bwp:SGReadyEnabled=" + false);
				devStiebelISG.setVal("SG-ReadyStates", "SGReadyEnabled", BooleanValue.of(false));
				
							
				/// control SG-Ready by enum SGReadyStateLv2Type
				devStiebelISG.setVal("SG-ReadyStates", "SGReadyOpModeCmd", EnumValue.of("HP_NORMAL"));
				LOG.info("  Setting  ReadyStates_bwp:SGReadyOpModeCmd=HP_NORMAL");
			
			
				// control SG-Ready by contacts

				devStiebelISG.setVal("SG-ReadyStates", "SGReadyInp1isON", BooleanValue.of(false));
				LOG.info("  Setting SG-ReadyStates:SGReadyInp1isON=false",BooleanValue.of(false));
                devStiebelISG.setVal("SG-ReadyStates", "SGReadyInp2isON", BooleanValue.of(false));
                LOG.info("  Setting SG-ReadyStates:SGReadyInp2isON=false");
			*/
			}

			// testing
			// used by unknown programmer setMockIntegerType(true);
			EnumRecord oEnumList = devStiebelISG.getVal("HeatPumpBase", "HPOpModeCmd").getEnum();
			bitmapVal1 = devStiebelISG.getVal("HeatPumpBase", "HPOpStateAsBitmap");
			bVal1 = devStiebelISG.getVal("HeatPumpBase", "ErrorFlagSGr").getBoolean();
			fVal1 = devStiebelISG.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
			fVal2 = devStiebelISG.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
			fVal3 = devStiebelISG.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();
			fVal4 = devStiebelISG.getVal("HeatPumpBase", "SourceTemp").getFloat32();
			LOG.info(String.format("  HeatPumpBase:      HPOpModeCmd=" + oEnumList.getLiteral() + ",  HPOpState=" + iVal2 + ",  ErrorFlagSGr=" + bVal1 + ",  OutsideAir=" + fVal1 + " °C, SupplyWaterTemp=" + fVal2 + "°C,  ReturnSupplyWaterTemp=" + fVal3 + " °C,   SourceTemp=" + fVal4 + " °C "));


			LOG.info("    HPOpStateAsBitmap bits set = {}", Integer.toBinaryString(bitmapVal1.getInt32()));
			bitmapVal1.getBitmap().forEach((literal, value) -> LOG.info("        {} = {}", literal, value));


			fVal1 = devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointComfort").getFloat32();
			fVal2 = devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointEco").getFloat32();
			fVal3 = devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointFb").getFloat32();
			fVal4 = devStiebelISG.getVal("DomHotWaterCtrl", "ActDomHotWaterTemp").getFloat32();
			LOG.info(String.format("  DomHotWCtrl:  DomHotWaterTempSetpointComfort=" + fVal1 + " °C,  DomHotWaterTempSetpointEco=" + +fVal2 + " °C,  DomHotWaterTempSetpointFb=" + fVal3 + " °C,  ActDomHotWaterTemp=" + fVal4 + " °C "));
			LOG.info(String.format(" "));

			fVal1 = devStiebelISG.getVal("BufferStorageCtrl", "ActHeatBufferTempSetpointFb").getFloat32();
			fVal2 = devStiebelISG.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
			LOG.info(String.format("  BufferStorageCtrl: ActHeatBufferTempSetpointFb=" + fVal1 + " °C,    ActHeatBufferTemp=" + fVal2 + " °C"));
			LOG.info(String.format(" "));

			fVal1 = devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointComfort").getFloat32();
			fVal2 = devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEco").getFloat32();
			fVal3 = devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointFb").getFloat32();
			fVal4 = devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
			LOG.info(String.format("  HeatCoolCtrl_1:    SupplyWaterTempSetpointComfort=" + fVal1 + " °C,  SupplyWaterTempSetpointEco=" + fVal2 + " °C,  SupplyWaterTempSetpointFb=" + fVal3 + " °C,  SupplyWaterTemp=" + fVal4 + " °C "));
			LOG.info(String.format(" "));			;
			fVal1 = devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointComfort").getFloat32();
			fVal2 = devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEco").getFloat32();
			fVal3 = devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointFb").getFloat32();
			fVal4 = devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTemp").getFloat32();
			LOG.info(String.format("  HeatCoolCtrl_2:    SupplyWaterTempSetpointComf=" + fVal1 + " °C,  SupplyWaterTempSetpointEco=" + fVal2 + " °C,  SupplyWaterTempSetpointFb=" + fVal3 + " °C,  SupplyWaterTemp=" + fVal4 + " °C "));
			LOG.info(String.format(" "));

			oEnumList = devStiebelISG.getVal("SG-ReadyStates", "SGReadyState").getEnum();
			bVal1 = devStiebelISG.getVal("SG-ReadyStates", "SGReadyEnabled").getBoolean();
			bVal2 = devStiebelISG.getVal("SG-ReadyStates", "SGReadyInp1isON").getBoolean();
			bVal3 = devStiebelISG.getVal("SG-ReadyStates", "SGReadyInp2isON").getBoolean();
			LOG.info(String.format(
					"  SGReady-bwp:      SGReadyState=" + oEnumList.getLiteral() + ",  SGReadyEnabled=" + bVal1
							+ ",  SGReadyInp1isON=" + bVal2 + ",  SGReadyInp2isON=" + bVal3));

			LOG.info(String.format(" "));
			fVal1 = devStiebelISG.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
			fVal2 = devStiebelISG.getVal("EnergyMonitor", "ActiveEnergyACheat").getFloat32();
			fVal3 = devStiebelISG.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
			fVal4 = devStiebelISG.getVal("EnergyMonitor", "ActiveEnergyACDomHotWater").getFloat32();
			LOG.info(String.format(" "));
			LOG.info(String.format("  EnergyMonitor ThermalEnergyHeat=" + fVal1 + " kWh,  ActiveEnergyACheat=" + fVal2 + " kWh,  ThermalEnergyDomHotWater=" + fVal3 + " kWh,  ActiveEnergyACDomHotWater=" + fVal4 + " kWh"));

			fVal1 = devStiebelISG.getVal("EnergyMonitor", "RuntimeHeating").getFloat32();
			fVal2 = devStiebelISG.getVal("EnergyMonitor", "RuntimeDomHotWater").getFloat32();
			LOG.info(String.format("  EnergyMonitor RuntimeHeating=" + fVal1 + " h,  RuntimeDomHotWater=" + fVal2 + " h"));

//	             TBC: launches illegal address @31 , no other start data found
//	 				lVal=devStiebelISG.getValByGDPType("EnergyMonitor", "NrOfStartupsCompressor").getInt16U();
//				+ fVal3 + " h,  NrOfStartupsCompressor="  + lVal+" times

		} catch (Exception e) {
			LOG.info(String.format("Error reading value from device devStiebelISG: " + e));
			e.printStackTrace();
			devStiebel_ISGExcpetions++;
		}

	}


	// USED TO COPY / PASTE ADDITIONAL TEST DEVICES
	// -----------------------------------------------------------------------------------------------------------------------------
	// Device testing frame CTA
	// -----------------------------------------------------------------------------------------------------------------------------
	static void initCTAoptiHeat(String aBaseDir, String aDescriptionFile) {

		try {

			Properties prop = new Properties();
			prop.put("tcp_address", "192.168.1.50");
			prop.put("tcp_port", "502");
			prop.put("SlaveID", "0");

			devCTAoptiHeat = new SGrDeviceBuilder()
					//.useModbusGatewayFactory(new EasyModbusGatewayFactory())
					.properties(prop)
					.eid(Path.of(aBaseDir, aDescriptionFile))
					.build();

			devCTAoptiHeat.connect();


			// set back remote control enabler
			devCTAoptiHeat.setVal("DeviceInformation", "RemoteHCTempSetptEnable", BooleanValue.of(false));


		} catch (Exception e) {
			LOG.info(String.format("Error connecting device. " + e));
		}
	}

	static void tstCTAoptiHeat() {
		float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0;
		String sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 = "0.0";
		int iVal1 = 0, iVal2 = 0, iVal3 = 0, iVal4 = 0;
		long lVal1 = 0, lVal2 = 0, lVal3 = 0, lVal4 = 0;
		boolean bVal1 = false;

		//Setters
		float fValStpt = (float) runtimeCnt * (float) 0.1;
		DataTypeProduct gdtValue = new DataTypeProduct();
		DataTypeProduct modeCmd = new DataTypeProduct();

		try {
			// if RTU is used, set address here
			// mbRTU.setUnitIdentifier((byte) 7);

			LOG.info(String.format("Testing CTAoptiHeat"));
			Thread.sleep(25);


			if (runtimeCnt == 2) {
				DataTypeProduct hpval = new DataTypeProduct();

				LOG.info(String.format("######################  setting new values ######################"));
				// testing setters: one setting for a test run only recommended
				// read the device manual carefully before testing any setpoint

				// test setters
				/* Important Remark related to Device level activity controls
				 * ctaRemoteCtrlTimeSec is a time counter counting down activce tine fpr remote setpoints  of this device.
				 * Activating this counter means that the selected  remote setpoints become valid again
				 * THIS IS ONLY USED FOR Setpoints in HeatCool functional profiles, NOT  for OpModeCmds
				 */
						  
						  /*  set ctaRemoteCtrlTimeSec  
						  //  enable remote control for setpoints (NOT modeCmd's)
						  iVal1 = 60;
						  devCTAoptiHeat.setVal("DeviceInformation","ctaRemoteCtrlTimeSec", Int16UValue.of(iVal1));
						  LOG.info(String.format("Setting ctaRemoteCtrlTimeSec="  + iVal1));
						  Thread.sleep(25);
						  */

						  /* set HeatCool_1  remote setpoint enabled
						  fVal1 = (float) 22.6;
						  devCTAoptiHeat.setVal("HeatCoolCtrl_1","RemoteHCTempSetptEnable",BooleanValue.of(true) );  
						  LOG.info(String.format("RemoteHCTempSetptEnable enabled"));
						  devCTAoptiHeat.setVal("HeatCoolCtrl_1","SupplyWaterTempSetpoint",Float64Value.of(fVal1));
						  LOG.info(String.format("Setting HeatCoolCtrl_1: SupplyWaterTempSetpoint="  + fVal1 )); 
						  */   
						  
						  /* set HeatCoolOpModeCmd  
						  // remark:  RemoteHCTempSetptEnable must be false,  ctaRemoteCtrlTimeSec must be 0
						  String sLiteral =  "HC_HEAT_ECO";
  						  devCTAoptiHeat.setVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info(String.format("Setting HeatCoolCtrlOpModeCmd="+ sLiteral.intern())); 
						  */   
						  
						  
						  /*  Set DomHotWSetPoint
						  fVal1 = (float) 51.1;
						  Thread.sleep(25);
						  devCTAoptiHeat.setVal("DomHotWaterCtrl", "ActDomHotWaterTemp",Float64Value.of(fVal1));
						  */
						  
						  /*  Set DomHotWCtrl
						  String sLiteral = "DHW_OFF";						   
						  devCTAoptiHeat.setVal("DomHotWaterCtrl", "DomHotWaterOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info(String.format("Setting ctaDomHotWOpMode ="+ sLiteral.intern())); 
					      */
						  
						  
						  /* set power
						  fVal1 = (float) 38.0;
						  devCTAoptiHeat.setVal("PowerCtrl","SpeedCtrlSetpoint",Float32Value.of(fVal1));
						  LOG.info(String.format("Setting PowerCtrl: SpeedCtrlSetpoint="  + fVal1));
						  */
						  
						  /* set storage buffer
						  fVal1 = (float) 25.0;
						  devCTAoptiHeat.setVal("BufferStorageCtrl","HeatBufferTempStptOffset",Float64Value.of(fVal1));
						  LOG.info(String.format("Setting BufferStorageCtrl: HeatBufferTempStptOffset="  + fVal1));
					      */

						  
						  /* control SG-Ready by enum SGReadyStateLv2Type
						  String sLiteral = "HP_NORMAL";
						  devCTAoptiHeat.setVal("SG-ReadyStates", "SGReadyOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info("  Setting  SG-ReadyStates:SGReadyCmd="+ sLiteral.intern());
						  */
			}


			// testing getters
			// used by unknown programmer setMockIntegerType(true);
			iVal1 = (int) devCTAoptiHeat.getVal("DeviceInformation", "ctaRemoteCtrlTimeSec").getInt16U();
			boolean bRem = true;
			if (iVal1 == 0) bRem = false;
			LOG.info(String.format("  ctaRemoteCtrlTimeSec: " + bRem + "  ctaRemoteCtrlTimeSec=" + iVal1));


			EnumRecord oEnumListGet = devCTAoptiHeat.getVal("HeatPumpBase", "HPOpStateAsEnum").getEnum();
			iVal3 = (int) devCTAoptiHeat.getVal("HeatPumpBase", "ErrorFlagSGr").getInt16();

			// used by unknown programmer setMockIntegerType(false);
			fVal1 = devCTAoptiHeat.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();
			fVal4 = devCTAoptiHeat.getVal("HeatPumpBase", "SourceTemp").getFloat32();

			LOG.info(String.format("  HeatPumpBase: HPOpStateAsEnum=" + oEnumListGet.getLiteral() + "/" + oEnumListGet.getOrdinal()
					+ ", HPOpStateAsEnum=" + oEnumListGet.getLiteral() + " / " + oEnumListGet.getOrdinal() + ", ErrorFlagSGr=" + iVal3));

			LOG.info(String.format("	 OutsideAir=" + fVal1 + " °C,  SupplyWaterTemp=" + fVal2 + "°C, ReturnSupplyWaterTemp=" + fVal3 + "°C, SourceTemp=" + fVal4 + "°C "));
			LOG.info(String.format(" "));

			EnumRecord oEnumListSet = devCTAoptiHeat.getVal("DomHotWaterCtrl", "DomHotWaterOpModeCmd").getEnum();
			fVal1 = devCTAoptiHeat.getVal("DomHotWaterCtrl", "ActDomHotWaterTemp").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointComfort").getFloat32();

			LOG.info(String.format("  DomHotWaterCtrl: DomHotWaterOpModeCmd=" + oEnumListSet.getLiteral() + "/" + oEnumListSet.getOrdinal()
					+ ",  ActDomHotWaterTemp=" + fVal1 + " °C,  DomHotWaterTempSetpointComfort=" + fVal2 + " °C "));
			LOG.info(String.format(" "));

			fVal1 = devCTAoptiHeat.getVal("PowerCtrl", "ActSpeed").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("PowerCtrl", "ActPowerACtot").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("PowerCtrl", "SpeedCtrlSetpoint").getFloat32();
			LOG.info(String.format("  PowerCtrl:  ActSpeed=" + fVal1 + " rpm, ActPowerACtot=" + fVal2 + " kW,  HeatBufferTempSetpointOffset=" + fVal3));
			LOG.info(String.format(" "));

			fVal1 = devCTAoptiHeat.getVal("BufferStorageCtrl", "HeatBufferTempStptOffset").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("BufferStorageCtrl", "ActHeatBufferTempLower").getFloat32();
			LOG.info(String.format("  BufferStorageCtrl : ActHeatBufferTemp=" + fVal2 + " °C,  ActHeatBufferTempLower=" + fVal3 + " °C   HeatBufferTempStptOffset=" + fVal1 + " °C"));
			LOG.info(String.format(" "));

			oEnumListSet = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd").getEnum();
			oEnumListGet = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "HeatCoolCtrlOpState").getEnum();
			LOG.info(String.format("  HeatCoolCtrl_1: HeatCoolCtrlOpModeCmd=" + oEnumListSet.getLiteral() + " / " + oEnumListSet.getOrdinal() + ",  HeatCoolCtrlOpState=" + oEnumListGet.getLiteral() + " / " + oEnumListGet.getOrdinal()));
			fVal1 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpoint").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "ReturnSupplyWaterTemp").getFloat32();

			LOG.info(String.format("       SupplyWaterTempSetpoint=" + fVal1 + " °C,  SupplyWaterTemp=" + fVal2 + " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "));
			LOG.info(String.format(" "));

			EnumRecord oEnumList = devCTAoptiHeat.getVal("SG-ReadyStates", "SGReadyState").getEnum();
			LOG.info(String.format("  SG-ReadyStates:  SGReadyState=" + oEnumList.getLiteral()));
			LOG.info(String.format(" "));


			fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyTot").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
			LOG.info(String.format("  EnergyMonitor: ThermalEnergyTot=" + fVal1 + " kWh, ThermalEnergyHeat=" + fVal2 + " kWh,  ThermalEnergyDomHotWater=" + fVal3 + " kWh"));
			fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACtot").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACheat").getFloat32();
			fVal3 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACDomHotW").getFloat32();
			LOG.info(String.format("                 ActiveEnergyACTot=" + fVal1 + " kWh, ActiveEnergyACHeat=" + fVal2 + " kWh, ActiveEnergyACDomHotW=" + fVal3 + " kWh"));


			fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "RuntimeHeating").getFloat32();
			fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "RuntimeCooling").getFloat32();

			// used by unknown programmer setMockIntegerType(true);
			long lVal = devCTAoptiHeat.getVal("EnergyMonitor", "NoOfStartupsCompressor").getInt32U();
			LOG.info(String.format("                 RuntimeHeating=" + fVal1 + " h, RuntimeCooling=" + fVal2 + " h, NoOfStartupsCompressor=" + lVal + " times"));


		} catch (Exception e) {
			LOG.info(String.format("Error reading value from device dev: " + e));
			e.printStackTrace();
			devCTAoptiHeat_Exceptions++;
		}
	}


	// -----------------------------------------------------------------------------------------------------------------------------
	// Device testing frame  Heliotherm
	// -----------------------------------------------------------------------------------------------------------------------------
	static void initHelioTCP(String aBaseDir, String aDescriptionFile) {

		try {

			Properties prop = new Properties();
			prop.put("tcp_address", "138.131.190.29");
			prop.put("tcp_port", "502");
			prop.put("SlaveID", "0");

			devHelioTCP = new SGrDeviceBuilder()
					//.useModbusGatewayFactory(new EasyModbusGatewayFactory())
					.properties(prop)
					.eid(Path.of(aBaseDir, aDescriptionFile))
					.build();

			devHelioTCP.connect();

		} catch (Exception e) {
			devHelioTCPIsOn = false;
			LOG.info(String.format("Error connecting device  " + e));
		}
	}


	static void tstHelioTCP() {

		double dfVal1 =  0.0;
		float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0, fVal5 = (float) 0.0;
		//float dVal1 = 0.0, dVal2 = 0.0;
		int iVal1 = 0, iVal2 = 0, iVal3 = 0, iVal4 = 0;
		long lVal = 0;
		boolean bVal1 = false, bVal2 = false, bVal3 = false;
		String sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 = "0.0";
		DataTypeProduct gdtValue = new DataTypeProduct();

		try {
			LOG.info(String.format("HeatPump HelioTCP"));
			Thread.sleep(200);

			if (runtimeCnt == 2) {
				// Testing Setters
				DataTypeProduct hpCmd = new DataTypeProduct();

				float fValStpt = (float) 0.0;
				Value hpval = Float64Value.of(fValStpt);
				LOG.info(String.format("######################  setting new values ######################"));
				// testing setters: one setting for a test run only recommended
				// read the device manual carefully before testing any setpoint


				// -------------------------------  SGR LEVEL 2 SETPOINTS	------------------------
				// control HeatPumpBase by HPOpModeCmd enum
	/*			// TODO:  check enumerations
				devHelioTCP.setVal("HeatPumpBase", "HPOpModeCmd", EnumValue.of("HEL_HP_AUTOMATIC"));
				LOG.info("  Setting  HeatPumpBase:HPOpModeCmd=HEL_HP_AUTOMATIC");

				// control HeatCoolCtrl_1 by enum HeatCoolCtrlOpModeCmd
				devHelioTCP.setVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd", EnumValue.of("HEL_HC_CONSTANT"));
				LOG.info("  Setting  HeatCoolCtrl_1:HeatCoolCtrlOpModeCmd=HEL_HC_CONSTANT");

				// control HeatCoolCtrl_2 by enum HeatCoolCtrlOpModeCmd
				devHelioTCP.setVal("HeatCoolCtrl_2", "HeatCoolCtrlOpModeCmd", EnumValue.of("HEL_HC_CONSTANT"));
				LOG.info("  Setting  HeatCoolCtrl_2:HeatCoolCtrlOpModeCmd=HEL_HC_CONSTANT");


				// control DomHotWaterCtrl by  DomHotWaterOpModeCmd
				devHelioTCP.setVal("DomHotWaterCtrl", "DomHotWaterOpModeCmd", EnumValue.of("HEL_DHW_ECO_OP"));
				LOG.info("  Setting  DomHotWaterCtrl:DomHotWOpModeCmd=HEL_DHW_ECO_OP");

				// control ReadyStates_bwp by enum SGReadyStateLv2Type
				devHelioTCP.setVal("SG-ReadyStates", "SGReadySrcSelect", EnumValue.of("SRC_src_CONTACTS"));
				LOG.info("  Setting  ReadyStates_bwp:SGReadySrcSelect=SRC_src_CONTACTS");
				devHelioTCP.setVal("SG-ReadyStates", "SGReadyOpModeCmd", EnumValue.of("HP_NORMAL"));
				LOG.info("  Setting  ReadyStates_bwp:SGReadyOpModeCmd=HP_NORMAL");

				// -------------------------------  SGR LEVEL 4 SETPOINTS -----------------------
				//
				// PowerCtrl new Setpoint in %
				fValStpt =  (float) 60.00f;
				devHelioTCP.setVal("PowerCtrl", "SpeedCtrlSetpt", Float64Value.of(50.0f));
				LOG.info(String.format("  Setting PowerCtrl: SpeedCtrlSetpt=" + fValStpt + "%%"));
				// PowerCtrl new Setpoint in kW
				devHelioTCP.setVal("PowerCtrl", "SpeedCtrlSetpoint", Float64Value.of(50.0f));
				LOG.info(String.format("  Setting PowerCtrl: SpeedCtrlSetpoint=" + fValStpt + "kW"));
				bVal1 = false;  // must be true to enable SpeedCtrlSetpoint
				devHelioTCP.setVal("PowerCtrl", "SpeedCtrlSetpointEnable", BooleanValue.of(bVal1));
				LOG.info(String.format("  Setting PowerCtrl: SpeedCtrlSetpointEnable=" + bVal1 ));

*/
				// HeatCoolCtrl_1 Setpoints
				fValStpt =  (float) 21.21f;
				devHelioTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt ));
				bVal1 = false;  // must be true to enable SpeedCtrlSetpoint
				devHelioTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEnable", BooleanValue.of(bVal1));
				LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempSetpointEnable=" + bVal1 + " , SupplyWaterTempSetpoint=" + fValStpt + "°C"));
/*
			   // HeatCoolCtrl_2 Setpoints
				fValStpt =  (float) 22.0f;
				devHelioTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpoint", Float64Value.of(fValStpt ));
				bVal1 = false;  // must be true to enable SpeedCtrlSetpoint
				devHelioTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEnable", BooleanValue.of(bVal1));
				LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempSetpointEnable=" + bVal1 + " , SupplyWaterTempSetpoint=" + fValStpt + "°C"));

				// DomHotWaterCtrl  DomHotWaterTempSetpointOffset
				fValStpt =  (float) 44.1f;
				devHelioTCP.setVal("DomHotWaterCtrl", "DomHotWaterTempSetpoint", Float64Value.of(fValStpt));
				bVal1 = false;  // must be true to enable SpeedCtrlSetpoint
				devHelioTCP.setVal("DomHotWaterCtrl", "DomHotWaterTempSetpointEnable", BooleanValue.of(bVal1));
				LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWaterTempSetpointEnable=" + bVal1 + ", DomHotWaterTempSetpoint=" + fValStpt + "°C"));


				// BufferStorageCtrl Offset-Setpoint
				fValStpt =  (float) 42.0f;
				devHelioTCP.setVal("BufferStorageCtrl", "HeatBufferTempSetpoint", Float64Value.of(fValStpt));
				bVal1 = false;
				devHelioTCP.setVal("BufferStorageCtrl", "HeatBufferTempSetpointEnable", BooleanValue.of(bVal1));
				LOG.info(String.format("  Setting BufferStorageCtrl: HeatBufferTempSetpointEnable=" + bVal1 + ", HeatBufferTempSetpoint=" + fValStpt + "°C"));


				//RoomTempCtrl_Z1
				fValStpt =  (float) 21.0f;
				devHelioTCP.setVal("RoomTempCtrl_Z1", "RoomZoneTempSetpoint", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting RoomTempCtrl_Z1: RoomZoneTempSetpoint=" + fValStpt + "°C"));

				//RoomTempCtrl_Z2
				fValStpt =  (float) 22.0f;
				devHelioTCP.setVal("RoomTempCtrl_Z2", "RoomZoneTempSetpoint", Float64Value.of(fValStpt));
				LOG.info(String.format("  Setting RoomTempCtrl_Z2: RoomZoneTempSetpoint=" + fValStpt + "°C"));

;
*/
			}

			// testing getter
			// used by unknown programmer setMockIntegerType(true);

			// HeatPumpBase
			EnumRecord opModeVal = devHelioTCP.getVal("HeatPumpBase", "HPOpModeCmd").getEnum();
			EnumRecord opStateVal = devHelioTCP.getVal("HeatPumpBase", "HPOpStateAsEnum").getEnum();
			bVal1 = devHelioTCP.getVal("HeatPumpBase", "ErrorFlagSGr").getBoolean();
			fVal1 = devHelioTCP.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();;
			fVal4 = devHelioTCP.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
			fVal5 = devHelioTCP.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();

			LOG.info(String.format(
					"  HeatPumpBase:    HPOpModeCmd=" + opModeVal.getLiteral() + "/" + opModeVal.getOrdinal()
							+ ",  HPOpStateAsEnum=" + opStateVal.getLiteral() + "/" + opStateVal.getOrdinal()
							+ ",  ErrorFlagSGr=" + bVal1 + ",  OutsideAirTemp=" + fVal1 + "°C"));
			LOG.info(String.format("    SupplyWaterTemp=" + fVal4 + "°C,  ReturnSupplyWaterTemp=" + fVal5 + " °C "));
			LOG.info(" ");

			// PowerCtrl
			fVal1 = devHelioTCP.getVal("PowerCtrl", "SpeedCtrlStpt").getFloat32();
			fVal2 = devHelioTCP.getVal("PowerCtrl", "ActSpeed").getFloat32();
			fVal3 = devHelioTCP.getVal("PowerCtrl", "ActPowerACtot").getFloat32();
			fVal4 = devHelioTCP.getVal("PowerCtrl", "PowerCtrlSetpoint").getFloat32();
			bVal1 = devHelioTCP.getVal("PowerCtrl", "PowerCtrlSetpointEnable").getBoolean();
			LOG.info(String.format("  PowerCtrl: PowerCtrlStp =" + fVal1 + " %% , ActSpeed=" + fVal2 + " %% , ActPowerACtot=" + fVal3 + " kW"));
			LOG.info(String.format("             SpeedCtrlSetpoint =" + fVal4 + " kW , SpeedCtrlSetpointEnable =" + bVal1 ));
			LOG.info(" ");

            // HeatCoolCtrl_1
			EnumRecord oEnumList= devHelioTCP.getVal("HeatCoolCtrl_1", "HeatCoolCtrlOpModeCmd").getEnum();
			bVal1 = devHelioTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpointEnable").getBoolean();
			fVal1 = devHelioTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempSetpoint").getFloat32();
			fVal2 = devHelioTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
			fVal3 = devHelioTCP.getVal("HeatCoolCtrl_1", "ReturnSupplyWaterTemp").getFloat32();
            for (String s : Arrays.asList(String.format(
                    "  HeatCoolCtrl_1:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
                            + ", SupplyWaterTempSetpointEnable =" + bVal1
                            + ", SupplyWaterTempSetpoint=" + fVal1 + " °C,  SupplyWaterTemp=" + fVal2
                            + " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "), " ")) {
                LOG.info(s);
            }

            // HeatCoolCtrl_2
			oEnumList= devHelioTCP.getVal("HeatCoolCtrl_2", "HeatCoolCtrlOpModeCmd").getEnum();
			bVal1 = devHelioTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpointEnable").getBoolean();
			fVal1 = devHelioTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempSetpoint").getFloat32();
			fVal2 = devHelioTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTemp").getFloat32();
			fVal3 = devHelioTCP.getVal("HeatCoolCtrl_2", "ReturnSupplyWaterTemp").getFloat32();
			LOG.info(String.format(
					"  HeatCoolCtrl_2:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral() + "/" + oEnumList.getOrdinal()
							+ " , SupplyWaterTempSetpointEnable =" + bVal1
							+ " , SupplyWaterTempSetpoint=" + fVal1 + " °C,  SupplyWaterTemp=" + fVal2
							+ " °C,  ReturnSupplyWaterTemp=" + fVal3 + " °C "));
			LOG.info(" ");

			// used by unknown programmer setMockIntegerType(true);
			// DomHotWaterCtrl
			fVal1 = devHelioTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpoint").getFloat32();
			fVal2 = devHelioTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointFb").getFloat32();
			fVal3 = devHelioTCP.getVal("DomHotWaterCtrl", "ActDomHotWaterTemp").getFloat32();
			bVal1 = devHelioTCP.getVal("DomHotWaterCtrl", "DomHotWaterTempSetpointEnable").getBoolean();
			LOG.info(String.format("  DomHotWaterCtrl:  DomHotWaterTempSetpoint=" + fVal1 + " °C,  DomHotWaterTempSetpointFb=" + fVal2 + " °C , SupplyWaterTempSetpointEnable =" + bVal1 + " , ActDomHotWaterTemp=" + fVal3 + " °C"));
			LOG.info(" ");


            // BufferStorageCtrl
			bVal1 = devHelioTCP.getVal("BufferStorageCtrl", "HeatBufferTempSetpointEnable").getBoolean();
			fVal1 = devHelioTCP.getVal("BufferStorageCtrl", "HeatBufferTempSetpoint").getFloat32();
			fVal2 = devHelioTCP.getVal("BufferStorageCtrl", "HeatBufferTempSetpointFb").getFloat32();
			fVal3 = devHelioTCP.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
			LOG.info(String.format("  BufferStorageCtrl: HeatBufferTempSetpointEnable=" + bVal1 + ", HeatBufferTempSetpoint=" + fVal1 + "°C , HeatBufferTempSetpointFb=" + fVal2 + "°C , ActHeatBufferTemp=" + fVal3 + "°C"));

            // RoomTempCtrl_Z1
			dfVal1 = devHelioTCP.getVal("RoomTempCtrl_Z1", "RoomZoneTempSetpoint").getFloat64();
			LOG.info(String.format("  Setting RoomTempCtrl_Z1: RoomZoneTempSetpoint=" + dfVal1 + "°C"));

			//RoomTempCtrl_Z2
			dfVal1 = devHelioTCP.getVal("RoomTempCtrl_Z2", "RoomZoneTempSetpoint").getFloat64();
			LOG.info(String.format("  Setting RoomTempCtrl_Z2: RoomZoneTempSetpoint=" + dfVal1 + "°C"));
			LOG.info(" ");

            // EnergyMonitor
			fVal1 = devHelioTCP.getVal("EnergyMonitor", "ActiveEnergyACtot").getFloat32();
			fVal2 = devHelioTCP.getVal("EnergyMonitor", "ActiveEnergyACheat").getFloat32();
			fVal3 = devHelioTCP.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
			fVal4 = devHelioTCP.getVal("EnergyMonitor", "ThermalEnergyTot").getFloat32();
			fVal5 = devHelioTCP.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
			LOG.info(String.format("  EnergyMonitor: ActiveEnergyACtot=" + fVal1 + " kWh, ActiveEnergyACheat=" + fVal2 + " kWh, ThermalEnergyDomHotWater=" + fVal3 + " kWh, ThermalEnergyTot=" + fVal4 + " kWh"));


			fVal1 = devHelioTCP.getVal("EnergyMonitor", "ActivePowerACtot").getFloat32();
			fVal2 = devHelioTCP.getVal("EnergyMonitor", "ThermalPower").getFloat32();
			fVal3 = devHelioTCP.getVal("EnergyMonitor", "RuntimeHeating").getFloat32();
			fVal4 = devHelioTCP.getVal("EnergyMonitor", "RuntimeDomHotWater").getFloat32();
			LOG.info(String.format("                 ThermalEnergyHeat=" + fVal5 + " kWh, ActivePowerACtot=" + fVal1 + " kWh, ThermalPower=" + fVal2 + " kW, RuntimeHeating=" + fVal3 + " kWh, RuntimeDomHotWater=" + fVal4 + " h"));
			LOG.info(" ");

		} catch (Exception  e) {
			// unknown programmer used multi-catch statement
			LOG.info(String.format("Error reading value from device dev: " + e));
			e.printStackTrace();
			devHelioTCP_Exceptions++;
		}
	}

}