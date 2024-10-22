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

author: IBT/cb 2023/5/11
The purpose of this class is to offer a test environment to show Device Interoperabil-ity (WAGO METER vs ABB METER), Functional Profile Interoperability 
(CurrentAC WAGO METER vs ABB METER vs Bender Controller) and Protocol Interoperability ( Bender Con-troller Modbus/TCP  vs OCPP)


 */
package com.smartgridready.communicator.modbus.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// WIP/cb import com.smartgridready.ns.v0.SGrBool2BitRankType;
import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.Float32Value;
import com.smartgridready.communicator.modbus.api.GenDeviceApi4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;
import com.smartgridready.driver.api.modbus.Parity;
import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;

// debug an logger classes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SuppressWarnings("java:S2925")
public class IOPdemoBOX {


	private static final String XML_BASE_DIR = "../../../SGrSpecifications/XMLInstances/ExtInterfaces/";
	private static final Logger LOG = LoggerFactory.getLogger(IOPdemoBOX.class);

	// we need static definitions for performance reason
	//------------------------------------------------------
	// Modbus RTU devices

	private static GenDeviceApi4Modbus devIOPMeter = null;
	// we need a single driver instance for RTU and separate these by device addres
	private static GenDriverAPI4Modbus mbRTU = null;

	// Modbus TCP devices
	private static GenDeviceApi4Modbus devOMCCIWallbox = null;

	// test loop parameters
	private static int runtimeCnt = 0;
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	// Exception Counters
	private static int devIOPMeterExceptions = 0;
	private static int devOMCCIWallboxExceptions = 0;

	// device selection
	private static boolean  devIOPMeterTestIsOn = true;
	private static boolean  devOMCCIWallboxTestIsOn = false;


	// DEVICE FILE SELECTION
	//public static String eidMeter = "SGr_04_0014_0000_WAGO_SmartMeterV0.2.1.xml";
	//public static byte rtuMeterAddr = (byte)7;
	public static String eidMeter = "SGr_04_0016_xxxx_ABBMeterV0.2.1.xml";
	public static byte rtuMeterAddr = (byte)1;

	public static void main( String argv[] ) {

		try {

			//DeviceDescriptionLoader loader = new DeviceDescriptionLoader();

			// Modbus RTU uses a single driver  (tailored to easymodbus)
			GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
			mbRTU = factory.createRtuTransport("COM4", 9600, Parity.NONE);	// for mobile RTU Interface
			//mbRTU = factory.createRtuTransport("COM9", 9600, Parity.NONE);   // for Office RTU Interface
			mbRTU.connect();

			// TestBox
			if (devIOPMeterTestIsOn)  {
				LOG.info(" -init " + eidMeter + " as IOP demonstrator @: " + dtf.format(LocalDateTime.now())+ " ");
				initIOPMeter(XML_BASE_DIR, eidMeter,rtuMeterAddr);
			}

			if (devOMCCIWallboxTestIsOn) {
				//TODO: complete and use OMCCI EI.xml
				LOG.info(" -init devOMCCIWallbox as Modbus/TCP demonstrator @:" + dtf.format(LocalDateTime.now())+ " ");
				initOMCCIWallbox(XML_BASE_DIR, "SGr_04_0005_xxxx_GARO_WallboxV0.2.1.xml");
			}


			// **************************   Start demo loop    **********************************

			try {

				for (runtimeCnt = 0;runtimeCnt<30000;runtimeCnt++)
				{

					// loop data & test reporting
					LOG.info("\n\n>>>Time=" + dtf.format(LocalDateTime.now())+" ------> LOOP=" +	runtimeCnt );
					if (devIOPMeterTestIsOn)     LOG.info("IOPMeter     exceptions=" +  devIOPMeterExceptions+ ",");
					if (devOMCCIWallboxTestIsOn) LOG.info("OMCCIWallbox exceptions=" + devOMCCIWallboxExceptions+ ",");

					//_____Next loop
					if (devIOPMeterTestIsOn)    tstIOPMeter();
					if (devOMCCIWallboxTestIsOn) tstOMCCIWallbox();
					Thread.sleep(5000);  // show last block for ccc  milliseconds
				}
			}

			catch ( Exception e)
			{
				LOG.info( "Error reading value from device " + e);
				e.printStackTrace();
			}
		}

		catch ( Exception e )
		{
			LOG.info( "Error loading device description. " + e);
		}

	}




	// -----------------------------------------------------------------------------------------------------------------------------
	// Device IOPMeter Testing
	// -----------------------------------------------------------------------------------------------------------------------------
	static byte rtuAddr=0;

	static void initIOPMeter(String aBaseDir, String aDescriptionFile, byte deviceAddr ) {

		try {
			rtuAddr = deviceAddr;
			DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
			DeviceFrame tstDesc = loader.load(aBaseDir, aDescriptionFile);
			devIOPMeter =  new SGrModbusDevice(tstDesc, mbRTU );

		}

		catch ( Exception e )
		{
			LOG.info( "Error loading device description. " + e);
		}
	}

	static void tstIOPMeter()
	{
		double fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0;
		String  sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 ="0.0";

		try {
			mbRTU.setUnitIdentifier(rtuAddr);
			LOG.info("@:Testing IOPMeterData: ");
			Thread.sleep(25);
			fVal1 = devIOPMeter.getVal("VoltageAC", "VoltageL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("VoltageAC", "VoltageL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("VoltageAC", "VoltageL3").getFloat64();
			Thread.sleep(10);
			fVal4 = devIOPMeter.getVal("Frequency", "Frequency").getFloat64();
			LOG.info(String.format("  VoltageAC         L1=%3.2f V,    L2=%3.2f V,      L3=%3.2f V   Frequency=%2.2f Hz",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			fVal1 = devIOPMeter.getVal("VoltageAC", "VoltageACL1-L2").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("VoltageAC", "VoltageACL1-L3").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("VoltageAC", "VoltageACL2-L3").getFloat64();
			LOG.info(String.format("  VoltageAC         L1-L2=%3.2f V  L1-L3=%3.2f V  L2-L3=%3.2f V", fVal1, fVal2, fVal3));
			Thread.sleep(10);

			fVal1 = devIOPMeter.getVal("CurrentAC", "CurrentACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("CurrentAC", "CurrentACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("CurrentAC", "CurrentACL3").getFloat64();
			LOG.info(String.format("  CurrentAC         L1=%2.3f A,     L2=%2.3f A,     L3=%3.3f A", fVal1, fVal2, fVal3));
			Thread.sleep(10);

	  				/*  not implemented yet with ABB
	  				fVal4 = devIOPMeter.getVal("PowerFactor", "PowerFactor").getFloat64();
	  				Thread.sleep(10);
	  				fVal1 = devIOPMeter.getVal("PowerFactor", "PowerFactorL1").getFloat64();
	  				Thread.sleep(10);
	  				fVal2 = devIOPMeter.getVal("PowerFactor", "PowerFactorL2").getFloat64();
	  				Thread.sleep(10);
	  				fVal3 = devIOPMeter.getVal("PowerFactor", "PowerFactorL3").getFloat64();
	  				LOG.info(String.format("  Powerfactor       L1=%1.2f        L2=%1.2f        L3=%1.2f       total=%1.2f",fVal1,fVal2,fVal3,fVal4));
	  				Thread.sleep(10);
	  				*/
			fVal4 = devIOPMeter.getVal("ActiveEnergyAC", "ActiveEnergyACtot").getFloat64();
			Thread.sleep(10);
			fVal1 = devIOPMeter.getVal("ActiveEnergyAC", "ActiveEnergyACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ActiveEnergyAC", "ActiveEnergyACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ActiveEnergyAC", "ActiveEnergyACL3").getFloat64();
			LOG.info(String.format("  ActiveEnergyAC    L1=%3.2f kWh    L2=%3.2f kWh     L3=%3.2f kWh   total=%3.2f kWh",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			fVal4 = devIOPMeter.getVal("ActivePowerAC", "ActivePowerACtot").getFloat64();
			Thread.sleep(10);
			fVal1 = devIOPMeter.getVal("ActivePowerAC", "ActivePowerACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ActivePowerAC", "ActivePowerACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ActivePowerAC", "ActivePowerACL3").getFloat64();
			LOG.info(String.format("  ActivePowerAC     L1=%3.2f W       L2=%3.2f W       L=%3.2f W       total=%3.2f W",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			fVal4 = devIOPMeter.getVal("ReactivePowerAC", "ReactivePowerACtot").getFloat64();
			Thread.sleep(10);
			fVal1 = devIOPMeter.getVal("ReactivePowerAC", "ReactivePowerACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ReactivePowerAC", "ReactivePowerACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ReactivePowerAC", "ReactivePowerACL3").getFloat64();
			LOG.info(String.format("  ReactivePowerAC   L1=%3.2f var     L2=%3.2f var     L3=%3.2f var    total=%3.2f var",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			fVal4 = devIOPMeter.getVal("ApparentPowerAC", "ApparentPowerACtot").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ApparentPowerAC", "ApparentPowerACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ApparentPowerAC", "ApparentPowerACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ApparentPowerAC", "ApparentPowerACL3").getFloat64();
			LOG.info(String.format("  ApparentPowerAC   L1=%3.2f va      L2=%3.2f va      L3=%3.2f va     total=%3.2f va",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			Thread.sleep(10);
			fVal4 = devIOPMeter.getVal("PowerQuadrant", "PwrQuadACtot").getFloat64();
			Thread.sleep(10);
			fVal1 = devIOPMeter.getVal("PowerQuadrant", "PwrQuadACL1").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("PowerQuadrant", "PwrQuadACL2").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("PowerQuadrant", "PwrQuadACL3").getFloat64();
			LOG.info(String.format("  PowerQuadrant     L1=%1.0f           L2=%1.0f            L3=%1.0f         total=%1.0f",fVal1,fVal2,fVal3,fVal4));
			Thread.sleep(10);

			fVal1 = devIOPMeter.getVal("ActiveEnerBalanceAC", "ActiveImportAC").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ActiveEnerBalanceAC", "ActiveExportAC").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ActiveEnerBalanceAC", "ActiveNetAC").getFloat64();
			LOG.info(String.format("  ActiveEnerBalanceAC       Import=%3.2f kwh     Export=%3.2f kWh      Netto=%3.2f kWh",fVal1,fVal2,fVal3));
			Thread.sleep(10);

			fVal1 = devIOPMeter.getVal("ReactiveEnerBalanceAC", "ReactiveImportAC").getFloat64();
			Thread.sleep(10);
			fVal2 = devIOPMeter.getVal("ReactiveEnerBalanceAC", "ReactiveExportAC").getFloat64();
			Thread.sleep(10);
			fVal3 = devIOPMeter.getVal("ReactiveEnerBalanceAC", "ReactiveNetAC").getFloat64();
			LOG.info(String.format("  ReactiveEnerBalanceAC     Import=%3.2f kvarh   Export=%3.2f kvarh    Netto=%3.2f kvarh",fVal1,fVal2,fVal3));


		}
		catch ( Exception e)
		{
			devIOPMeterExceptions++;
			LOG.info( "Error reading value from device devIOPMeter:" +  e);
			e.printStackTrace();
		}
	}



	// -----------------------------------------------------------------------------------------------------------------------------
	// OMCCI Wallbox Test
	// -----------------------------------------------------------------------------------------------------------------------------
	static void initOMCCIWallbox(String aBaseDir, String aDescriptionFile ) {
		//TODO: complete and use full OMCCI EI.xml
		try {
			DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
			DeviceFrame tstDesc = loader.load(aBaseDir, aDescriptionFile);

			// Modbus TCP uses a driver instance per device (Sockets, tailored to easymodbus)
			GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
			GenDriverAPI4Modbus mbWbOMCCI= factory.createTcpTransport("192.168.1.183",502);
			devOMCCIWallbox = new SGrModbusDevice(tstDesc, mbWbOMCCI);
			mbWbOMCCI.connect();

		}

		catch ( Exception e )
		{
			LOG.info( "Error loading device description. " + e);
		}
	}


	static void tstOMCCIWallbox()
	{
		double fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0;
		String  sVal1 = "0.0", sVal2 = "0.0";
		int     iVal1  = 0;
		float CurtailCurrent;


		try {
			LOG.info("\n@:Testing  OMCCIWallbox: ");
			if ((runtimeCnt%60)== 0)
			{
				CurtailCurrent = (float) 7.0 + (float)((runtimeCnt/60)%4) ;
				devOMCCIWallbox.setVal("Curtailment", "HemsCurrentLimit", Float32Value.of(CurtailCurrent));
				LOG.info("  Setting HemsCurrentLimit to :     " + CurtailCurrent + "  ");
			}
			fVal1 = devOMCCIWallbox.getVal("CurrentAC", "CurrentACL1").getFloat64();
			Thread.sleep(200);
			fVal2 = devOMCCIWallbox.getVal("CurrentAC", "CurrentACL2").getFloat64();
			Thread.sleep(200);
			fVal3 = devOMCCIWallbox.getVal("CurrentAC", "CurrentACL3").getFloat64();
			Thread.sleep(200);

			EnumRecord oEnumList = devOMCCIWallbox.getVal("EVSEState", "EV-StatusCode").getEnum();
			Thread.sleep(200);
			LOG.info("  EV-StatusCode:                    " + oEnumList.getLiteral() + "  ");

			oEnumList = devOMCCIWallbox.getVal("EVSEState", "ocppState").getEnum();
			Thread.sleep(200);
			LOG.info("  OCPP-StatusCode:                  " + oEnumList.getLiteral() + "  ");
			LOG.info("  CurrentAC[A]                      I[L1]= " + fVal1 + ",  I[L2] = "  + fVal2 + ",  I[L3] = "  + fVal3 + "  ");

			fVal1 = devOMCCIWallbox.getVal("ActivePowerAC", "ActivePowerACL1").getFloat64();
			Thread.sleep(200);
			fVal2 = devOMCCIWallbox.getVal("ActivePowerAC", "ActivePowerACL2").getFloat64();
			Thread.sleep(200);
			fVal3 = devOMCCIWallbox.getVal("ActivePowerAC", "ActivePowerACL3").getFloat64();
			Thread.sleep(200);
			LOG.info("  PowerAC[kW]:                      P[1L]= " + fVal1 + ",  P[L2] = "  + fVal2 + ",  P[L3] = "  + fVal3 + "  ");

			fVal1 = devOMCCIWallbox.getVal("ActiveEnergyAC", "ActiveEnergyACL1").getFloat64();
			Thread.sleep(200);
			fVal2 = devOMCCIWallbox.getVal("ActiveEEnergyAC", "ActiveEnergyACL2").getFloat64();
			Thread.sleep(200);
			fVal3 = devOMCCIWallbox.getVal("ActiveEEnergyAC", "ActiveEnergyACL3").getFloat64();
			Thread.sleep(200);
			LOG.info("  EnergyAC[kWh] L1/L2/L3:           W[1] = " + fVal1 + "  W[2] = "  + fVal2 + "  W[3] = "  + fVal3 + "  ");

			boolean bVal = devOMCCIWallbox.getVal("EVState", "isSmartEV15118").getBoolean();
			Thread.sleep(200);
			EnumRecord enumRecord = devOMCCIWallbox.getVal("EVState", "EVCCID").getEnum();
			Thread.sleep(200);
			LOG.info("  EVState  support (ISO/IEC 15118): " + sVal1 + ",    EVCCID = " + enumRecord.getLiteral() + "  ");

			fVal1 = devOMCCIWallbox.getVal("Curtailment", "SafeCurrent").getFloat64();
			Thread.sleep(200);
			fVal2 = devOMCCIWallbox.getVal("Curtailment", "HemsCurrentLimit").getFloat64();
			Thread.sleep(200);
			fVal3 = devOMCCIWallbox.getVal("Curtailment", "HWCurrentLimit").getFloat64();
			Thread.sleep(200);
			iVal1 = (int)devOMCCIWallbox.getVal("Curtailment", "maxReceiveTimeSec").getInt16U();
			Thread.sleep(200);
			LOG.info("  Curtailment:                      SafeCurrent = " + fVal1 + "  HemsCurrentLimit = "  + fVal2 + "  HWCurrentLimit = "  + fVal3 +  "  maxReceiveTimeSec = "  + iVal1 +"  ");

		}
		catch ( Exception e)
		{
			devOMCCIWallboxExceptions++;
			LOG.info( "Error reading value from device devOMCCIWallbox:" + e);
			e.printStackTrace();
		}
	}

}
