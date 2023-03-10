package communicator.impl;

import java.net.URL;

import communicator.api.GenDeviceApi4Modbus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartgridready.ns.v0.SGrModbusDeviceFrame;

import communicator.helper.DeviceDescriptionLoader;
import de.re.easymodbus.adapter.GenDriverAPI4ModbusRTU;

public class GetValBlockTransferTester {
	
	private static final Logger LOG = LoggerFactory.getLogger(GetValArrayTester.class);
	
	private static final String XML_BASE_DIR = ""; 
	
	public static void main( String argv[] ) {				
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL deviceDesc = classloader.getResource("SGr_04_0014_0000_WAGO_SmartMeterV0.2.1-Blocktransfer.xml");
		
	try {
			DeviceDescriptionLoader<SGrModbusDeviceFrame> loader = new DeviceDescriptionLoader<>();
			SGrModbusDeviceFrame tstMeter = loader.load( XML_BASE_DIR, deviceDesc.getPath());
			
			GenDriverAPI4ModbusRTU mbRTU = new GenDriverAPI4ModbusRTU();
			mbRTU.initTrspService("COM3", 19200);

			GenDeviceApi4Modbus devWagoMeter = new SGrModbusDevice(tstMeter, mbRTU );
			
			try {	
				// set device address of devWagoMeter
					
				mbRTU.setUnitIdentifier((byte) 1);				
				
				
				// Voltages from device
				String voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				String voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				String voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 1: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1, voltage2, voltage3);
				
				// Voltages from cache (since lifetime 200ms)
				voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 2: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1, voltage2, voltage3);

				
				// new Voltages from Device because of sleep 1s.
				Thread.sleep(1000);
				voltage1 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L2");
				voltage2 = devWagoMeter.getVal("VoltageAC", "VoltageACL1-L3");
				voltage3 = devWagoMeter.getVal("VoltageAC", "VoltageACL2-L3");
				
				LOG.info("WAGO Meter Voltages AC run 3: L1-L2: {}V || L1-L3 {}V  || L2-L3: {}V", 
						voltage1, voltage2, voltage3);
				
			}
			catch ( Exception e)
			{
				System.out.println( "Error reading value from device. " + e);
				e.printStackTrace();
			}
		}
		catch ( Exception e )
		{
			System.out.println( "Error loading device description. " + e);
		}
	}
}
