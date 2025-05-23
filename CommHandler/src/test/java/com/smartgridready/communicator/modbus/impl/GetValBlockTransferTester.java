package com.smartgridready.communicator.modbus.impl;

import java.net.URL;
import java.util.Properties;

import com.smartgridready.ns.v0.DeviceFrame;

import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.modbus.api.GenDeviceApi4Modbus;
import com.smartgridready.driver.api.modbus.GenDriverAPI4ModbusFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartgridready.communicator.common.helper.DeviceDescriptionLoader;
import com.smartgridready.communicator.common.helper.DriverFactoryLoader;

@SuppressWarnings("java:S2925")
public class GetValBlockTransferTester {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetValArrayTester.class);
	
	private static final String XML_BASE_DIR = ""; 
	
	public static void main( String[] argv ) {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL deviceDesc = classloader.getResource("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-Blocktransfer.xml");
		
		try {
			Properties props = new Properties();
			props.setProperty("portName", "COM3");

			DeviceDescriptionLoader loader = new DeviceDescriptionLoader();
			DeviceFrame tstMeter = loader.load(XML_BASE_DIR, deviceDesc != null ? deviceDesc.getPath() : null, props);
			
			GenDriverAPI4ModbusFactory factory = DriverFactoryLoader.getModbusDriver();

			GenDeviceApi4Modbus devWagoMeter = new SGrModbusDevice(tstMeter, factory);
			devWagoMeter.connect();
			
			try {	
				// Voltages from device
				Value voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				Value voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				Value voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 1: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1.getFloat32(), voltage2.getFloat32(), voltage3.getFloat32());
				
				// Voltages from cache (since lifetime 200ms)
				voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 2: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1.getFloat32(), voltage2.getFloat32(), voltage3.getFloat32());

				
				// new Voltages from Device because of sleep 1s.
				Thread.sleep(1000);
				voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 3: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1.getFloat32(), voltage2.getFloat32(), voltage3.getFloat32());
				
			}
			catch ( Exception e)
			{
				System.out.println( "Error reading value from device. " + e);
				e.printStackTrace();
			}

			devWagoMeter.disconnect();
		}
		catch ( Exception e )
		{
			System.out.println( "Error loading device description. " + e);
		}
	}
}
