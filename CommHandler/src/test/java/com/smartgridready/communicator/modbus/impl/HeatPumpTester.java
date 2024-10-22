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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.smartgridready.ns.v0.DataTypeProduct;
import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.common.api.values.BooleanValue;
import com.smartgridready.communicator.common.api.values.EnumRecord;
import com.smartgridready.communicator.common.api.values.EnumValue;
import com.smartgridready.communicator.common.api.values.Float64Value;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.driver.api.modbus.GenDriverAPI4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//proprietary definitions

import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;


@SuppressWarnings("java:S2925")
public class HeatPumpTester {

	private static final Logger LOG = LoggerFactory.getLogger(HeatPumpTester.class);
	
	
	private static final String XML_BASE_DIR="../../../SGrSpecifications/XMLInstances/ExtInterfaces/"; 
	
	// we need static definitions for performance reason
	//------------------------------------------------------
	
	// Modbus RTU devices
	private static SGrModbusDevice devRTU_IOP=null;
	private static SGrModbusDevice devHovalRTU=null;
	
	// we need a single driver instance for RTU and separate these by device address
	private static GenDriverAPI4Modbus mbRTU=null;
	
	// Modbus TCP devices
	private static SGrModbusDevice devTCP_IOP=null;
	private static SGrModbusDevice devStiebelISG=null;
	private static SGrModbusDevice devCTAoptiHeat = null;
	private static SGrModbusDevice devHovalTCP = null;
	
	// test loop parameters
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private static int runtimeCnt=0;
	// Exception Counters
	private static int devRTU_IOP_Exceptions=0;
	private static int devTCP_IOP_Exceptions=0;
	private static int devStiebel_ISGExcpetions=0;
	private static int devHovalRTU_Exceptions=0;
	private static int devHovalTCP_Exceptions=0;
	private static int devCTAoptiHeat_Exceptions=0;
	// device selection
	private static boolean  devRTU_IOPIsOn=false;
	private static boolean  devTCP_IOPIsOn=true; 
	private static boolean  devStiebelISGIsOn=false;
	private static boolean  devCTAoptiHeatIsOn=true;
	private static boolean  devHovalTCPIsOn=false;

	// Set the mockModbusDriver to new GenDriverAPI4ModbusRTUMock() to mock the real devices.
	//private static GenDriverAPI4Modbus  mockModbusDriver = new GenDriverAPI4ModbusRTUMock();
	private static GenDriverAPI4Modbus  mockModbusDriver = null;

	public static void main( String argv[] ) {			

		
		try {
			DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
		    
			LOG.info(String.format("\n\n\n"));
			LOG.info(String.format("****************************************  SESSION STARTS ***********************************"));
			LOG.info("*  Time=" + dtf.format(LocalDateTime.now()) + "                                                                *");	
			LOG.info(String.format("********************************************************************************************"));
			
			if (devRTU_IOPIsOn)
			{
			  // Modbus RTU uses a single driver  (tailored to easymodbus)
			  GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
			  mbRTU=factory.createRtuTransport("COM9");
			  mbRTU.connect();			
			}
			
			if (devStiebelISGIsOn) initStiebelISG(XML_BASE_DIR, "SGr_04_0015_xxxx_StiebelEltron_HeatPumpV0.2.1.xml");			
			if (devCTAoptiHeatIsOn) initCTAoptiHeat(XML_BASE_DIR, "SGr_04_0033_0000_CTA_HeatPumpV0.2.1.xml");
			if (devHovalTCPIsOn) initHovalTCP (XML_BASE_DIR, "SGr_04_0017_xxxx_HOVAL_HeatPumpV0.2.1.xml");
			
			// **************************   Start device Testing   **********************************						
   			
			try {
				
				for (runtimeCnt=0;runtimeCnt<3000;runtimeCnt++)
				{
				   // loop data & test reporting
				   Thread.sleep(10000);  // show last block for ccc  milliseconds
				    LOG.info(String.format("\n\n"));
				    LOG.info(">>> Time=" + dtf.format(LocalDateTime.now()));	
					LOG.info(String.format("------> LOOP=" + runtimeCnt ));		
					
					if (devStiebelISGIsOn && (devStiebel_ISGExcpetions !=0 ))  
						LOG.info(String.format("  StiebelISG Excpetions =" + devStiebel_ISGExcpetions));
					if (devCTAoptiHeatIsOn && (devCTAoptiHeat_Exceptions!=0)) 
						LOG.info(String.format("  CTAoptiHeat Excpetions=" + devCTAoptiHeat_Exceptions));
					if (devHovalTCPIsOn && (devHovalTCP_Exceptions!=0) )  
						LOG.info(String.format("HovalTCP Excpetions=" + devHovalTCP_Exceptions ));

					//Next loop 
					if (devStiebelISGIsOn)  tstStiebelISG();	
					if (devCTAoptiHeatIsOn)  tstCTAoptiHeat();	
					if (devHovalTCPIsOn) tstHovalTCP();

			}

		}
		catch ( Exception e)
		{
				 LOG.info(String.format("Error reading value from device " + e));
				e.printStackTrace();
		}
	 }
	 catch ( Exception e )
	 {
			 LOG.info(String.format("Error loading device description. " + e));
	 }									
  }
	

	
	
	
	   // -----------------------------------------------------------------------------------------------------------------------------	
	   // Device testing frame
	   // -----------------------------------------------------------------------------------------------------------------------------	
		static void initHovalTCP(String aBaseDir, String aDescriptionFile ) {				
			
			DeviceFrame tstDesc = null;
			
			try {	
				DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
				tstDesc=loader.load(aBaseDir, aDescriptionFile);
				
				// replace device specific for RTU
				//devHovalRTU= new SGrModbusDevice(tstDesc, mbRTU );
			}
			
			catch ( Exception e )
			{
				devHovalTCPIsOn = false;
				LOG.info(String.format("Error loading device description. " + e));
			}	
			
			try {
				// // replace device specific for TCP  (easymodus uses Driver instance per device)
				GenDriverAPI4Modbus mbHovalTCP = mockModbusDriver;
				if (mockModbusDriver == null) {
					GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
					mbHovalTCP = factory.createTcpTransport("192.168.0.35", 502);
					//mbHovalTCP = factory.createTcpTransport("192.168.1.55", 502);
				}
				devHovalTCP=new SGrModbusDevice(tstDesc, mbHovalTCP);							
				//setting Hoval Lab 
				mbHovalTCP.connect();
			}
			
			catch ( Exception e )
			{
				devHovalTCPIsOn = false;
				LOG.info(String.format("Error connecting device  " + e));
			}		
		}
		
		
		static void tstHovalTCP()
		{

			float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float)  0.0, fVal4 = (float)  0.0, fVal5= (float)  0.0;
			//float dVal1 = 0.0, dVal2 = 0.0;
			int  iVal1=0, iVal2=0, iVal3=0, iVal4=0;
			long lVal=0;
			boolean bVal1=false,bVal2=false,bVal3=false;
			String  sVal1="0.0", sVal2="0.0", sVal3="0.0", sVal4 ="0.0";
			DataTypeProduct gdtValue = new DataTypeProduct();
				
			try {			
				 LOG.info(String.format("HeatPump HovalTCP"));
				 Thread.sleep(200);
				
				if (runtimeCnt == 2)
				{
                  // Testing Setters
				  DataTypeProduct  hpCmd = new DataTypeProduct();

				  float fValStpt = (float) 0.0 ;	
				  Value hpval = Float64Value.of(fValStpt);
				   LOG.info(String.format("######################  setting new values ######################"));
				   // testing setters: one setting for a test run only recommended
				   // read the device manual carefully before testing any setpoint
				   

				  // -------------------------------  SGR LEVEL 2 SETPOINTS	------------------------			 
				  // control HeatPumpBase by hovHPOpModeCmd enum
				  devHovalTCP.setVal("HeatPumpBase", "hovHPOpModeCmd", EnumValue.of("HP_AUTOMATIC"));
				  LOG.info("  Setting  HeatPumpBase:hovHPOpModeCmd=HP_AUTOMATIC");
				  
				  // control HeatCoolCtrl_1 by enum hovHeatCoolCtrlOpModeCmd
				  devHovalTCP.setVal("HeatCoolCtrl_1", "hovHeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
				  LOG.info("  Setting  HeatCoolCtrl_1:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");
				  
				  // control HeatCoolCtrl_2 by enum hovHeatCoolCtrlOpModeCmd
				  devHovalTCP.setVal("HeatCoolCtrl_2", "hovHeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
				  LOG.info("  Setting  HeatCoolCtrl_2:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");
				  
				  // control HeatCoolCtrl_1 by enum hovHeatCoolCtrlOpModeCmd
				  devHovalTCP.setVal("HeatCoolCtrl_3", "hovHeatCoolCtrlOpModeCmd", EnumValue.of("HC_CONSTANT"));
					LOG.info("  Setting  HeatCoolCtrl_3:hovHeatCoolCtrlOpModeCmd=HC_CONSTANT");
				  
				  // control DomHotWCtrl by  DomHotWOpMode
				  devHovalTCP.setVal("DomHotWaterCtrl", "hovDomHotWOpModeCmd", EnumValue.of("DHW_ECO_OP"));
				  LOG.info("  Setting  DomHotWaterCtrl:hovDomHotWOpModeCmd=DHW_ECO_OP");
				 		
				  // control ReadyStates_bwp by enum SGReadyStateLv2Type
				  devHovalTCP.setVal("SG-ReadyStates_bwp", "hovSGReadySrcSelect", EnumValue.of("SRC_src_CONTACTS"));
				  LOG.info("  Setting  ReadyStates_bwp:hovSGReadySrcSelect=SRC_src_CONTACTS");
				  devHovalTCP.setVal("SG-ReadyStates_bwp", "SGReadyOpModeCmd", EnumValue.of("HP_NORMAL"));
				  LOG.info("  Setting  ReadyStates_bwp:SGReadyOpModeCmd=HP_NORMAL");
				
                 // -------------------------------  SGR LEVEL 4 SETPOINTS -----------------------		
				 // PowerCtrl new Setpoint in %
		         devHovalTCP.setVal("PowerCtrl", "PowerCtrlStpt",Float64Value.of(50.0f));
		         LOG.info(String.format("  Setting PowerCtrl: PowerCtrlStp=" +fValStpt + "%%"));
				   
		         // HeatCoolCtrl Setpoints
		         devHovalTCP.setVal("HeatCoolCtrl_1", "SupplyWaterTempStpt", Float64Value.of(20.1f));
		         devHovalTCP.setVal("HeatCoolCtrl_2", "SupplyWaterTempStpt",Float64Value.of(0.1f));
		         devHovalTCP.setVal("HeatCoolCtrl_3", "SupplyWaterTempStpt",Float64Value.of(0.2f));
		         LOG.info(String.format("  Setting HeatCoolCtrl: SupplyWaterTempStpt _1="+ fValStpt +" �C , _2=" + (fValStpt+(float)0.1) +" �C , _3=" + (fValStpt+(float)0.2)+" �C" ));

			     // BufferStorageCtrl Offset-Setpoint Heating
			     devHovalTCP.setVal("BufferStorageCtrl", "HeatBufferTempStptOffs",Float64Value.of(1.3f));
			     LOG.info(String.format("  Setting BufferStorageCtrl: HeatBufferTempStptOffs=" + fValStpt + " �C" ));
			     
			      // BufferStorageCtrl Offset-Setpoint Cooling
			     devHovalTCP.setVal("BufferStorageCtrl", "CoolBufferTempStptOffs",Float64Value.of(-1.3f));
			     LOG.info(String.format("  Setting BufferStorageCtrl: CoolBufferTempStptOffs=" + fValStpt + " �C" ));
			    
				   
				 // DomHotWaterCtrl  DomHotWTempStptComf Setpoint
			     devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWTempStptComf",Float64Value.of(55.0f));
			     LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWTempStptComf=" + fValStpt + " �C" ));
				   
				 // DomHotWaterCtrl  DomHotWTempStptComf Setpoint
			     devHovalTCP.setVal("DomHotWaterCtrl", "DomHotWTempStptEco",Float64Value.of(55.5f));
			     LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWTempStptEco=" + fValStpt + " �C" ));
			     LOG.info("\n");
			}
 
        // testing getter
		setMockIntegerType(true);
		EnumRecord opModeVal = devHovalTCP.getVal("HeatPumpBase", "hovHPOpModeCmd").getEnum();
		EnumRecord opStateVal = devHovalTCP.getVal("HeatPumpBase", "hovHPOpState").getEnum();
		bVal1=devHovalTCP.getVal("HeatPumpBase", "ErrorNrSGr").getBoolean();
		fVal1=devHovalTCP.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
		fVal2=devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTempStpt").getFloat32();
		fVal3=devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTempStptFb").getFloat32();
		fVal4=devHovalTCP.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
		fVal5=devHovalTCP.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();

		LOG.info(String.format(
				"  HeatPumpBase:    hovHPOpModeCmd=" + opModeVal.getLiteral() + "/" + opModeVal.getOrdinal()
						+ ",  HPOpState=" + opStateVal.getLiteral()+"/" + opStateVal.getOrdinal()
						+ ",  ErrorNrSGr=" + bVal1 + ",  OutsideAir=" + fVal1 +"�C"));

		LOG.info(String.format("    SupplyWaterTempStpt=" + fVal2 +" �C,  SupplyWaterTempStptFb=" + fVal3 + "�C, SupplyWaterTemp=" + fVal4 +  "�C,  ReturnSupplyWaterTemp=" + fVal5 +  " �C "));
		LOG.info(" ");

		setMockIntegerType(true);
		EnumRecord hovHotWOpMode =  devHovalTCP.getVal("DomHotWaterCtrl", "hovDomHotWOpModeCmd").getEnum();
		EnumRecord hovHotWOpState = devHovalTCP.getVal("DomHotWaterCtrl", "hovDomHotWState").getEnum();
		fVal1=devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWTempStptOffs").getFloat32();
		fVal2=devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWTempStptComf").getFloat32();
		fVal3=devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWTempStptEco").getFloat32();
		fVal4=devHovalTCP.getVal("DomHotWaterCtrl", "ActDomHotWTemp").getFloat32();
		fVal5=devHovalTCP.getVal("DomHotWaterCtrl", "DomHotWTempStptFb").getFloat32();

		LOG.info(String.format(
				"  DomHotWCtrl: hovDomHotWOpModeCmd=" + hovHotWOpMode.getLiteral() +"/"+ hovHotWOpMode.getOrdinal()
						+ ",  DomHotWState=" +  hovHotWOpState.getLiteral()+"/"+ hovHotWOpState.getOrdinal().intValue()
						+ ", DomHotWTempStptComf=" + fVal2 + " �C"));

		LOG.info(String.format("        DomHotWTempStptComf=" + fVal2 + " �C,  DomHotWTempStptEco=" + fVal3 + " �C,   ActDomHotWaterTemp=" + fVal4 + " �C,  DomHotWTempStptFb=" + fVal5 + " �C "));  
		LOG.info(" ");

		fVal1=devHovalTCP.getVal("PowerCtrl", "PowerCtrlStpt").getFloat32();
		fVal2=devHovalTCP.getVal("PowerCtrl", "ActSpeed").getFloat32();
		fVal3=devHovalTCP.getVal("BufferStorageCtrl", "CoolBufferTempStptOffs").getFloat32();
		fVal3=devHovalTCP.getVal("PowerCtrl", "ActPowerACtot").getFloat32();
		LOG.info(String.format("  PowerCtrl: PowerCtrlStp =" + fVal1 + " %% , ActSpeed="  + fVal2 + " %% , ActPowerACtot=" + fVal3 +" kW"  ));  
		LOG.info(" ");

		EnumRecord oEnumList = devHovalTCP.getVal("BufferStorageCtrl", "hovActBufferState").getEnum();
		fVal1=devHovalTCP.getVal("BufferStorageCtrl", "ActBufferTempStptFb").getFloat32();
		fVal2=devHovalTCP.getVal("BufferStorageCtrl","HeatBufferTempStptOffs").getFloat32();
		fVal3=devHovalTCP.getVal("BufferStorageCtrl","CoolBufferTempStptOffs").getFloat32();
		LOG.info(String.format("  BufferStorageCtrl: hovActBufferState=" + oEnumList.getLiteral()+"/"+ oEnumList.getOrdinal()
				+ ",  ActBufferWaterTempStptFb=" + fVal1 +",  HeatBufferTempStptOffs="  + fVal2 +  " �C,  CoolBufferTempStptOffs=" + fVal3 + " �C"));
		fVal1=devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
		fVal2=devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTempUpper").getFloat32();
		fVal3=devHovalTCP.getVal("BufferStorageCtrl", "ActHeatBufferTempLower").getFloat32();
		fVal4=devHovalTCP.getVal("BufferStorageCtrl", "ActCoolBufferTempUpper").getFloat32();
		fVal5=devHovalTCP.getVal("BufferStorageCtrl", "ActCoolBufferTempLower").getFloat32();
		LOG.info(String.format("      ActHeatBufferTemp=" + fVal1 +",  ActHeatBufferTempUpper="  + fVal2 +  " �C,  ActHeatBufferTempLower=" + fVal3 + " �C,  ActCoolBufferTempUpper=" + fVal4 + " �C,  ActCoolBufferTempLower=" + fVal5 + " �C "));
		LOG.info(" "); 	
	

		oEnumList=devHovalTCP.getVal("HeatCoolCtrl_1", "hovHeatCoolCtrlOpModeCmd").getEnum();
		EnumRecord oEnumList1 =devHovalTCP.getVal("HeatCoolCtrl_1", "hovHeatCoolOpState").getEnum();
		fVal1=devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
		fVal2=devHovalTCP.getVal("HeatCoolCtrl_1", "SupplyWaterTempStpt").getFloat32();
		fVal3=devHovalTCP.getVal("HeatCoolCtrl_1", "ReturnSupplyWaterTemp").getFloat32();
		LOG.info(String.format(
				"  HeatCoolCtrl_1:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral()+"/"+oEnumList.getOrdinal()
						+" ,  HeatCoolOpState: "  + oEnumList1.getLiteral()+"/"+ oEnumList1.getOrdinal()
						+ " SupplyWaterTemp=" + fVal1 + " �C,  SupplyWaterTempStpt=" + fVal2
						+ " �C,  ReturnSupplyWaterTemp=" + fVal3 + " �C "));
		LOG.info(" "); 

		oEnumList=devHovalTCP.getVal("HeatCoolCtrl_2", "hovHeatCoolCtrlOpModeCmd").getEnum();
		oEnumList1=devHovalTCP.getVal("HeatCoolCtrl_2", "hovHeatCoolOpState").getEnum();
		fVal1=devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTemp").getFloat32();
		fVal2=devHovalTCP.getVal("HeatCoolCtrl_2", "SupplyWaterTempStpt").getFloat32();
		fVal3=devHovalTCP.getVal("HeatCoolCtrl_2", "ReturnSupplyWaterTemp").getFloat32();
		LOG.info(String.format(
				"  HeatCoolCtrl_2:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral()+"/"+oEnumList.getOrdinal()
						+" ,  HeatCoolOpState: "  + oEnumList1.getLiteral()+"/"+ oEnumList1.getOrdinal()
						+ ", SupplyWaterTemp=" + fVal1 + " �C,  SupplyWaterTempStpt=" + fVal2 + " �C,  ReturnSupplyWaterTemp=" + fVal3 + " �C "));
		LOG.info(" "); 

		oEnumList=devHovalTCP.getVal("HeatCoolCtrl_3", "hovHeatCoolCtrlOpModeCmd").getEnum();
		oEnumList1=devHovalTCP.getVal("HeatCoolCtrl_3", "hovHeatCoolOpState").getEnum();
		fVal1=devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTemp").getFloat32();
		fVal2=devHovalTCP.getVal("HeatCoolCtrl_3", "SupplyWaterTempStpt").getFloat32();
		fVal3=devHovalTCP.getVal("HeatCoolCtrl_3", "ReturnSupplyWaterTemp").getFloat32();
		LOG.info(String.format("  HeatCoolCtrl_3:  HeatCoolCtrlOpModeCmd=" + oEnumList.getLiteral()+"/"+oEnumList.getOrdinal()
				+ " ,  HeatCoolOpState: "  + oEnumList1.getLiteral()+ "/" + oEnumList1.getOrdinal()
				+ " SupplyWaterTemp=" + fVal1 + " �C,  SupplyWaterTempStpt=" + fVal2
				+ " �C,  ReturnSupplyWaterTemp=" + fVal3 + " �C "));
		LOG.info(" "); 
		
		fVal1=devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyTot").getFloat32();
		fVal2=devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
		fVal3=devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyCool").getFloat32();
		fVal4=devHovalTCP.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
		LOG.info(String.format("  EnergyMonitor: ThermalEnergyTot=" + fVal1 + " kWh, ThermalEnergyHeat=" + fVal2 + " kWh, ThermalEnergyCool=" + fVal3 + " kWh, ThermalEnergyDomHotWaterater=" + fVal4 + " kWh"));    

		fVal1=devHovalTCP.getVal("EnergyMonitor", "ActiveEnergyACtot").getFloat32();
		fVal2=devHovalTCP.getVal("EnergyMonitor", "ActivePowerACtot").getFloat32();
		fVal3=devHovalTCP.getVal("EnergyMonitor", "RuntimeCompressor").getFloat32();
		lVal=devHovalTCP.getVal("EnergyMonitor", "NrOfStartupsCompressor").getInt32U();
		LOG.info(String.format("         ActiveEnergyACtot="+ fVal1 + " kWh,  ActivePowerACtot=" + fVal2 + " kWh, RuntimeCompressor="+ fVal3 + " h,  NrOfStartupsCompressor="+ lVal+"  times"));    
		LOG.info(" "); 


		oEnumList=devHovalTCP.getVal("SG-ReadyStates_bwp", "SGReadyOpModeCmd").getEnum();
		oEnumList1=devHovalTCP.getVal("SG-ReadyStates_bwp", "hovSGReadySrcSelect").getEnum();
		LOG.info(String.format("  SG-ReadyStates_bwp SGReadyCmd="+ oEnumList.getLiteral()+"/" + oEnumList.getOrdinal()
				+ ",   hovSGReadySrcSelect="+ oEnumList1.getLiteral() +"/" + oEnumList1.getOrdinal()));

	}
	catch ( Exception e)
	{
		 LOG.info(String.format("Error reading value from device dev: " + e));
		e.printStackTrace();
		devHovalTCP_Exceptions++;
	}
  }	
		
			

	   // -----------------------------------------------------------------------------------------------------------------------------	
	   // Device testing frame Stiebel
	   // -----------------------------------------------------------------------------------------------------------------------------	
		static void initStiebelISG(String aBaseDir, String aDescriptionFile ) {				
			
			DeviceFrame tmpDesc=null;

			try {	
				DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
				tmpDesc=loader.load(aBaseDir, aDescriptionFile);	
			}
			
			catch ( Exception e )
			{
				 devStiebelISGIsOn = false;
				 LOG.info(String.format("Error loading device description. " + e));
			}		
			
			if (tmpDesc != null)
			{
			  try {
				
				  // replace device specific for TCP  (easymodus uses Driver instance per device)
				  GenDriverAPI4Modbus mbStiebelISG = mockModbusDriver;
				  if (mockModbusDriver == null) {
					  GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
					  mbStiebelISG = factory.createTcpTransport("192.168.1.55", 502);
				  }
				  devStiebelISG=new SGrModbusDevice(tmpDesc, mbStiebelISG);							
				  mbStiebelISG.connect();
			   }
			
			  catch ( Exception e )
			  {
				 devStiebelISGIsOn = false;
				 LOG.info(String.format("Error while connecting device. " + e));
			  }	
				
			}
			
		}
		
		static void tstStiebelISG()
		{
			float fVal1=(float) 0.0, fVal2=(float) 0.0, fVal3=(float) 0.0, fVal4=(float) 0.0, fVal5=(float) 0.0;
			double dVal1 = 0.0, dVal2 = 0.0;
			int iVal1, iVal2=0, iVal3=0, iVal4=0;
			Value bitmapVal1;
			Value bitmapVal2;
			long lVal=0;
			boolean bVal1=false,bVal2=false,bVal3=false;
			String  sVal1="0.0", sVal2="0.0", sVal3="0.0", sVal4 ="0.0";

				
			try {
				LOG.info(String.format("HeatPump Stiebel-Eltron"));
				Thread.sleep(25);
				
				if (runtimeCnt == 2)
				{
					/*
					DataTypeProduct  hpval = new DataTypeProduct();
				   LOG.info(String.format("######################  setting new values ######################"));
				   // testing setters: one setting for a test run only recommended
				   // read the device manual carefully before testing any setpoint
				  // control by HPOpMode enum
				  devStiebelISG.setVal("HeatPumpBase", "HPOpModeCmd", EnumValue.of("WP_PROG_OP"));
				
				 
				  //Setpoints Comfort
				  devStiebelISG.setVal("HeatCoolCtrl_1", "SupplyWaterTempStptComf",Float64Value.of(29.1f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempStptComf=" + fVal1));
				  devStiebelISG.setVal("HeatCoolCtrl_2", "SupplyWaterTempStptComf",Float64Value.of(29.2f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempStptComf=" + fVal1));
				  devStiebelISG.setVal("DomHotWaterCtrl", "DomHotWTempStptComf",Float64Value.of(28.3f));
				  LOG.info(String.format("  Setting DomHotWaterCtrl: DomHotWTempStptComf=" + fVal1));
				 

				  //Setpoints Eco
				  devStiebelISG.setVal("HeatCoolCtrl_1", "SupplyWaterTempStptEco",Float64Value.of(25.1f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempStptEco=" + fVal1));
				  devStiebelISG.setVal("HeatCoolCtrl_2", "SupplyWaterTempStptEco",Float64Value.of(25.8));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: SupplyWaterTempStptEco=" + fVal1));
				  devStiebelISG.setVal("DomHotWCtrl", "DomHotWTempStptEco",Float64Value.of(26.2f));
				  LOG.info(String.format("  Setting HeatCoolCtrl_2: DomHotWTempStptEco=" + fVal1));

				
				// enable or disable SG-Ready
				LOG.info("  Setting ReadyStates_bwp:SGReadyEnabled=" + false);
				devStiebelISG.setVal("SG-ReadyStates_bwp", "SGReadyEnabled", BooleanValue.of(false));
				
							
				/// control SG-Ready by enum SGReadyStateLv2Type
				devStiebelISG.setVal("SG-ReadyStates_bwp", "SGReadyOpModeCmd", EnumValue.of("HP_NORMAL"));
				LOG.info("  Setting  ReadyStates_bwp:SGReadyOpModeCmd=HP_NORMAL");
			
			
				// control SG-Ready by contacts

				devStiebelISG.setVal("SG-ReadyStates_bwp", "SGReadyInp1isON", BooleanValue.of(false));
				LOG.info("  Setting SG-ReadyStates_bwp:SGReadyInp1isON=false",BooleanValue.of(false));
                devStiebelISG.setVal("SG-ReadyStates_bwp", "SGReadyInp2isON", BooleanValue.of(false));
                LOG.info("  Setting SG-ReadyStates_bwp:SGReadyInp2isON=false");
					 */
				}

				// testing
				setMockIntegerType(true);
				EnumRecord oEnumList= devStiebelISG.getVal("HeatPumpBase", "HPOpModeCmd").getEnum();
				bitmapVal1 = devStiebelISG.getVal("HeatPumpBase", "HPOpState");
				bitmapVal2 = devStiebelISG.getVal("HeatPumpBase", "stiHPOpState");
				bVal1=devStiebelISG.getVal("HeatPumpBase", "ErrorNrSGr").getBoolean();
				fVal1=devStiebelISG.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
				fVal2=devStiebelISG.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
				fVal3=devStiebelISG.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();
				fVal4=devStiebelISG.getVal("HeatPumpBase", "SourceTemp").getFloat32();
				LOG.info(String.format("  HeatPumpBase:      HPOpModeCmd=" + oEnumList.getLiteral() + ",  HPOpState=" + iVal2 + ",  ErrorNrSGr=" + bVal1 + ",  OutsideAir=" + fVal1 +" �C, SupplyWaterTemp=" + fVal2 +  "�C,  ReturnSupplyWaterTemp=" + fVal3 +  " �C,   SourceTemp=" + fVal4 +" �C "));
				// oEnumList.unsetSgrHPOpMode(); // TODO what is this supposed to do?

				LOG.info("    HPOpState bits set = {}", Integer.toBinaryString(bitmapVal1.getInt32()));
				bitmapVal1.getBitmap().forEach( (literal, value) -> LOG.info("        {} = {}", literal, value));


				LOG.info("    stiHPOpState bits set = {}", Integer.toBinaryString(bitmapVal2.getInt32()));
				bitmapVal2.getBitmap().forEach((literal, value) -> LOG.info("        {} = {}", literal, value));
				LOG.info(" ");
				
				fVal1=devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWTempStptComf").getFloat32();
				fVal2=devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWTempStptEco").getFloat32();
				fVal3=devStiebelISG.getVal("DomHotWaterCtrl", "DomHotWTempStptFb").getFloat32();
				fVal4=devStiebelISG.getVal("DomHotWaterCtrl", "ActDomHotWaterTemp").getFloat32();
				 LOG.info(String.format("  DomHotWCtrl:  DomHotWTempStptComf=" + fVal1 + " �C,  DomHotWTempStptEco=" + + fVal2 + " �C,  DomHotWTempStptFb=" + fVal3 + " �C,  ActDomHotWaterTemp=" + fVal4 + " �C "));  
				 LOG.info(String.format(" "));	
				fVal1=devStiebelISG.getVal("BufferStorageCtrl", "ActHeatBufferTempStptFb").getFloat32();
				fVal2=devStiebelISG.getVal("BufferStorageCtrl", "ActHeatBufferTemp").getFloat32();
				 LOG.info(String.format("  BufferStorageCtrl: ActHeatBufferTempStptFb=" + fVal1 + " �C,    ActHeatBufferTemp="  + fVal2 +  " �C"));  
				 LOG.info(String.format(" "));	
				fVal1=devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempStptComf").getFloat32();
				fVal2=devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempStptEco").getFloat32();
				fVal3=devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTempStptFb").getFloat32();
				fVal4=devStiebelISG.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
				 LOG.info(String.format("  HeatCoolCtrl_1:    SupplyWaterTempStptComf=" + fVal1 + " �C,  SupplyWaterTempStptEco=" + fVal2 +  " �C,  SupplyWaterTempStptFb=" + fVal3 + " �C,  SupplyWaterTemp=" + fVal4 + " �C "));  
				 LOG.info(String.format(" "));				 ;
				fVal1=devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempStptComf").getFloat32();
				fVal2=devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempStptEco").getFloat32();
				fVal3=devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTempStptFb").getFloat32();
				fVal4=devStiebelISG.getVal("HeatCoolCtrl_2", "SupplyWaterTemp").getFloat32();
				 LOG.info(String.format("  HeatCoolCtrl_2:    SupplyWaterTempStptComf=" + fVal1 + " �C,  SupplyWaterTempStptEco=" + fVal2 +  " �C,  SupplyWaterTempStptFb=" + fVal3 + " �C,  SupplyWaterTemp=" + fVal4 + " �C "));  
				 LOG.info(String.format(" "));

				oEnumList = devStiebelISG.getVal("SG-ReadyStates_bwp", "SGReadyState").getEnum();
				bVal1=devStiebelISG.getVal("SG-ReadyStates_bwp", "SGReadyEnabled").getBoolean();
				bVal2=devStiebelISG.getVal("SG-ReadyStates_bwp", "SGReadyInp1isON").getBoolean();
				bVal3=devStiebelISG.getVal("SG-ReadyStates_bwp", "SGReadyInp2isON").getBoolean();
				LOG.info(String.format(
						"  SGReady-bwp:      SGReadyState=" + oEnumList.getLiteral() + ",  SGReadyEnabled=" + bVal1
						+ ",  SGReadyInp1isON=" + bVal2 + ",  SGReadyInp2isON=" + bVal3 ));
				// oEnumList.unsetSgreadyStateLv2(); // TODO what is this supposed to do?
				LOG.info(String.format(" "));	
				fVal1=devStiebelISG.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
				fVal2=devStiebelISG.getVal("EnergyMonitor", "ActiveEnergyACheat").getFloat32();
				fVal3=devStiebelISG.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
				fVal4=devStiebelISG.getVal("EnergyMonitor", "ActivelEnergyACdomWater").getFloat32();
				LOG.info(String.format(" "));
				LOG.info(String.format("  EnergyMonitor ThermalEnergyHeat=" + fVal1 + " kWh,  ActiveEnergyACheat="+ fVal2 + " kWh,  ThermalEnergyDomHotWater="+ fVal3 + " kWh,  ActivelEnergyACdomWater="+ fVal4 + " kWh"));  
				  
				
				fVal1=devStiebelISG.getVal("EnergyMonitor", "RuntimeHeating").getFloat32();
				fVal2=devStiebelISG.getVal("EnergyMonitor", "RuntimeDomHotWater").getFloat32();
				 LOG.info(String.format("  EnergyMonitor RuntimeHeating=" + fVal1 + " h,  RuntimeDomHotWater="+ fVal2 + " h"));  
			 				
//	             TBC: launches illegal address @31 , no other start data found
//	 				lVal=devStiebelISG.getValByGDPType("EnergyMonitor", "NrOfStartupsCompressor").getInt16U();
//				+ fVal3 + " h,  NrOfStartupsCompressor="  + lVal+" times

		}

			catch ( Exception e)
			{
				 LOG.info(String.format("Error reading value from device devStiebelISG: " + e));
				e.printStackTrace();
				devStiebel_ISGExcpetions++;
			}
	
}
	
		

		
	    // USED TO COPY / PASTE ADDITIONAL TEST DEVICES
		   // -----------------------------------------------------------------------------------------------------------------------------	
		   // Device testing frame CTA
		   // -----------------------------------------------------------------------------------------------------------------------------	
			static void initCTAoptiHeat(String aBaseDir, String aDescriptionFile ) {
				
				DeviceFrame tstDesc=null;
				
				try {	
					DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
					tstDesc=loader.load(aBaseDir, aDescriptionFile);	
				}
				
				catch ( Exception e )
				{
					devCTAoptiHeatIsOn = false;
					LOG.info(String.format("Error loading device description. " + e));
				}	
					
				try {
					// // replace device specific for TCP  (easymodus uses Driver instance per device)
					GenDriverAPI4Modbus mbCTAoptiHeat = mockModbusDriver;
					if (mockModbusDriver == null) {
						GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getImplementation(GenDriverAPI4ModbusFactory.class);
						mbCTAoptiHeat = factory.createTcpTransport("192.168.1.55",502);
					}
					devCTAoptiHeat=new SGrModbusDevice(tstDesc, mbCTAoptiHeat);							
					//mbCTAoptiHeat.connect();
					
					// set back remote control enabler
					devCTAoptiHeat.setVal("DeviceInformation","ctaRemoteHCTempSetptEnable",BooleanValue.of(false) );  
					
					
				}
				
				catch ( Exception e )
				{
					 LOG.info(String.format("Error connecting device. " + e));
				}		
			}
			
			static void tstCTAoptiHeat()
			{
				float fVal1=(float) 0.0, fVal2=(float) 0.0, fVal3=(float) 0.0, fVal4=(float) 0.0 ;
				String  sVal1="0.0", sVal2="0.0", sVal3="0.0", sVal4 ="0.0";
				int  iVal1=0, iVal2=0, iVal3=0, iVal4=0;
				long  lVal1=0, lVal2=0, lVal3=0, lVal4=0;
				boolean bVal1 = false;
				
				//Setters
				float fValStpt=(float)  runtimeCnt *  (float) 0.1;
				DataTypeProduct gdtValue= new DataTypeProduct();
				DataTypeProduct  modeCmd = new DataTypeProduct();
				
					try {	
						// if RTU is used, set address here
						// mbRTU.setUnitIdentifier((byte) 7);
					     
						LOG.info(String.format("Testing CTAoptiHeat"));
						Thread.sleep(25);
						

						if (runtimeCnt == 2)
						{
							DataTypeProduct  hpval = new DataTypeProduct();
								
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
						  devCTAoptiHeat.setVal("HeatCoolCtrl_1","ctaRemoteHCTempSetptEnable",BooleanValue.of(true) );  
						  LOG.info(String.format("ctaRemoteHCTempSetptEnable enabled"));
						  devCTAoptiHeat.setVal("HeatCoolCtrl_1","SupplyWaterTempStpt",Float64Value.of(fVal1));
						  LOG.info(String.format("Setting HeatCoolCtrl_1: SupplyWaterTempStpt="  + fVal1 )); 
						  */   
						  
						  /* set HeatCoolOpModeCmd  
						  // remark:  ctaRemoteHCTempSetptEnable must be false,  ctaRemoteCtrlTimeSec must be 0
						  String sLiteral =  "HC_HEAT_ECO";
  						  devCTAoptiHeat.setVal("HeatCoolCtrl_1", "ctaHCOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info(String.format("Setting ctaHCOpModeCmd="+ sLiteral.intern())); 
						  */   
						  
						  
						  /*  Set DomHotWSetPoint
						  fVal1 = (float) 51.1;
						  Thread.sleep(25);
						  devCTAoptiHeat.setVal("DomHotWaterCtrl", "DomHotWTempStpt",Float64Value.of(fVal1));
						  */
						  
						  /*  Set DomHotWCtrl
						  String sLiteral = "DHW_OFF";						   
						  devCTAoptiHeat.setVal("DomHotWaterCtrl", "ctaDomHotWOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info(String.format("Setting ctaDomHotWOpMode ="+ sLiteral.intern())); 
					      */
						  
						  
						  /* set power
						  fVal1 = (float) 38.0;
						  devCTAoptiHeat.setVal("PowerCtrl","PowerCtrlStpt",Float32Value.of(fVal1));
						  LOG.info(String.format("Setting PowerCtrl: PowerCtrlStpt="  + fVal1));
						  */
						  
						  /* set storage buffer
						  fVal1 = (float) 25.0;
						  devCTAoptiHeat.setVal("BufferStorageCtrl","HeatBufferTempStptOffset",Float64Value.of(fVal1));
						  LOG.info(String.format("Setting BufferStorageCtrl: HeatBufferTempStptOffset="  + fVal1));
					      */

						  
						  /* control SG-Ready by enum SGReadyStateLv2Type
						  String sLiteral = "HP_NORMAL";
						  devCTAoptiHeat.setVal("SG-ReadyStates_bwp", "SGReadyOpModeCmd", EnumValue.of(sLiteral.intern()));
						  LOG.info("  Setting  ReadyStates_bwp:SGReadyCmd="+ sLiteral.intern()); 
						  */
						}
						
					      
			            // testing getters
						setMockIntegerType(true);
						iVal1 =  (int)devCTAoptiHeat.getVal("DeviceInformation","ctaRemoteCtrlTimeSec").getInt16U();
						boolean bRem = true;
						if (iVal1==0) bRem = false; 
						LOG.info(String.format("  ctaRemoteCtrlTimeSec: "  + bRem + "  ctaRemoteCtrlTimeSec=" + iVal1));

						EnumRecord oEnumListSet= devCTAoptiHeat.getVal("HeatPumpBase", "ctaHPOpModeCmdFb").getEnum();
						EnumRecord oEnumListGet = devCTAoptiHeat.getVal("HeatPumpBase", "ctaHPOpState").getEnum();
						iVal3 = (int)devCTAoptiHeat.getVal("HeatPumpBase", "ErrorNrSGr").getInt16();

						setMockIntegerType(false);
						fVal1 = devCTAoptiHeat.getVal("HeatPumpBase", "OutsideAirTemp").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("HeatPumpBase", "SupplyWaterTemp").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("HeatPumpBase", "ReturnSupplyWaterTemp").getFloat32();
						fVal4 = devCTAoptiHeat.getVal("HeatPumpBase", "SourceTemp").getFloat32();

						LOG.info(String.format("  HeatPumpBase: ctaHPOpModeCmdFb="+ oEnumListSet.getLiteral() + "/" + oEnumListSet.getOrdinal()
								+ ", ctaHPOpState=" + oEnumListGet.getLiteral() + " / " + oEnumListGet.getOrdinal() + ", ErrorNrSGr=" + iVal3));

						LOG.info(String.format("	 OutsideAir="+ fVal1 +" �C,  SupplyWaterTemp=" + fVal2 + "�C, ReturnSupplyWaterTemp="   + fVal3 +  "�C, SourceTemp="   + fVal4 +  "�C " ));    
						LOG.info(String.format(" "));

						oEnumListSet=  devCTAoptiHeat.getVal("DomHotWaterCtrl", "ctaDomHotWOpModeCmd").getEnum();
						fVal1 = devCTAoptiHeat.getVal("DomHotWaterCtrl", "ActDomHotWTemp").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("DomHotWaterCtrl", "DomHotWTempStpt").getFloat32();

						LOG.info(String.format("  DomHotWaterCtrl: ctaDomHotWOpModeCmd=" + oEnumListSet.getLiteral() + "/" +   oEnumListSet.getOrdinal()
								+ ",  ActDomHotWaterTemp="  + fVal1 + " �C,  DomHotWTempStpt=" + fVal2 + " �C "));
						LOG.info(String.format(" "));	
						
						fVal1 = devCTAoptiHeat.getVal("PowerCtrl", "ActSpeed").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("PowerCtrl", "ActPowerACtot").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("PowerCtrl","PowerCtrlStpt").getFloat32();
						LOG.info(String.format("  PowerCtrl:  ActSpeed="  + fVal1 + " rpm, ActPowerACtot=" + fVal2 + " kW,  PowerCtrlStpt=" + fVal3 ));  
						LOG.info(String.format(" "));	

						fVal1 = devCTAoptiHeat.getVal("BufferStorageCtrl", "HeatBufferTempStptOffset").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("BufferStorageCtrl", "ActHeatBufferTempUpper").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("BufferStorageCtrl", "ActHeatBufferTempLower").getFloat32();
						LOG.info(String.format("  BufferStorageCtrl : ActHeatBufferTempUpper=" + fVal2 + " �C,  ActHeatBufferTempLower=" + fVal3  + " �C   HeatBufferTempStptOffset=" + fVal1 + " �C"));  
						LOG.info(String.format(" "));	
						
						oEnumListSet = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "ctaHCOpModeCmd").getEnum();
						oEnumListGet = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "ctaHCOpState").getEnum();
						LOG.info(String.format("  HeatCoolCtrl_1: ctaHCOpModeCmd="+ oEnumListSet.getLiteral() + " / " + oEnumListSet.getOrdinal() + ",  ctaHCOpState=" + oEnumListGet.getLiteral() + " / " + oEnumListGet.getOrdinal()));   				
						fVal1 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "SupplyWaterTempStpt").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "SupplyWaterTemp").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("HeatCoolCtrl_1", "ReturnSupplyWaterTemp").getFloat32();
						
						LOG.info(String.format("       SupplyWaterTempStpt=" + fVal1 + " �C,  SupplyWaterTemp=" + fVal2 + " �C,  ReturnSupplyWaterTemp=" + fVal3 + " �C " ));  			
						LOG.info(String.format(" "));						
					
						EnumRecord oEnumList = devCTAoptiHeat.getVal("SG-ReadyStates_bwp", "SGReadyState").getEnum();
						LOG.info(String.format("  SGReady-bwp:  SGReadyState=" + oEnumList.getLiteral()));
						LOG.info(String.format(" "));	
						

						fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyTot").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyHeat").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("EnergyMonitor", "ThermalEnergyDomHotWater").getFloat32();
						LOG.info(String.format("  EnergyMonitor: ThermalEnergyTot="  + fVal1 + " kWh, ThermalEnergyHeat=" + fVal2 + " kWh,  ThermalEnergyDomHotWater=" + fVal3 + " kWh")); 
						fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACtot").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACheat").getFloat32();
						fVal3 = devCTAoptiHeat.getVal("EnergyMonitor", "ActiveEnergyACDomHotW").getFloat32();
						LOG.info(String.format("                 ActiveEnergyACTot="  + fVal1 + " kWh, ActiveEnergyACHeat=" + fVal2 + " kWh, ActiveEnergyACDomHotW=" + fVal3 + " kWh"));   


						fVal1 = devCTAoptiHeat.getVal("EnergyMonitor", "RuntimeHeating").getFloat32();
						fVal2 = devCTAoptiHeat.getVal("EnergyMonitor", "RuntimeCooling").getFloat32();

						setMockIntegerType(true);
						long lVal = devCTAoptiHeat.getVal("EnergyMonitor", "NrOfStartupsCompressor").getInt32U();
						LOG.info(String.format("                 RuntimeHeating="  + fVal1 + " h, RuntimeCooling=" + fVal2 + " h, NrOfStartupsCompressor=" + lVal + " times"));   

						
					}
					catch ( Exception e)
					{
						 LOG.info(String.format("Error reading value from device dev: " + e));
						e.printStackTrace();
						devCTAoptiHeat_Exceptions++;
					}
			}
	
    // USED TO COPY / PASTE ADDITIONAL TEST DEVICES
	   // -----------------------------------------------------------------------------------------------------------------------------	
	   // Device testing frame
	   // -----------------------------------------------------------------------------------------------------------------------------	
		static void initdevXXXXX(String aBaseDir, String aDescriptionFile ) {				
			
			DeviceFrame tstDesc;
			
			try {	
				DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
				tstDesc=loader.load(aBaseDir, aDescriptionFile);	
				
				// replace device specific for RTU
				//add devXXXX= new SGrModbusDevice(tstDesc, mbRTU );
			}
			
			catch ( Exception e )
			{
				    // devXXXXXON = false; 
					 LOG.info(String.format("Error loading device description. " + e));
			}	
			
			try {
				// // replace device specific for TCP  (easymodus uses Driver instance per device)						
				// GenDriverAPI4ModbusTCP mbXXXXX= new GenDriverAPI4ModbusTCP();
				// devXXXXX=new SGrModbusDevice(tstDesc, mbWmbXXXXX);							
				// mbXXXXX.initDevice("192.168.1.182",502);
					
			}
			
			catch ( Exception e )
			{
				 //devXXXXXON = false; 
				 LOG.info(String.format("Error connecting device  " + e));
			}		
		}
		
		
		static void tstdevXXXXX() {

			float fVal1 = (float) 0.0, fVal2 = (float) 0.0, fVal3 = (float) 0.0, fVal4 = (float) 0.0, fVal5 = (float) 0.0;
			double dVal1 = 0.0, dVal2 = 0.0;
			int iVal1 = 0, iVal2 = 0, iVal3 = 0, iVal4 = 0;
			long lVal = 0;
			boolean bVal1 = false, bVal2 = false, bVal3 = false;
			String sVal1 = "0.0", sVal2 = "0.0", sVal3 = "0.0", sVal4 = "0.0";


			try {
				LOG.info(String.format("HeatPump xxxxxx"));
				Thread.sleep(25);

				if (runtimeCnt==2) {
					/*
					DataTypeProduct hpval = new DataTypeProduct();

					LOG.info(String.format("######################  setting new values ######################"));
					// testing setters: one setting for a test run only recommended
					// read the device manual carefully before testing any setpoint
					// control by HPOpMode enum

					devXXXXX.setVal("HeatPumpBase", "HPOpModeCmd", EnumValue.of("WP_PROG_OP"));

					//Setpoints Comfort
					devXXXXX.setValByGDPType("HeatCoolCtrl_1", "SupplyWaterTempStptComf", Float64Value.of(31.1f));
					LOG.info(String.format("  Setting HeatCoolCtrl_1: SupplyWaterTempStptComf=" + fVal1));
					*/
				}
			} catch (Exception e) {
				LOG.info(String.format("Error reading value from device dev: " + e));
				e.printStackTrace();
				// add Exception counter here
			}

			// ******************  ENUM Sample  ************************************ //
            /*
			EnumRecord state = devXXXXX.getVal("HeatPumpBase", "ctaHPOpState").getEnum();
			LOG.info(String.format("  Setting  HeatPumpBase:HPOpModeCmd=", state));
             */
		}


	private static void setMockIntegerType(boolean isInteger) {
			if (mockModbusDriver instanceof GenDriverAPI4ModbusRTUMock) {
				((GenDriverAPI4ModbusRTUMock)mockModbusDriver).setIsIntegerType(isInteger);
			}
		}
}
