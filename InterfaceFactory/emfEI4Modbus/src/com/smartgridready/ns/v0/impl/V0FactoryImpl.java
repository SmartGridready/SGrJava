/**
 */
package com.smartgridready.ns.v0.impl;

import com.smartgridready.ns.v0.*;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class V0FactoryImpl extends EFactoryImpl implements V0Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static V0Factory init() {
		try {
			V0Factory theV0Factory = (V0Factory)EPackage.Registry.INSTANCE.getEFactory(V0Package.eNS_URI);
			if (theV0Factory != null) {
				return theV0Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new V0FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public V0FactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case V0Package.CONTACT_API_INTERFACE_DESC_TYPE: return createContactAPIInterfaceDescType();
			case V0Package.DOCUMENT_ROOT: return createDocumentRoot();
			case V0Package.FUNCTIONAL_PROFILE_TYPE: return createFunctionalProfileType();
			case V0Package.HEADER_ENTRY: return createHeaderEntry();
			case V0Package.HEADER_LIST: return createHeaderList();
			case V0Package.IP_ADDR_TYPE: return createIpADDRType();
			case V0Package.MODBUS_JMES_PATH_TYPE: return createModbusJMESPathType();
			case V0Package.NETWORK_CONNECTION_STATE_TYPE: return createNetworkConnectionStateType();
			case V0Package.RESPONSE_QUERY: return createResponseQuery();
			case V0Package.REST_SERVICE_CALL: return createRestServiceCall();
			case V0Package.RTU_DEV_INSTANCE_TYPE: return createRTUDevInstanceType();
			case V0Package.RT_UTRSP_SRV_INSTANCE_TYPE: return createRTUtrspSrvInstanceType();
			case V0Package.RTU_TYPE: return createRTUType();
			case V0Package.SGR_ACCESS_PROTECTION_ENABLED_TYPE: return createSGrAccessProtectionEnabledType();
			case V0Package.SGR_ATTR4_GENERIC_TYPE: return createSGrAttr4GenericType();
			case V0Package.SGR_ATTR4_MODBUS_TYPE: return createSGrAttr4ModbusType();
			case V0Package.SGR_ATTR4_REST_API_TYPE: return createSGrAttr4RestAPIType();
			case V0Package.SGR_BASIC_GEN_DATA_POINT_TYPE_TYPE: return createSGrBasicGenDataPointTypeType();
			case V0Package.SGR_BITMAPPER_LIST_TYPE: return createSGrBitmapperListType();
			case V0Package.SGR_CHANGE_LOG: return createSGrChangeLog();
			case V0Package.SGR_CONTACT_API_DEVICE_FRAME: return createSGrContactAPIDeviceFrame();
			case V0Package.SGR_CONTACT_API_FUNCTIONAL_PROFILE_TYPE: return createSGrContactAPIFunctionalProfileType();
			case V0Package.SGR_DATA_POINT_BASE_TYPE: return createSGrDataPointBaseType();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE: return createSGrDataPointDescriptionType();
			case V0Package.SGR_DEVICE_BASE_TYPE: return createSGrDeviceBaseType();
			case V0Package.SGR_DEVICE_PROFILE_TYPE: return createSGrDeviceProfileType();
			case V0Package.SGR_ENUM_LIST_TYPE: return createSGrEnumListType();
			case V0Package.SGR_FLEX_ASSISTANCE_TYPE: return createSGrFlexAssistanceType();
			case V0Package.SGR_FUNCTIONAL_PROFILE_BASE_TYPE: return createSGrFunctionalProfileBaseType();
			case V0Package.SGR_FUNCTIONAL_PROFILE_FRAME_TYPE: return createSGrFunctionalProfileFrameType();
			case V0Package.SGR_GEN_DEVICE_FRAME: return createSGrGenDeviceFrame();
			case V0Package.SGR_GEN_FUNCTIONAL_PROFILE_TYPE: return createSGrGenFunctionalProfileType();
			case V0Package.SGR_LEGIB_DOCUMENTATION_TYPE: return createSGrLegibDocumentationType();
			case V0Package.SGR_MODBUS_BITMAP_MAPPER_TYPE: return createSGrModbusBitmapMapperType();
			case V0Package.SGR_MODBUS_BOOLEAN_MAPPER_TYPE: return createSGrModbusBooleanMapperType();
			case V0Package.SGR_MODBUS_DATA_POINT_DESCRIPTION_TYPE: return createSGrModbusDataPointDescriptionType();
			case V0Package.SGR_MODBUS_DATA_POINT_TYPE: return createSGrModbusDataPointType();
			case V0Package.SGR_MODBUS_DEVICE_FRAME: return createSGrModbusDeviceFrame();
			case V0Package.SGR_MODBUS_ENUM_MAPPER_TYPE: return createSGrModbusEnumMapperType();
			case V0Package.SGR_MODBUS_FUNCTIONAL_PROFILE_TYPE: return createSGrModbusFunctionalProfileType();
			case V0Package.SGR_MODBUS_INTERFACE_DESCRIPTION_TYPE: return createSGrModbusInterfaceDescriptionType();
			case V0Package.SGR_NAMELIST_TYPE: return createSGrNamelistType();
			case V0Package.SGR_PROFILE_DESCRIPTION_TYPE: return createSGrProfileDescriptionType();
			case V0Package.SGR_PROFILENUMBER_TYPE: return createSGrProfilenumberType();
			case V0Package.SGR_RELEASE_NOTES: return createSGrReleaseNotes();
			case V0Package.SG_RREST_API_BEARER_TYPE: return createSGRrestAPIBearerType();
			case V0Package.SGR_REST_API_DATA_POINT_DESCRIPTION_TYPE: return createSGrRestAPIDataPointDescriptionType();
			case V0Package.SGR_REST_API_DATA_POINT_TYPE: return createSGrRestAPIDataPointType();
			case V0Package.SGR_REST_API_DEVICE_FRAME: return createSGrRestAPIDeviceFrame();
			case V0Package.SGR_REST_API_FUNCTIONAL_PROFILE_TYPE: return createSGrRestAPIFunctionalProfileType();
			case V0Package.SGR_REST_API_INTERFACE_DESCRIPTION_TYPE: return createSGrRestAPIInterfaceDescriptionType();
			case V0Package.SGR_REST_BASIC_TYPE: return createSGrRestBasicType();
			case V0Package.SGR_SCALING_TYPE: return createSGrScalingType();
			case V0Package.SGR_SERIAL_INTERFACE_CAPABILITY_TYPE: return createSGrSerialInterfaceCapabilityType();
			case V0Package.SGR_SMOOTH_TRANSITION_TYPE: return createSGrSmoothTransitionType();
			case V0Package.SGR_STABILITY_FALLBACK_TYPE: return createSGrStabilityFallbackType();
			case V0Package.SGR_TIME_SYNC_BLOCK_NOTIFICATION_TYPE: return createSGrTimeSyncBlockNotificationType();
			case V0Package.SGR_VERSION_NUMBER_TYPE: return createSGrVersionNumberType();
			case V0Package.TCP_DEV_INSTANCE_TYPE: return createTCPDevInstanceType();
			case V0Package.TC_PTRSP_SRV_INSTANCE_TYPE: return createTCPtrspSrvInstanceType();
			case V0Package.TCP_TYPE: return createTCPType();
			case V0Package.TPIP_MODBUS: return createTPIPModbus();
			case V0Package.TPIP_V4GEN_ADDR_TYPE: return createTPipV4genAddrType();
			case V0Package.TPIP_V6GEN_ADDR_TYPE: return createTPipV6genAddrType();
			case V0Package.TPRTU_MODBUS: return createTPRTUModbus();
			case V0Package.TRSP_SERVICE_MODBUS_TYPE: return createTrspServiceModbusType();
			case V0Package.TS_GR_MODBUS_REGISTER_REF: return createTSGrModbusRegisterRef();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case V0Package.CTA_DHW_OP_MODE_TYPE:
				return createCtaDHWOpModeTypeFromString(eDataType, initialValue);
			case V0Package.CTA_HC_OP_MODE_TYPE:
				return createCtaHCOpModeTypeFromString(eDataType, initialValue);
			case V0Package.CTA_HP_OP_MODE_TYPE:
				return createCtaHPOpModeTypeFromString(eDataType, initialValue);
			case V0Package.CTA_HP_OP_STATE_TYPE:
				return createCtaHPOpStateTypeFromString(eDataType, initialValue);
			case V0Package.EBAUD_RATE_TYPE:
				return createEBaudRateTypeFromString(eDataType, initialValue);
			case V0Package.EBYTE_LEN_TYPE:
				return createEByteLenTypeFromString(eDataType, initialValue);
			case V0Package.EPARITY_TYPE:
				return createEParityTypeFromString(eDataType, initialValue);
			case V0Package.ESTOP_BIT_LEN_TYPE:
				return createEStopBitLenTypeFromString(eDataType, initialValue);
			case V0Package.HOV_BUFFER_STATE_TYPE:
				return createHovBufferStateTypeFromString(eDataType, initialValue);
			case V0Package.HOV_DOM_HOT_WOP_MODE_TYPE:
				return createHovDomHotWOpModeTypeFromString(eDataType, initialValue);
			case V0Package.HOV_DOM_HOT_WSTATE_TYPE:
				return createHovDomHotWStateTypeFromString(eDataType, initialValue);
			case V0Package.HOV_HC_OP_MODE_TYPE:
				return createHovHCOpModeTypeFromString(eDataType, initialValue);
			case V0Package.HOV_HC_OP_STATE_TYPE:
				return createHovHCOpStateTypeFromString(eDataType, initialValue);
			case V0Package.HOV_HP_ACT_BUFFER_STATE_TYPE:
				return createHovHPActBufferStateTypeFromString(eDataType, initialValue);
			case V0Package.HOV_HP_OP_MODE_TYPE:
				return createHovHPOpModeTypeFromString(eDataType, initialValue);
			case V0Package.HOV_HP_OP_STATE_TYPE:
				return createHovHPOpStateTypeFromString(eDataType, initialValue);
			case V0Package.HOV_SG_READY_SRC_SEL_TYPE:
				return createHovSGReadySrcSelTypeFromString(eDataType, initialValue);
			case V0Package.HOV_TYPE:
				return createHovTypeFromString(eDataType, initialValue);
			case V0Package.HTTP_METHOD:
				return createHttpMethodFromString(eDataType, initialValue);
			case V0Package.LEVEL_OF_OPERATION_TYPE:
				return createLevelOfOperationTypeFromString(eDataType, initialValue);
			case V0Package.MASTER_FUNCTIONS_SUPPORTED_TYPE:
				return createMasterFunctionsSupportedTypeFromString(eDataType, initialValue);
			case V0Package.MODBUS_INTERFACE_SELECTION_TYPE:
				return createModbusInterfaceSelectionTypeFromString(eDataType, initialValue);
			case V0Package.PROFILE_TYPE_ENUM_TYPE:
				return createProfileTypeEnumTypeFromString(eDataType, initialValue);
			case V0Package.RESPONSE_QUERY_TYPE:
				return createResponseQueryTypeFromString(eDataType, initialValue);
			case V0Package.SGR_BOOL2_BIT_RANK_TYPE:
				return createSGrBool2BitRankTypeFromString(eDataType, initialValue);
			case V0Package.SGR_DEVICE_KIND_TYPE:
				return createSGrDeviceKindTypeFromString(eDataType, initialValue);
			case V0Package.SG_READY_STATE_LV1_TYPE:
				return createSGReadyStateLv1TypeFromString(eDataType, initialValue);
			case V0Package.SG_READY_STATE_LV2_TYPE:
				return createSGReadyStateLv2TypeFromString(eDataType, initialValue);
			case V0Package.SGR_EVSE_STATE_LV1_TYPE:
				return createSGrEVSEStateLv1TypeFromString(eDataType, initialValue);
			case V0Package.SGR_EVSE_STATE_LV2_TYPE:
				return createSGrEVSEStateLv2TypeFromString(eDataType, initialValue);
			case V0Package.SGR_EV_STATE_TYPE:
				return createSGrEVStateTypeFromString(eDataType, initialValue);
			case V0Package.SGR_HC_OP_MODE_TYPE:
				return createSGrHCOpModeTypeFromString(eDataType, initialValue);
			case V0Package.SGR_HC_OP_STATE_TYPE:
				return createSgrHCOpStateTypeFromString(eDataType, initialValue);
			case V0Package.SGR_HP_OP_MODE_TYPE:
				return createSGrHPOpModeTypeFromString(eDataType, initialValue);
			case V0Package.SGR_HP_OPSTATE_TYPE:
				return createSGrHPOpstateTypeFromString(eDataType, initialValue);
			case V0Package.SGR_LANGUAGE_TYPE:
				return createSGrLanguageTypeFromString(eDataType, initialValue);
			case V0Package.SGR_MANUFACTURER_ID_TYPE:
				return createSGrManufacturerIDTypeFromString(eDataType, initialValue);
			case V0Package.SGR_MEAS_VALUE_SOURCE_TYPE:
				return createSGrMeasValueSourceTypeFromString(eDataType, initialValue);
			case V0Package.SGR_MEAS_VALUE_TYPE:
				return createSGrMeasValueTypeFromString(eDataType, initialValue);
			case V0Package.SGR_MODBUS_LAYER6_DEVIATION_TYPE:
				return createSGrModbusLayer6DeviationTypeFromString(eDataType, initialValue);
			case V0Package.SGR_MRO_PRESENCE_LEVEL_INDICATION_TYPE:
				return createSGrMROPresenceLevelIndicationTypeFromString(eDataType, initialValue);
			case V0Package.SGR_OBLIG_LVL_TYPE:
				return createSGrObligLvlTypeFromString(eDataType, initialValue);
			case V0Package.SGR_OCPP_STATE_TYPE:
				return createSGrOCPPStateTypeFromString(eDataType, initialValue);
			case V0Package.SGR_POWER_SOURCE_TYPE:
				return createSGrPowerSourceTypeFromString(eDataType, initialValue);
			case V0Package.SGR_RELEASE_STATE:
				return createSGrReleaseStateFromString(eDataType, initialValue);
			case V0Package.SGR_REST_API_AUTHENTICATION_ENUM_METHOD_TYPE:
				return createSGrRestAPIAuthenticationEnumMethodTypeFromString(eDataType, initialValue);
			case V0Package.SGR_REST_AP_IDATA_TYPE_TYPE:
				return createSGrRestAPIdataTypeTypeFromString(eDataType, initialValue);
			case V0Package.SGR_REST_API_INTERFACE_SELECTIONTYPE:
				return createSGrRestAPIInterfaceSelectiontypeFromString(eDataType, initialValue);
			case V0Package.SGR_RWP_TYPE:
				return createSGrRWPTypeFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_FEED_IN_STATE_LV2_TYPE:
				return createSGrSGCPFeedInStateLv2TypeFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_LOAD_STATE_LV2_TYPE:
				return createSGrSGCPLoadStateLv2TypeFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_SERVICE_TYPE:
				return createSGrSGCPServiceTypeFromString(eDataType, initialValue);
			case V0Package.SGR_SUNSP_STATE_CODES_TYPE:
				return createSGrSunspStateCodesTypeFromString(eDataType, initialValue);
			case V0Package.SGR_TRANSPORT_SERVICES_USED_LIST_TYPE:
				return createSGrTransportServicesUsedListTypeFromString(eDataType, initialValue);
			case V0Package.SGR_UNITS:
				return createSGrUnitsFromString(eDataType, initialValue);
			case V0Package.STI_HP_OPSTATE_TYPE:
				return createStiHPOpstateTypeFromString(eDataType, initialValue);
			case V0Package.SUB_PROFILE_TYPE_ENUM_TYPE:
				return createSubProfileTypeEnumTypeFromString(eDataType, initialValue);
			case V0Package.TENUM_CONVERSION_FCT:
				return createTEnumConversionFctFromString(eDataType, initialValue);
			case V0Package.TENUM_EXCEPTION_CODE_TYPE:
				return createTEnumExceptionCodeTypeFromString(eDataType, initialValue);
			case V0Package.TENUM_OBJECT_TYPE:
				return createTEnumObjectTypeFromString(eDataType, initialValue);
			case V0Package.BITMASK_TYPE:
				return createBitmaskTypeFromString(eDataType, initialValue);
			case V0Package.CTA_DHW_OP_MODE_TYPE_OBJECT:
				return createCtaDHWOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.CTA_HC_OP_MODE_TYPE_OBJECT:
				return createCtaHCOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.CTA_HP_OP_MODE_TYPE_OBJECT:
				return createCtaHPOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.CTA_HP_OP_STATE_TYPE_OBJECT:
				return createCtaHPOpStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.DP_SIZE_NR_REGISTERS_TYPE:
				return createDpSizeNrRegistersTypeFromString(eDataType, initialValue);
			case V0Package.DP_SIZE_NR_REGISTERS_TYPE_OBJECT:
				return createDpSizeNrRegistersTypeObjectFromString(eDataType, initialValue);
			case V0Package.EBAUD_RATE_TYPE_OBJECT:
				return createEBaudRateTypeObjectFromString(eDataType, initialValue);
			case V0Package.EBYTE_LEN_TYPE_OBJECT:
				return createEByteLenTypeObjectFromString(eDataType, initialValue);
			case V0Package.EPARITY_TYPE_OBJECT:
				return createEParityTypeObjectFromString(eDataType, initialValue);
			case V0Package.ESTOP_BIT_LEN_TYPE_OBJECT:
				return createEStopBitLenTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_BUFFER_STATE_TYPE_OBJECT:
				return createHovBufferStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_DOM_HOT_WOP_MODE_TYPE_OBJECT:
				return createHovDomHotWOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_DOM_HOT_WSTATE_TYPE_OBJECT:
				return createHovDomHotWStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_HC_OP_MODE_TYPE_OBJECT:
				return createHovHCOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_HC_OP_STATE_TYPE_OBJECT:
				return createHovHCOpStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_HP_ACT_BUFFER_STATE_TYPE_OBJECT:
				return createHovHPActBufferStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_HP_OP_MODE_TYPE_OBJECT:
				return createHovHPOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_HP_OP_STATE_TYPE_OBJECT:
				return createHovHPOpStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_SG_READY_SRC_SEL_TYPE_OBJECT:
				return createHovSGReadySrcSelTypeObjectFromString(eDataType, initialValue);
			case V0Package.HOV_TYPE_OBJECT:
				return createHovTypeObjectFromString(eDataType, initialValue);
			case V0Package.HTTP_METHOD_OBJECT:
				return createHttpMethodObjectFromString(eDataType, initialValue);
			case V0Package.IP_V4N1_TYPE:
				return createIpV4n1TypeFromString(eDataType, initialValue);
			case V0Package.IP_V4N1_TYPE1:
				return createIpV4n1Type1FromString(eDataType, initialValue);
			case V0Package.IP_V4N2_TYPE:
				return createIpV4n2TypeFromString(eDataType, initialValue);
			case V0Package.IP_V4N2_TYPE1:
				return createIpV4n2Type1FromString(eDataType, initialValue);
			case V0Package.IP_V4N3_TYPE:
				return createIpV4n3TypeFromString(eDataType, initialValue);
			case V0Package.IP_V4N3_TYPE1:
				return createIpV4n3Type1FromString(eDataType, initialValue);
			case V0Package.IP_V4N4_TYPE:
				return createIpV4n4TypeFromString(eDataType, initialValue);
			case V0Package.IP_V4N4_TYPE1:
				return createIpV4n4Type1FromString(eDataType, initialValue);
			case V0Package.IP_V4PORT_NR_TYPE:
				return createIpV4portNrTypeFromString(eDataType, initialValue);
			case V0Package.IP_V4PORT_NR_TYPE_OBJECT:
				return createIpV4portNrTypeObjectFromString(eDataType, initialValue);
			case V0Package.LEVEL_OF_OPERATION_TYPE_OBJECT:
				return createLevelOfOperationTypeObjectFromString(eDataType, initialValue);
			case V0Package.MASTER_FUNCTIONS_SUPPORTED_TYPE_OBJECT:
				return createMasterFunctionsSupportedTypeObjectFromString(eDataType, initialValue);
			case V0Package.MODBUS_INTERFACE_SELECTION_TYPE_OBJECT:
				return createModbusInterfaceSelectionTypeObjectFromString(eDataType, initialValue);
			case V0Package.PLACE_HOLDER4FUTURE_EXTENSIONS_TYPE:
				return createPlaceHolder4futureExtensionsTypeFromString(eDataType, initialValue);
			case V0Package.PRELIM_STRING_DEF_TYPE:
				return createPrelimStringDefTypeFromString(eDataType, initialValue);
			case V0Package.PROFILE_TYPE_ENUM_TYPE_OBJECT:
				return createProfileTypeEnumTypeObjectFromString(eDataType, initialValue);
			case V0Package.RESPONSE_QUERY_TYPE_OBJECT:
				return createResponseQueryTypeObjectFromString(eDataType, initialValue);
			case V0Package.REST_API_END_POINT_TYPE:
				return createRestAPIEndPointTypeFromString(eDataType, initialValue);
			case V0Package.REST_APIJMES_PATH_TYPE:
				return createRestAPIJMESPathTypeFromString(eDataType, initialValue);
			case V0Package.REST_BASIC_PASSWORD_TYPE:
				return createRestBasicPasswordTypeFromString(eDataType, initialValue);
			case V0Package.REST_BASIC_USERNAME_TYPE:
				return createRestBasicUsernameTypeFromString(eDataType, initialValue);
			case V0Package.SGR_BOOL2_BIT_RANK_TYPE_OBJECT:
				return createSGrBool2BitRankTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_DEVICE_KIND_TYPE_OBJECT:
				return createSGrDeviceKindTypeObjectFromString(eDataType, initialValue);
			case V0Package.SG_READY_STATE_LV1_TYPE_OBJECT:
				return createSGReadyStateLv1TypeObjectFromString(eDataType, initialValue);
			case V0Package.SG_READY_STATE_LV2_TYPE_OBJECT:
				return createSGReadyStateLv2TypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_EVSE_STATE_LV1_TYPE_OBJECT:
				return createSGrEVSEStateLv1TypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_EVSE_STATE_LV2_TYPE_OBJECT:
				return createSGrEVSEStateLv2TypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_EV_STATE_TYPE_OBJECT:
				return createSGrEVStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_HC_OP_MODE_TYPE_OBJECT:
				return createSGrHCOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_HC_OP_STATE_TYPE_OBJECT:
				return createSgrHCOpStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_HP_OP_MODE_TYPE_OBJECT:
				return createSGrHPOpModeTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_HP_OPSTATE_TYPE_OBJECT:
				return createSGrHPOpstateTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_LANGUAGE_TYPE_OBJECT:
				return createSGrLanguageTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_MANUFACTURER_ID_TYPE_OBJECT:
				return createSGrManufacturerIDTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_MEAS_VALUE_SOURCE_TYPE_OBJECT:
				return createSGrMeasValueSourceTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_MEAS_VALUE_TYPE_OBJECT:
				return createSGrMeasValueTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_MODBUS_LAYER6_DEVIATION_TYPE_OBJECT:
				return createSGrModbusLayer6DeviationTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_MRO_PRESENCE_LEVEL_INDICATION_TYPE_OBJECT:
				return createSGrMROPresenceLevelIndicationTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_OBLIG_LVL_TYPE_OBJECT:
				return createSGrObligLvlTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_OCPP_STATE_TYPE_OBJECT:
				return createSGrOCPPStateTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_POWER_SOURCE_TYPE_OBJECT:
				return createSGrPowerSourceTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_PRECISION_TYPE:
				return createSGrPrecisionTypeFromString(eDataType, initialValue);
			case V0Package.SGR_PRECISION_TYPE_OBJECT:
				return createSGrPrecisionTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_RELEASE_STATE_OBJECT:
				return createSGrReleaseStateObjectFromString(eDataType, initialValue);
			case V0Package.SGR_REST_API_AUTHENTICATION_ENUM_METHOD_TYPE_OBJECT:
				return createSGrRestAPIAuthenticationEnumMethodTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_REST_AP_IDATA_TYPE_TYPE_OBJECT:
				return createSGrRestAPIdataTypeTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_REST_API_INTERFACE_SELECTIONTYPE_OBJECT:
				return createSGrRestAPIInterfaceSelectiontypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_RWP_TYPE_OBJECT:
				return createSGrRWPTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_FEED_IN_STATE_LV2_TYPE_OBJECT:
				return createSGrSGCPFeedInStateLv2TypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_LOAD_STATE_LV2_TYPE_OBJECT:
				return createSGrSGCPLoadStateLv2TypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_SGCP_SERVICE_TYPE_OBJECT:
				return createSGrSGCPServiceTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_SPECIAL_QUALITY_REQ_TYPE:
				return createSGrSpecialQualityReqTypeFromString(eDataType, initialValue);
			case V0Package.SGR_SUNSP_STATE_CODES_TYPE_OBJECT:
				return createSGrSunspStateCodesTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_TRANSPORT_SERVICES_USED_LIST_TYPE_OBJECT:
				return createSGrTransportServicesUsedListTypeObjectFromString(eDataType, initialValue);
			case V0Package.SGR_UNITS_OBJECT:
				return createSGrUnitsObjectFromString(eDataType, initialValue);
			case V0Package.STI_HP_OPSTATE_TYPE_OBJECT:
				return createStiHPOpstateTypeObjectFromString(eDataType, initialValue);
			case V0Package.SUB_PROFILE_TYPE_ENUM_TYPE_OBJECT:
				return createSubProfileTypeEnumTypeObjectFromString(eDataType, initialValue);
			case V0Package.TBIT_RANK:
				return createTBitRankFromString(eDataType, initialValue);
			case V0Package.TBIT_RANK_OBJECT:
				return createTBitRankObjectFromString(eDataType, initialValue);
			case V0Package.TENUM_CONVERSION_FCT_OBJECT:
				return createTEnumConversionFctObjectFromString(eDataType, initialValue);
			case V0Package.TENUM_EXCEPTION_CODE_TYPE_OBJECT:
				return createTEnumExceptionCodeTypeObjectFromString(eDataType, initialValue);
			case V0Package.TENUM_OBJECT_TYPE_OBJECT:
				return createTEnumObjectTypeObjectFromString(eDataType, initialValue);
			case V0Package.TEXT_ELEMENT_TYPE:
				return createTextElementTypeFromString(eDataType, initialValue);
			case V0Package.TI_PPORT_NR_TYPE:
				return createTIPportNrTypeFromString(eDataType, initialValue);
			case V0Package.TI_PPORT_NR_TYPE_OBJECT:
				return createTIPportNrTypeObjectFromString(eDataType, initialValue);
			case V0Package.TSLAVE_ID_TYPE:
				return createTSlaveIDTypeFromString(eDataType, initialValue);
			case V0Package.TSLAVE_ID_TYPE_OBJECT:
				return createTSlaveIDTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case V0Package.CTA_DHW_OP_MODE_TYPE:
				return convertCtaDHWOpModeTypeToString(eDataType, instanceValue);
			case V0Package.CTA_HC_OP_MODE_TYPE:
				return convertCtaHCOpModeTypeToString(eDataType, instanceValue);
			case V0Package.CTA_HP_OP_MODE_TYPE:
				return convertCtaHPOpModeTypeToString(eDataType, instanceValue);
			case V0Package.CTA_HP_OP_STATE_TYPE:
				return convertCtaHPOpStateTypeToString(eDataType, instanceValue);
			case V0Package.EBAUD_RATE_TYPE:
				return convertEBaudRateTypeToString(eDataType, instanceValue);
			case V0Package.EBYTE_LEN_TYPE:
				return convertEByteLenTypeToString(eDataType, instanceValue);
			case V0Package.EPARITY_TYPE:
				return convertEParityTypeToString(eDataType, instanceValue);
			case V0Package.ESTOP_BIT_LEN_TYPE:
				return convertEStopBitLenTypeToString(eDataType, instanceValue);
			case V0Package.HOV_BUFFER_STATE_TYPE:
				return convertHovBufferStateTypeToString(eDataType, instanceValue);
			case V0Package.HOV_DOM_HOT_WOP_MODE_TYPE:
				return convertHovDomHotWOpModeTypeToString(eDataType, instanceValue);
			case V0Package.HOV_DOM_HOT_WSTATE_TYPE:
				return convertHovDomHotWStateTypeToString(eDataType, instanceValue);
			case V0Package.HOV_HC_OP_MODE_TYPE:
				return convertHovHCOpModeTypeToString(eDataType, instanceValue);
			case V0Package.HOV_HC_OP_STATE_TYPE:
				return convertHovHCOpStateTypeToString(eDataType, instanceValue);
			case V0Package.HOV_HP_ACT_BUFFER_STATE_TYPE:
				return convertHovHPActBufferStateTypeToString(eDataType, instanceValue);
			case V0Package.HOV_HP_OP_MODE_TYPE:
				return convertHovHPOpModeTypeToString(eDataType, instanceValue);
			case V0Package.HOV_HP_OP_STATE_TYPE:
				return convertHovHPOpStateTypeToString(eDataType, instanceValue);
			case V0Package.HOV_SG_READY_SRC_SEL_TYPE:
				return convertHovSGReadySrcSelTypeToString(eDataType, instanceValue);
			case V0Package.HOV_TYPE:
				return convertHovTypeToString(eDataType, instanceValue);
			case V0Package.HTTP_METHOD:
				return convertHttpMethodToString(eDataType, instanceValue);
			case V0Package.LEVEL_OF_OPERATION_TYPE:
				return convertLevelOfOperationTypeToString(eDataType, instanceValue);
			case V0Package.MASTER_FUNCTIONS_SUPPORTED_TYPE:
				return convertMasterFunctionsSupportedTypeToString(eDataType, instanceValue);
			case V0Package.MODBUS_INTERFACE_SELECTION_TYPE:
				return convertModbusInterfaceSelectionTypeToString(eDataType, instanceValue);
			case V0Package.PROFILE_TYPE_ENUM_TYPE:
				return convertProfileTypeEnumTypeToString(eDataType, instanceValue);
			case V0Package.RESPONSE_QUERY_TYPE:
				return convertResponseQueryTypeToString(eDataType, instanceValue);
			case V0Package.SGR_BOOL2_BIT_RANK_TYPE:
				return convertSGrBool2BitRankTypeToString(eDataType, instanceValue);
			case V0Package.SGR_DEVICE_KIND_TYPE:
				return convertSGrDeviceKindTypeToString(eDataType, instanceValue);
			case V0Package.SG_READY_STATE_LV1_TYPE:
				return convertSGReadyStateLv1TypeToString(eDataType, instanceValue);
			case V0Package.SG_READY_STATE_LV2_TYPE:
				return convertSGReadyStateLv2TypeToString(eDataType, instanceValue);
			case V0Package.SGR_EVSE_STATE_LV1_TYPE:
				return convertSGrEVSEStateLv1TypeToString(eDataType, instanceValue);
			case V0Package.SGR_EVSE_STATE_LV2_TYPE:
				return convertSGrEVSEStateLv2TypeToString(eDataType, instanceValue);
			case V0Package.SGR_EV_STATE_TYPE:
				return convertSGrEVStateTypeToString(eDataType, instanceValue);
			case V0Package.SGR_HC_OP_MODE_TYPE:
				return convertSGrHCOpModeTypeToString(eDataType, instanceValue);
			case V0Package.SGR_HC_OP_STATE_TYPE:
				return convertSgrHCOpStateTypeToString(eDataType, instanceValue);
			case V0Package.SGR_HP_OP_MODE_TYPE:
				return convertSGrHPOpModeTypeToString(eDataType, instanceValue);
			case V0Package.SGR_HP_OPSTATE_TYPE:
				return convertSGrHPOpstateTypeToString(eDataType, instanceValue);
			case V0Package.SGR_LANGUAGE_TYPE:
				return convertSGrLanguageTypeToString(eDataType, instanceValue);
			case V0Package.SGR_MANUFACTURER_ID_TYPE:
				return convertSGrManufacturerIDTypeToString(eDataType, instanceValue);
			case V0Package.SGR_MEAS_VALUE_SOURCE_TYPE:
				return convertSGrMeasValueSourceTypeToString(eDataType, instanceValue);
			case V0Package.SGR_MEAS_VALUE_TYPE:
				return convertSGrMeasValueTypeToString(eDataType, instanceValue);
			case V0Package.SGR_MODBUS_LAYER6_DEVIATION_TYPE:
				return convertSGrModbusLayer6DeviationTypeToString(eDataType, instanceValue);
			case V0Package.SGR_MRO_PRESENCE_LEVEL_INDICATION_TYPE:
				return convertSGrMROPresenceLevelIndicationTypeToString(eDataType, instanceValue);
			case V0Package.SGR_OBLIG_LVL_TYPE:
				return convertSGrObligLvlTypeToString(eDataType, instanceValue);
			case V0Package.SGR_OCPP_STATE_TYPE:
				return convertSGrOCPPStateTypeToString(eDataType, instanceValue);
			case V0Package.SGR_POWER_SOURCE_TYPE:
				return convertSGrPowerSourceTypeToString(eDataType, instanceValue);
			case V0Package.SGR_RELEASE_STATE:
				return convertSGrReleaseStateToString(eDataType, instanceValue);
			case V0Package.SGR_REST_API_AUTHENTICATION_ENUM_METHOD_TYPE:
				return convertSGrRestAPIAuthenticationEnumMethodTypeToString(eDataType, instanceValue);
			case V0Package.SGR_REST_AP_IDATA_TYPE_TYPE:
				return convertSGrRestAPIdataTypeTypeToString(eDataType, instanceValue);
			case V0Package.SGR_REST_API_INTERFACE_SELECTIONTYPE:
				return convertSGrRestAPIInterfaceSelectiontypeToString(eDataType, instanceValue);
			case V0Package.SGR_RWP_TYPE:
				return convertSGrRWPTypeToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_FEED_IN_STATE_LV2_TYPE:
				return convertSGrSGCPFeedInStateLv2TypeToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_LOAD_STATE_LV2_TYPE:
				return convertSGrSGCPLoadStateLv2TypeToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_SERVICE_TYPE:
				return convertSGrSGCPServiceTypeToString(eDataType, instanceValue);
			case V0Package.SGR_SUNSP_STATE_CODES_TYPE:
				return convertSGrSunspStateCodesTypeToString(eDataType, instanceValue);
			case V0Package.SGR_TRANSPORT_SERVICES_USED_LIST_TYPE:
				return convertSGrTransportServicesUsedListTypeToString(eDataType, instanceValue);
			case V0Package.SGR_UNITS:
				return convertSGrUnitsToString(eDataType, instanceValue);
			case V0Package.STI_HP_OPSTATE_TYPE:
				return convertStiHPOpstateTypeToString(eDataType, instanceValue);
			case V0Package.SUB_PROFILE_TYPE_ENUM_TYPE:
				return convertSubProfileTypeEnumTypeToString(eDataType, instanceValue);
			case V0Package.TENUM_CONVERSION_FCT:
				return convertTEnumConversionFctToString(eDataType, instanceValue);
			case V0Package.TENUM_EXCEPTION_CODE_TYPE:
				return convertTEnumExceptionCodeTypeToString(eDataType, instanceValue);
			case V0Package.TENUM_OBJECT_TYPE:
				return convertTEnumObjectTypeToString(eDataType, instanceValue);
			case V0Package.BITMASK_TYPE:
				return convertBitmaskTypeToString(eDataType, instanceValue);
			case V0Package.CTA_DHW_OP_MODE_TYPE_OBJECT:
				return convertCtaDHWOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.CTA_HC_OP_MODE_TYPE_OBJECT:
				return convertCtaHCOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.CTA_HP_OP_MODE_TYPE_OBJECT:
				return convertCtaHPOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.CTA_HP_OP_STATE_TYPE_OBJECT:
				return convertCtaHPOpStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.DP_SIZE_NR_REGISTERS_TYPE:
				return convertDpSizeNrRegistersTypeToString(eDataType, instanceValue);
			case V0Package.DP_SIZE_NR_REGISTERS_TYPE_OBJECT:
				return convertDpSizeNrRegistersTypeObjectToString(eDataType, instanceValue);
			case V0Package.EBAUD_RATE_TYPE_OBJECT:
				return convertEBaudRateTypeObjectToString(eDataType, instanceValue);
			case V0Package.EBYTE_LEN_TYPE_OBJECT:
				return convertEByteLenTypeObjectToString(eDataType, instanceValue);
			case V0Package.EPARITY_TYPE_OBJECT:
				return convertEParityTypeObjectToString(eDataType, instanceValue);
			case V0Package.ESTOP_BIT_LEN_TYPE_OBJECT:
				return convertEStopBitLenTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_BUFFER_STATE_TYPE_OBJECT:
				return convertHovBufferStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_DOM_HOT_WOP_MODE_TYPE_OBJECT:
				return convertHovDomHotWOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_DOM_HOT_WSTATE_TYPE_OBJECT:
				return convertHovDomHotWStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_HC_OP_MODE_TYPE_OBJECT:
				return convertHovHCOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_HC_OP_STATE_TYPE_OBJECT:
				return convertHovHCOpStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_HP_ACT_BUFFER_STATE_TYPE_OBJECT:
				return convertHovHPActBufferStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_HP_OP_MODE_TYPE_OBJECT:
				return convertHovHPOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_HP_OP_STATE_TYPE_OBJECT:
				return convertHovHPOpStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_SG_READY_SRC_SEL_TYPE_OBJECT:
				return convertHovSGReadySrcSelTypeObjectToString(eDataType, instanceValue);
			case V0Package.HOV_TYPE_OBJECT:
				return convertHovTypeObjectToString(eDataType, instanceValue);
			case V0Package.HTTP_METHOD_OBJECT:
				return convertHttpMethodObjectToString(eDataType, instanceValue);
			case V0Package.IP_V4N1_TYPE:
				return convertIpV4n1TypeToString(eDataType, instanceValue);
			case V0Package.IP_V4N1_TYPE1:
				return convertIpV4n1Type1ToString(eDataType, instanceValue);
			case V0Package.IP_V4N2_TYPE:
				return convertIpV4n2TypeToString(eDataType, instanceValue);
			case V0Package.IP_V4N2_TYPE1:
				return convertIpV4n2Type1ToString(eDataType, instanceValue);
			case V0Package.IP_V4N3_TYPE:
				return convertIpV4n3TypeToString(eDataType, instanceValue);
			case V0Package.IP_V4N3_TYPE1:
				return convertIpV4n3Type1ToString(eDataType, instanceValue);
			case V0Package.IP_V4N4_TYPE:
				return convertIpV4n4TypeToString(eDataType, instanceValue);
			case V0Package.IP_V4N4_TYPE1:
				return convertIpV4n4Type1ToString(eDataType, instanceValue);
			case V0Package.IP_V4PORT_NR_TYPE:
				return convertIpV4portNrTypeToString(eDataType, instanceValue);
			case V0Package.IP_V4PORT_NR_TYPE_OBJECT:
				return convertIpV4portNrTypeObjectToString(eDataType, instanceValue);
			case V0Package.LEVEL_OF_OPERATION_TYPE_OBJECT:
				return convertLevelOfOperationTypeObjectToString(eDataType, instanceValue);
			case V0Package.MASTER_FUNCTIONS_SUPPORTED_TYPE_OBJECT:
				return convertMasterFunctionsSupportedTypeObjectToString(eDataType, instanceValue);
			case V0Package.MODBUS_INTERFACE_SELECTION_TYPE_OBJECT:
				return convertModbusInterfaceSelectionTypeObjectToString(eDataType, instanceValue);
			case V0Package.PLACE_HOLDER4FUTURE_EXTENSIONS_TYPE:
				return convertPlaceHolder4futureExtensionsTypeToString(eDataType, instanceValue);
			case V0Package.PRELIM_STRING_DEF_TYPE:
				return convertPrelimStringDefTypeToString(eDataType, instanceValue);
			case V0Package.PROFILE_TYPE_ENUM_TYPE_OBJECT:
				return convertProfileTypeEnumTypeObjectToString(eDataType, instanceValue);
			case V0Package.RESPONSE_QUERY_TYPE_OBJECT:
				return convertResponseQueryTypeObjectToString(eDataType, instanceValue);
			case V0Package.REST_API_END_POINT_TYPE:
				return convertRestAPIEndPointTypeToString(eDataType, instanceValue);
			case V0Package.REST_APIJMES_PATH_TYPE:
				return convertRestAPIJMESPathTypeToString(eDataType, instanceValue);
			case V0Package.REST_BASIC_PASSWORD_TYPE:
				return convertRestBasicPasswordTypeToString(eDataType, instanceValue);
			case V0Package.REST_BASIC_USERNAME_TYPE:
				return convertRestBasicUsernameTypeToString(eDataType, instanceValue);
			case V0Package.SGR_BOOL2_BIT_RANK_TYPE_OBJECT:
				return convertSGrBool2BitRankTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_DEVICE_KIND_TYPE_OBJECT:
				return convertSGrDeviceKindTypeObjectToString(eDataType, instanceValue);
			case V0Package.SG_READY_STATE_LV1_TYPE_OBJECT:
				return convertSGReadyStateLv1TypeObjectToString(eDataType, instanceValue);
			case V0Package.SG_READY_STATE_LV2_TYPE_OBJECT:
				return convertSGReadyStateLv2TypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_EVSE_STATE_LV1_TYPE_OBJECT:
				return convertSGrEVSEStateLv1TypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_EVSE_STATE_LV2_TYPE_OBJECT:
				return convertSGrEVSEStateLv2TypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_EV_STATE_TYPE_OBJECT:
				return convertSGrEVStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_HC_OP_MODE_TYPE_OBJECT:
				return convertSGrHCOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_HC_OP_STATE_TYPE_OBJECT:
				return convertSgrHCOpStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_HP_OP_MODE_TYPE_OBJECT:
				return convertSGrHPOpModeTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_HP_OPSTATE_TYPE_OBJECT:
				return convertSGrHPOpstateTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_LANGUAGE_TYPE_OBJECT:
				return convertSGrLanguageTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_MANUFACTURER_ID_TYPE_OBJECT:
				return convertSGrManufacturerIDTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_MEAS_VALUE_SOURCE_TYPE_OBJECT:
				return convertSGrMeasValueSourceTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_MEAS_VALUE_TYPE_OBJECT:
				return convertSGrMeasValueTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_MODBUS_LAYER6_DEVIATION_TYPE_OBJECT:
				return convertSGrModbusLayer6DeviationTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_MRO_PRESENCE_LEVEL_INDICATION_TYPE_OBJECT:
				return convertSGrMROPresenceLevelIndicationTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_OBLIG_LVL_TYPE_OBJECT:
				return convertSGrObligLvlTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_OCPP_STATE_TYPE_OBJECT:
				return convertSGrOCPPStateTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_POWER_SOURCE_TYPE_OBJECT:
				return convertSGrPowerSourceTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_PRECISION_TYPE:
				return convertSGrPrecisionTypeToString(eDataType, instanceValue);
			case V0Package.SGR_PRECISION_TYPE_OBJECT:
				return convertSGrPrecisionTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_RELEASE_STATE_OBJECT:
				return convertSGrReleaseStateObjectToString(eDataType, instanceValue);
			case V0Package.SGR_REST_API_AUTHENTICATION_ENUM_METHOD_TYPE_OBJECT:
				return convertSGrRestAPIAuthenticationEnumMethodTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_REST_AP_IDATA_TYPE_TYPE_OBJECT:
				return convertSGrRestAPIdataTypeTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_REST_API_INTERFACE_SELECTIONTYPE_OBJECT:
				return convertSGrRestAPIInterfaceSelectiontypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_RWP_TYPE_OBJECT:
				return convertSGrRWPTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_FEED_IN_STATE_LV2_TYPE_OBJECT:
				return convertSGrSGCPFeedInStateLv2TypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_LOAD_STATE_LV2_TYPE_OBJECT:
				return convertSGrSGCPLoadStateLv2TypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_SGCP_SERVICE_TYPE_OBJECT:
				return convertSGrSGCPServiceTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_SPECIAL_QUALITY_REQ_TYPE:
				return convertSGrSpecialQualityReqTypeToString(eDataType, instanceValue);
			case V0Package.SGR_SUNSP_STATE_CODES_TYPE_OBJECT:
				return convertSGrSunspStateCodesTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_TRANSPORT_SERVICES_USED_LIST_TYPE_OBJECT:
				return convertSGrTransportServicesUsedListTypeObjectToString(eDataType, instanceValue);
			case V0Package.SGR_UNITS_OBJECT:
				return convertSGrUnitsObjectToString(eDataType, instanceValue);
			case V0Package.STI_HP_OPSTATE_TYPE_OBJECT:
				return convertStiHPOpstateTypeObjectToString(eDataType, instanceValue);
			case V0Package.SUB_PROFILE_TYPE_ENUM_TYPE_OBJECT:
				return convertSubProfileTypeEnumTypeObjectToString(eDataType, instanceValue);
			case V0Package.TBIT_RANK:
				return convertTBitRankToString(eDataType, instanceValue);
			case V0Package.TBIT_RANK_OBJECT:
				return convertTBitRankObjectToString(eDataType, instanceValue);
			case V0Package.TENUM_CONVERSION_FCT_OBJECT:
				return convertTEnumConversionFctObjectToString(eDataType, instanceValue);
			case V0Package.TENUM_EXCEPTION_CODE_TYPE_OBJECT:
				return convertTEnumExceptionCodeTypeObjectToString(eDataType, instanceValue);
			case V0Package.TENUM_OBJECT_TYPE_OBJECT:
				return convertTEnumObjectTypeObjectToString(eDataType, instanceValue);
			case V0Package.TEXT_ELEMENT_TYPE:
				return convertTextElementTypeToString(eDataType, instanceValue);
			case V0Package.TI_PPORT_NR_TYPE:
				return convertTIPportNrTypeToString(eDataType, instanceValue);
			case V0Package.TI_PPORT_NR_TYPE_OBJECT:
				return convertTIPportNrTypeObjectToString(eDataType, instanceValue);
			case V0Package.TSLAVE_ID_TYPE:
				return convertTSlaveIDTypeToString(eDataType, instanceValue);
			case V0Package.TSLAVE_ID_TYPE_OBJECT:
				return convertTSlaveIDTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ContactAPIInterfaceDescType createContactAPIInterfaceDescType() {
		ContactAPIInterfaceDescTypeImpl contactAPIInterfaceDescType = new ContactAPIInterfaceDescTypeImpl();
		return contactAPIInterfaceDescType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionalProfileType createFunctionalProfileType() {
		FunctionalProfileTypeImpl functionalProfileType = new FunctionalProfileTypeImpl();
		return functionalProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HeaderEntry createHeaderEntry() {
		HeaderEntryImpl headerEntry = new HeaderEntryImpl();
		return headerEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HeaderList createHeaderList() {
		HeaderListImpl headerList = new HeaderListImpl();
		return headerList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IpADDRType createIpADDRType() {
		IpADDRTypeImpl ipADDRType = new IpADDRTypeImpl();
		return ipADDRType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModbusJMESPathType createModbusJMESPathType() {
		ModbusJMESPathTypeImpl modbusJMESPathType = new ModbusJMESPathTypeImpl();
		return modbusJMESPathType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NetworkConnectionStateType createNetworkConnectionStateType() {
		NetworkConnectionStateTypeImpl networkConnectionStateType = new NetworkConnectionStateTypeImpl();
		return networkConnectionStateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResponseQuery createResponseQuery() {
		ResponseQueryImpl responseQuery = new ResponseQueryImpl();
		return responseQuery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RestServiceCall createRestServiceCall() {
		RestServiceCallImpl restServiceCall = new RestServiceCallImpl();
		return restServiceCall;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RTUDevInstanceType createRTUDevInstanceType() {
		RTUDevInstanceTypeImpl rtuDevInstanceType = new RTUDevInstanceTypeImpl();
		return rtuDevInstanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RTUtrspSrvInstanceType createRTUtrspSrvInstanceType() {
		RTUtrspSrvInstanceTypeImpl rtUtrspSrvInstanceType = new RTUtrspSrvInstanceTypeImpl();
		return rtUtrspSrvInstanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RTUType createRTUType() {
		RTUTypeImpl rtuType = new RTUTypeImpl();
		return rtuType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrAccessProtectionEnabledType createSGrAccessProtectionEnabledType() {
		SGrAccessProtectionEnabledTypeImpl sGrAccessProtectionEnabledType = new SGrAccessProtectionEnabledTypeImpl();
		return sGrAccessProtectionEnabledType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrAttr4GenericType createSGrAttr4GenericType() {
		SGrAttr4GenericTypeImpl sGrAttr4GenericType = new SGrAttr4GenericTypeImpl();
		return sGrAttr4GenericType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrAttr4ModbusType createSGrAttr4ModbusType() {
		SGrAttr4ModbusTypeImpl sGrAttr4ModbusType = new SGrAttr4ModbusTypeImpl();
		return sGrAttr4ModbusType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrAttr4RestAPIType createSGrAttr4RestAPIType() {
		SGrAttr4RestAPITypeImpl sGrAttr4RestAPIType = new SGrAttr4RestAPITypeImpl();
		return sGrAttr4RestAPIType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrBasicGenDataPointTypeType createSGrBasicGenDataPointTypeType() {
		SGrBasicGenDataPointTypeTypeImpl sGrBasicGenDataPointTypeType = new SGrBasicGenDataPointTypeTypeImpl();
		return sGrBasicGenDataPointTypeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrBitmapperListType createSGrBitmapperListType() {
		SGrBitmapperListTypeImpl sGrBitmapperListType = new SGrBitmapperListTypeImpl();
		return sGrBitmapperListType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrChangeLog createSGrChangeLog() {
		SGrChangeLogImpl sGrChangeLog = new SGrChangeLogImpl();
		return sGrChangeLog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrContactAPIDeviceFrame createSGrContactAPIDeviceFrame() {
		SGrContactAPIDeviceFrameImpl sGrContactAPIDeviceFrame = new SGrContactAPIDeviceFrameImpl();
		return sGrContactAPIDeviceFrame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrContactAPIFunctionalProfileType createSGrContactAPIFunctionalProfileType() {
		SGrContactAPIFunctionalProfileTypeImpl sGrContactAPIFunctionalProfileType = new SGrContactAPIFunctionalProfileTypeImpl();
		return sGrContactAPIFunctionalProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrDataPointBaseType createSGrDataPointBaseType() {
		SGrDataPointBaseTypeImpl sGrDataPointBaseType = new SGrDataPointBaseTypeImpl();
		return sGrDataPointBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrDataPointDescriptionType createSGrDataPointDescriptionType() {
		SGrDataPointDescriptionTypeImpl sGrDataPointDescriptionType = new SGrDataPointDescriptionTypeImpl();
		return sGrDataPointDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrDeviceBaseType createSGrDeviceBaseType() {
		SGrDeviceBaseTypeImpl sGrDeviceBaseType = new SGrDeviceBaseTypeImpl();
		return sGrDeviceBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrDeviceProfileType createSGrDeviceProfileType() {
		SGrDeviceProfileTypeImpl sGrDeviceProfileType = new SGrDeviceProfileTypeImpl();
		return sGrDeviceProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrEnumListType createSGrEnumListType() {
		SGrEnumListTypeImpl sGrEnumListType = new SGrEnumListTypeImpl();
		return sGrEnumListType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrFlexAssistanceType createSGrFlexAssistanceType() {
		SGrFlexAssistanceTypeImpl sGrFlexAssistanceType = new SGrFlexAssistanceTypeImpl();
		return sGrFlexAssistanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrFunctionalProfileBaseType createSGrFunctionalProfileBaseType() {
		SGrFunctionalProfileBaseTypeImpl sGrFunctionalProfileBaseType = new SGrFunctionalProfileBaseTypeImpl();
		return sGrFunctionalProfileBaseType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrFunctionalProfileFrameType createSGrFunctionalProfileFrameType() {
		SGrFunctionalProfileFrameTypeImpl sGrFunctionalProfileFrameType = new SGrFunctionalProfileFrameTypeImpl();
		return sGrFunctionalProfileFrameType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrGenDeviceFrame createSGrGenDeviceFrame() {
		SGrGenDeviceFrameImpl sGrGenDeviceFrame = new SGrGenDeviceFrameImpl();
		return sGrGenDeviceFrame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrGenFunctionalProfileType createSGrGenFunctionalProfileType() {
		SGrGenFunctionalProfileTypeImpl sGrGenFunctionalProfileType = new SGrGenFunctionalProfileTypeImpl();
		return sGrGenFunctionalProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrLegibDocumentationType createSGrLegibDocumentationType() {
		SGrLegibDocumentationTypeImpl sGrLegibDocumentationType = new SGrLegibDocumentationTypeImpl();
		return sGrLegibDocumentationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusBitmapMapperType createSGrModbusBitmapMapperType() {
		SGrModbusBitmapMapperTypeImpl sGrModbusBitmapMapperType = new SGrModbusBitmapMapperTypeImpl();
		return sGrModbusBitmapMapperType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusBooleanMapperType createSGrModbusBooleanMapperType() {
		SGrModbusBooleanMapperTypeImpl sGrModbusBooleanMapperType = new SGrModbusBooleanMapperTypeImpl();
		return sGrModbusBooleanMapperType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusDataPointDescriptionType createSGrModbusDataPointDescriptionType() {
		SGrModbusDataPointDescriptionTypeImpl sGrModbusDataPointDescriptionType = new SGrModbusDataPointDescriptionTypeImpl();
		return sGrModbusDataPointDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusDataPointType createSGrModbusDataPointType() {
		SGrModbusDataPointTypeImpl sGrModbusDataPointType = new SGrModbusDataPointTypeImpl();
		return sGrModbusDataPointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusDeviceFrame createSGrModbusDeviceFrame() {
		SGrModbusDeviceFrameImpl sGrModbusDeviceFrame = new SGrModbusDeviceFrameImpl();
		return sGrModbusDeviceFrame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusEnumMapperType createSGrModbusEnumMapperType() {
		SGrModbusEnumMapperTypeImpl sGrModbusEnumMapperType = new SGrModbusEnumMapperTypeImpl();
		return sGrModbusEnumMapperType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusFunctionalProfileType createSGrModbusFunctionalProfileType() {
		SGrModbusFunctionalProfileTypeImpl sGrModbusFunctionalProfileType = new SGrModbusFunctionalProfileTypeImpl();
		return sGrModbusFunctionalProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrModbusInterfaceDescriptionType createSGrModbusInterfaceDescriptionType() {
		SGrModbusInterfaceDescriptionTypeImpl sGrModbusInterfaceDescriptionType = new SGrModbusInterfaceDescriptionTypeImpl();
		return sGrModbusInterfaceDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrNamelistType createSGrNamelistType() {
		SGrNamelistTypeImpl sGrNamelistType = new SGrNamelistTypeImpl();
		return sGrNamelistType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrProfileDescriptionType createSGrProfileDescriptionType() {
		SGrProfileDescriptionTypeImpl sGrProfileDescriptionType = new SGrProfileDescriptionTypeImpl();
		return sGrProfileDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrProfilenumberType createSGrProfilenumberType() {
		SGrProfilenumberTypeImpl sGrProfilenumberType = new SGrProfilenumberTypeImpl();
		return sGrProfilenumberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrReleaseNotes createSGrReleaseNotes() {
		SGrReleaseNotesImpl sGrReleaseNotes = new SGrReleaseNotesImpl();
		return sGrReleaseNotes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGRrestAPIBearerType createSGRrestAPIBearerType() {
		SGRrestAPIBearerTypeImpl sgRrestAPIBearerType = new SGRrestAPIBearerTypeImpl();
		return sgRrestAPIBearerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestAPIDataPointDescriptionType createSGrRestAPIDataPointDescriptionType() {
		SGrRestAPIDataPointDescriptionTypeImpl sGrRestAPIDataPointDescriptionType = new SGrRestAPIDataPointDescriptionTypeImpl();
		return sGrRestAPIDataPointDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestAPIDataPointType createSGrRestAPIDataPointType() {
		SGrRestAPIDataPointTypeImpl sGrRestAPIDataPointType = new SGrRestAPIDataPointTypeImpl();
		return sGrRestAPIDataPointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestAPIDeviceFrame createSGrRestAPIDeviceFrame() {
		SGrRestAPIDeviceFrameImpl sGrRestAPIDeviceFrame = new SGrRestAPIDeviceFrameImpl();
		return sGrRestAPIDeviceFrame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestAPIFunctionalProfileType createSGrRestAPIFunctionalProfileType() {
		SGrRestAPIFunctionalProfileTypeImpl sGrRestAPIFunctionalProfileType = new SGrRestAPIFunctionalProfileTypeImpl();
		return sGrRestAPIFunctionalProfileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestAPIInterfaceDescriptionType createSGrRestAPIInterfaceDescriptionType() {
		SGrRestAPIInterfaceDescriptionTypeImpl sGrRestAPIInterfaceDescriptionType = new SGrRestAPIInterfaceDescriptionTypeImpl();
		return sGrRestAPIInterfaceDescriptionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRestBasicType createSGrRestBasicType() {
		SGrRestBasicTypeImpl sGrRestBasicType = new SGrRestBasicTypeImpl();
		return sGrRestBasicType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrScalingType createSGrScalingType() {
		SGrScalingTypeImpl sGrScalingType = new SGrScalingTypeImpl();
		return sGrScalingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrSerialInterfaceCapabilityType createSGrSerialInterfaceCapabilityType() {
		SGrSerialInterfaceCapabilityTypeImpl sGrSerialInterfaceCapabilityType = new SGrSerialInterfaceCapabilityTypeImpl();
		return sGrSerialInterfaceCapabilityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrSmoothTransitionType createSGrSmoothTransitionType() {
		SGrSmoothTransitionTypeImpl sGrSmoothTransitionType = new SGrSmoothTransitionTypeImpl();
		return sGrSmoothTransitionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrStabilityFallbackType createSGrStabilityFallbackType() {
		SGrStabilityFallbackTypeImpl sGrStabilityFallbackType = new SGrStabilityFallbackTypeImpl();
		return sGrStabilityFallbackType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrTimeSyncBlockNotificationType createSGrTimeSyncBlockNotificationType() {
		SGrTimeSyncBlockNotificationTypeImpl sGrTimeSyncBlockNotificationType = new SGrTimeSyncBlockNotificationTypeImpl();
		return sGrTimeSyncBlockNotificationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrVersionNumberType createSGrVersionNumberType() {
		SGrVersionNumberTypeImpl sGrVersionNumberType = new SGrVersionNumberTypeImpl();
		return sGrVersionNumberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TCPDevInstanceType createTCPDevInstanceType() {
		TCPDevInstanceTypeImpl tcpDevInstanceType = new TCPDevInstanceTypeImpl();
		return tcpDevInstanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TCPtrspSrvInstanceType createTCPtrspSrvInstanceType() {
		TCPtrspSrvInstanceTypeImpl tcPtrspSrvInstanceType = new TCPtrspSrvInstanceTypeImpl();
		return tcPtrspSrvInstanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TCPType createTCPType() {
		TCPTypeImpl tcpType = new TCPTypeImpl();
		return tcpType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TPIPModbus createTPIPModbus() {
		TPIPModbusImpl tpipModbus = new TPIPModbusImpl();
		return tpipModbus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TPipV4genAddrType createTPipV4genAddrType() {
		TPipV4genAddrTypeImpl tPipV4genAddrType = new TPipV4genAddrTypeImpl();
		return tPipV4genAddrType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TPipV6genAddrType createTPipV6genAddrType() {
		TPipV6genAddrTypeImpl tPipV6genAddrType = new TPipV6genAddrTypeImpl();
		return tPipV6genAddrType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TPRTUModbus createTPRTUModbus() {
		TPRTUModbusImpl tprtuModbus = new TPRTUModbusImpl();
		return tprtuModbus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TrspServiceModbusType createTrspServiceModbusType() {
		TrspServiceModbusTypeImpl trspServiceModbusType = new TrspServiceModbusTypeImpl();
		return trspServiceModbusType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TSGrModbusRegisterRef createTSGrModbusRegisterRef() {
		TSGrModbusRegisterRefImpl tsGrModbusRegisterRef = new TSGrModbusRegisterRefImpl();
		return tsGrModbusRegisterRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaDHWOpModeType createCtaDHWOpModeTypeFromString(EDataType eDataType, String initialValue) {
		CtaDHWOpModeType result = CtaDHWOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaDHWOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHCOpModeType createCtaHCOpModeTypeFromString(EDataType eDataType, String initialValue) {
		CtaHCOpModeType result = CtaHCOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHCOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHPOpModeType createCtaHPOpModeTypeFromString(EDataType eDataType, String initialValue) {
		CtaHPOpModeType result = CtaHPOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHPOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHPOpStateType createCtaHPOpStateTypeFromString(EDataType eDataType, String initialValue) {
		CtaHPOpStateType result = CtaHPOpStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHPOpStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EBaudRateType createEBaudRateTypeFromString(EDataType eDataType, String initialValue) {
		EBaudRateType result = EBaudRateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEBaudRateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EByteLenType createEByteLenTypeFromString(EDataType eDataType, String initialValue) {
		EByteLenType result = EByteLenType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEByteLenTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EParityType createEParityTypeFromString(EDataType eDataType, String initialValue) {
		EParityType result = EParityType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEParityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStopBitLenType createEStopBitLenTypeFromString(EDataType eDataType, String initialValue) {
		EStopBitLenType result = EStopBitLenType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEStopBitLenTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovBufferStateType createHovBufferStateTypeFromString(EDataType eDataType, String initialValue) {
		HovBufferStateType result = HovBufferStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovBufferStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovDomHotWOpModeType createHovDomHotWOpModeTypeFromString(EDataType eDataType, String initialValue) {
		HovDomHotWOpModeType result = HovDomHotWOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovDomHotWOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovDomHotWStateType createHovDomHotWStateTypeFromString(EDataType eDataType, String initialValue) {
		HovDomHotWStateType result = HovDomHotWStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovDomHotWStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHCOpModeType createHovHCOpModeTypeFromString(EDataType eDataType, String initialValue) {
		HovHCOpModeType result = HovHCOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHCOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHCOpStateType createHovHCOpStateTypeFromString(EDataType eDataType, String initialValue) {
		HovHCOpStateType result = HovHCOpStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHCOpStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPActBufferStateType createHovHPActBufferStateTypeFromString(EDataType eDataType, String initialValue) {
		HovHPActBufferStateType result = HovHPActBufferStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPActBufferStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPOpModeType createHovHPOpModeTypeFromString(EDataType eDataType, String initialValue) {
		HovHPOpModeType result = HovHPOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPOpStateType createHovHPOpStateTypeFromString(EDataType eDataType, String initialValue) {
		HovHPOpStateType result = HovHPOpStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPOpStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovSGReadySrcSelType createHovSGReadySrcSelTypeFromString(EDataType eDataType, String initialValue) {
		HovSGReadySrcSelType result = HovSGReadySrcSelType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovSGReadySrcSelTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovType createHovTypeFromString(EDataType eDataType, String initialValue) {
		HovType result = HovType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpMethod createHttpMethodFromString(EDataType eDataType, String initialValue) {
		HttpMethod result = HttpMethod.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHttpMethodToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LevelOfOperationType createLevelOfOperationTypeFromString(EDataType eDataType, String initialValue) {
		LevelOfOperationType result = LevelOfOperationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLevelOfOperationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterFunctionsSupportedType createMasterFunctionsSupportedTypeFromString(EDataType eDataType, String initialValue) {
		MasterFunctionsSupportedType result = MasterFunctionsSupportedType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMasterFunctionsSupportedTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModbusInterfaceSelectionType createModbusInterfaceSelectionTypeFromString(EDataType eDataType, String initialValue) {
		ModbusInterfaceSelectionType result = ModbusInterfaceSelectionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModbusInterfaceSelectionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfileTypeEnumType createProfileTypeEnumTypeFromString(EDataType eDataType, String initialValue) {
		ProfileTypeEnumType result = ProfileTypeEnumType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProfileTypeEnumTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseQueryType createResponseQueryTypeFromString(EDataType eDataType, String initialValue) {
		ResponseQueryType result = ResponseQueryType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResponseQueryTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrBool2BitRankType createSGrBool2BitRankTypeFromString(EDataType eDataType, String initialValue) {
		SGrBool2BitRankType result = SGrBool2BitRankType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrBool2BitRankTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrDeviceKindType createSGrDeviceKindTypeFromString(EDataType eDataType, String initialValue) {
		SGrDeviceKindType result = SGrDeviceKindType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrDeviceKindTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGReadyStateLv1Type createSGReadyStateLv1TypeFromString(EDataType eDataType, String initialValue) {
		SGReadyStateLv1Type result = SGReadyStateLv1Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGReadyStateLv1TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGReadyStateLv2Type createSGReadyStateLv2TypeFromString(EDataType eDataType, String initialValue) {
		SGReadyStateLv2Type result = SGReadyStateLv2Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGReadyStateLv2TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVSEStateLv1Type createSGrEVSEStateLv1TypeFromString(EDataType eDataType, String initialValue) {
		SGrEVSEStateLv1Type result = SGrEVSEStateLv1Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVSEStateLv1TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVSEStateLv2Type createSGrEVSEStateLv2TypeFromString(EDataType eDataType, String initialValue) {
		SGrEVSEStateLv2Type result = SGrEVSEStateLv2Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVSEStateLv2TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVStateType createSGrEVStateTypeFromString(EDataType eDataType, String initialValue) {
		SGrEVStateType result = SGrEVStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHCOpModeType createSGrHCOpModeTypeFromString(EDataType eDataType, String initialValue) {
		SGrHCOpModeType result = SGrHCOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHCOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SgrHCOpStateType createSgrHCOpStateTypeFromString(EDataType eDataType, String initialValue) {
		SgrHCOpStateType result = SgrHCOpStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSgrHCOpStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHPOpModeType createSGrHPOpModeTypeFromString(EDataType eDataType, String initialValue) {
		SGrHPOpModeType result = SGrHPOpModeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHPOpModeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHPOpstateType createSGrHPOpstateTypeFromString(EDataType eDataType, String initialValue) {
		SGrHPOpstateType result = SGrHPOpstateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHPOpstateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrLanguageType createSGrLanguageTypeFromString(EDataType eDataType, String initialValue) {
		SGrLanguageType result = SGrLanguageType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrLanguageTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrManufacturerIDType createSGrManufacturerIDTypeFromString(EDataType eDataType, String initialValue) {
		SGrManufacturerIDType result = SGrManufacturerIDType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrManufacturerIDTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMeasValueSourceType createSGrMeasValueSourceTypeFromString(EDataType eDataType, String initialValue) {
		SGrMeasValueSourceType result = SGrMeasValueSourceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMeasValueSourceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMeasValueType createSGrMeasValueTypeFromString(EDataType eDataType, String initialValue) {
		SGrMeasValueType result = SGrMeasValueType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMeasValueTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrModbusLayer6DeviationType createSGrModbusLayer6DeviationTypeFromString(EDataType eDataType, String initialValue) {
		SGrModbusLayer6DeviationType result = SGrModbusLayer6DeviationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrModbusLayer6DeviationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMROPresenceLevelIndicationType createSGrMROPresenceLevelIndicationTypeFromString(EDataType eDataType, String initialValue) {
		SGrMROPresenceLevelIndicationType result = SGrMROPresenceLevelIndicationType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMROPresenceLevelIndicationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrObligLvlType createSGrObligLvlTypeFromString(EDataType eDataType, String initialValue) {
		SGrObligLvlType result = SGrObligLvlType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrObligLvlTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrOCPPStateType createSGrOCPPStateTypeFromString(EDataType eDataType, String initialValue) {
		SGrOCPPStateType result = SGrOCPPStateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrOCPPStateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrPowerSourceType createSGrPowerSourceTypeFromString(EDataType eDataType, String initialValue) {
		SGrPowerSourceType result = SGrPowerSourceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrPowerSourceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrReleaseState createSGrReleaseStateFromString(EDataType eDataType, String initialValue) {
		SGrReleaseState result = SGrReleaseState.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrReleaseStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIAuthenticationEnumMethodType createSGrRestAPIAuthenticationEnumMethodTypeFromString(EDataType eDataType, String initialValue) {
		SGrRestAPIAuthenticationEnumMethodType result = SGrRestAPIAuthenticationEnumMethodType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIAuthenticationEnumMethodTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIdataTypeType createSGrRestAPIdataTypeTypeFromString(EDataType eDataType, String initialValue) {
		SGrRestAPIdataTypeType result = SGrRestAPIdataTypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIdataTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIInterfaceSelectiontype createSGrRestAPIInterfaceSelectiontypeFromString(EDataType eDataType, String initialValue) {
		SGrRestAPIInterfaceSelectiontype result = SGrRestAPIInterfaceSelectiontype.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIInterfaceSelectiontypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRWPType createSGrRWPTypeFromString(EDataType eDataType, String initialValue) {
		SGrRWPType result = SGrRWPType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRWPTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPFeedInStateLv2Type createSGrSGCPFeedInStateLv2TypeFromString(EDataType eDataType, String initialValue) {
		SGrSGCPFeedInStateLv2Type result = SGrSGCPFeedInStateLv2Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPFeedInStateLv2TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPLoadStateLv2Type createSGrSGCPLoadStateLv2TypeFromString(EDataType eDataType, String initialValue) {
		SGrSGCPLoadStateLv2Type result = SGrSGCPLoadStateLv2Type.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPLoadStateLv2TypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPServiceType createSGrSGCPServiceTypeFromString(EDataType eDataType, String initialValue) {
		SGrSGCPServiceType result = SGrSGCPServiceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPServiceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSunspStateCodesType createSGrSunspStateCodesTypeFromString(EDataType eDataType, String initialValue) {
		SGrSunspStateCodesType result = SGrSunspStateCodesType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSunspStateCodesTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrTransportServicesUsedListType createSGrTransportServicesUsedListTypeFromString(EDataType eDataType, String initialValue) {
		SGrTransportServicesUsedListType result = SGrTransportServicesUsedListType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrTransportServicesUsedListTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrUnits createSGrUnitsFromString(EDataType eDataType, String initialValue) {
		SGrUnits result = SGrUnits.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrUnitsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StiHPOpstateType createStiHPOpstateTypeFromString(EDataType eDataType, String initialValue) {
		StiHPOpstateType result = StiHPOpstateType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStiHPOpstateTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubProfileTypeEnumType createSubProfileTypeEnumTypeFromString(EDataType eDataType, String initialValue) {
		SubProfileTypeEnumType result = SubProfileTypeEnumType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSubProfileTypeEnumTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumConversionFct createTEnumConversionFctFromString(EDataType eDataType, String initialValue) {
		TEnumConversionFct result = TEnumConversionFct.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumConversionFctToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumExceptionCodeType createTEnumExceptionCodeTypeFromString(EDataType eDataType, String initialValue) {
		TEnumExceptionCodeType result = TEnumExceptionCodeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumExceptionCodeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumObjectType createTEnumObjectTypeFromString(EDataType eDataType, String initialValue) {
		TEnumObjectType result = TEnumObjectType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumObjectTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public byte[] createBitmaskTypeFromString(EDataType eDataType, String initialValue) {
		return (byte[])XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.HEX_BINARY, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBitmaskTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.HEX_BINARY, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaDHWOpModeType createCtaDHWOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCtaDHWOpModeTypeFromString(V0Package.eINSTANCE.getCtaDHWOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaDHWOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCtaDHWOpModeTypeToString(V0Package.eINSTANCE.getCtaDHWOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHCOpModeType createCtaHCOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCtaHCOpModeTypeFromString(V0Package.eINSTANCE.getCtaHCOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHCOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCtaHCOpModeTypeToString(V0Package.eINSTANCE.getCtaHCOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHPOpModeType createCtaHPOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCtaHPOpModeTypeFromString(V0Package.eINSTANCE.getCtaHPOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHPOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCtaHPOpModeTypeToString(V0Package.eINSTANCE.getCtaHPOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtaHPOpStateType createCtaHPOpStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCtaHPOpStateTypeFromString(V0Package.eINSTANCE.getCtaHPOpStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCtaHPOpStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCtaHPOpStateTypeToString(V0Package.eINSTANCE.getCtaHPOpStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer createDpSizeNrRegistersTypeFromString(EDataType eDataType, String initialValue) {
		return (Integer)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.INT, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDpSizeNrRegistersTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.INT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer createDpSizeNrRegistersTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createDpSizeNrRegistersTypeFromString(V0Package.eINSTANCE.getDpSizeNrRegistersType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDpSizeNrRegistersTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDpSizeNrRegistersTypeToString(V0Package.eINSTANCE.getDpSizeNrRegistersType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EBaudRateType createEBaudRateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createEBaudRateTypeFromString(V0Package.eINSTANCE.getEBaudRateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEBaudRateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertEBaudRateTypeToString(V0Package.eINSTANCE.getEBaudRateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EByteLenType createEByteLenTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createEByteLenTypeFromString(V0Package.eINSTANCE.getEByteLenType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEByteLenTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertEByteLenTypeToString(V0Package.eINSTANCE.getEByteLenType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EParityType createEParityTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createEParityTypeFromString(V0Package.eINSTANCE.getEParityType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEParityTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertEParityTypeToString(V0Package.eINSTANCE.getEParityType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStopBitLenType createEStopBitLenTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createEStopBitLenTypeFromString(V0Package.eINSTANCE.getEStopBitLenType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEStopBitLenTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertEStopBitLenTypeToString(V0Package.eINSTANCE.getEStopBitLenType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovBufferStateType createHovBufferStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovBufferStateTypeFromString(V0Package.eINSTANCE.getHovBufferStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovBufferStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovBufferStateTypeToString(V0Package.eINSTANCE.getHovBufferStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovDomHotWOpModeType createHovDomHotWOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovDomHotWOpModeTypeFromString(V0Package.eINSTANCE.getHovDomHotWOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovDomHotWOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovDomHotWOpModeTypeToString(V0Package.eINSTANCE.getHovDomHotWOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovDomHotWStateType createHovDomHotWStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovDomHotWStateTypeFromString(V0Package.eINSTANCE.getHovDomHotWStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovDomHotWStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovDomHotWStateTypeToString(V0Package.eINSTANCE.getHovDomHotWStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHCOpModeType createHovHCOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovHCOpModeTypeFromString(V0Package.eINSTANCE.getHovHCOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHCOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovHCOpModeTypeToString(V0Package.eINSTANCE.getHovHCOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHCOpStateType createHovHCOpStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovHCOpStateTypeFromString(V0Package.eINSTANCE.getHovHCOpStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHCOpStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovHCOpStateTypeToString(V0Package.eINSTANCE.getHovHCOpStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPActBufferStateType createHovHPActBufferStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovHPActBufferStateTypeFromString(V0Package.eINSTANCE.getHovHPActBufferStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPActBufferStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovHPActBufferStateTypeToString(V0Package.eINSTANCE.getHovHPActBufferStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPOpModeType createHovHPOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovHPOpModeTypeFromString(V0Package.eINSTANCE.getHovHPOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovHPOpModeTypeToString(V0Package.eINSTANCE.getHovHPOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovHPOpStateType createHovHPOpStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovHPOpStateTypeFromString(V0Package.eINSTANCE.getHovHPOpStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovHPOpStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovHPOpStateTypeToString(V0Package.eINSTANCE.getHovHPOpStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovSGReadySrcSelType createHovSGReadySrcSelTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovSGReadySrcSelTypeFromString(V0Package.eINSTANCE.getHovSGReadySrcSelType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovSGReadySrcSelTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovSGReadySrcSelTypeToString(V0Package.eINSTANCE.getHovSGReadySrcSelType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HovType createHovTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createHovTypeFromString(V0Package.eINSTANCE.getHovType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHovTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHovTypeToString(V0Package.eINSTANCE.getHovType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HttpMethod createHttpMethodObjectFromString(EDataType eDataType, String initialValue) {
		return createHttpMethodFromString(V0Package.eINSTANCE.getHttpMethod(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHttpMethodObjectToString(EDataType eDataType, Object instanceValue) {
		return convertHttpMethodToString(V0Package.eINSTANCE.getHttpMethod(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n1TypeFromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n1TypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n1Type1FromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n1Type1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n2TypeFromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n2TypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n2Type1FromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n2Type1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n3TypeFromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n3TypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n3Type1FromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n3Type1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n4TypeFromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n4TypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createIpV4n4Type1FromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.POSITIVE_INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4n4Type1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.POSITIVE_INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long createIpV4portNrTypeFromString(EDataType eDataType, String initialValue) {
		return (Long)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.UNSIGNED_INT, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4portNrTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.UNSIGNED_INT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long createIpV4portNrTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createIpV4portNrTypeFromString(V0Package.eINSTANCE.getIpV4portNrType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIpV4portNrTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertIpV4portNrTypeToString(V0Package.eINSTANCE.getIpV4portNrType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LevelOfOperationType createLevelOfOperationTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createLevelOfOperationTypeFromString(V0Package.eINSTANCE.getLevelOfOperationType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLevelOfOperationTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertLevelOfOperationTypeToString(V0Package.eINSTANCE.getLevelOfOperationType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterFunctionsSupportedType createMasterFunctionsSupportedTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createMasterFunctionsSupportedTypeFromString(V0Package.eINSTANCE.getMasterFunctionsSupportedType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMasterFunctionsSupportedTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertMasterFunctionsSupportedTypeToString(V0Package.eINSTANCE.getMasterFunctionsSupportedType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModbusInterfaceSelectionType createModbusInterfaceSelectionTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createModbusInterfaceSelectionTypeFromString(V0Package.eINSTANCE.getModbusInterfaceSelectionType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModbusInterfaceSelectionTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertModbusInterfaceSelectionTypeToString(V0Package.eINSTANCE.getModbusInterfaceSelectionType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPlaceHolder4futureExtensionsTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlaceHolder4futureExtensionsTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPrelimStringDefTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPrelimStringDefTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfileTypeEnumType createProfileTypeEnumTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createProfileTypeEnumTypeFromString(V0Package.eINSTANCE.getProfileTypeEnumType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProfileTypeEnumTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertProfileTypeEnumTypeToString(V0Package.eINSTANCE.getProfileTypeEnumType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResponseQueryType createResponseQueryTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createResponseQueryTypeFromString(V0Package.eINSTANCE.getResponseQueryType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResponseQueryTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertResponseQueryTypeToString(V0Package.eINSTANCE.getResponseQueryType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRestAPIEndPointTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestAPIEndPointTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRestAPIJMESPathTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestAPIJMESPathTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRestBasicPasswordTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestBasicPasswordTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRestBasicUsernameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRestBasicUsernameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrBool2BitRankType createSGrBool2BitRankTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrBool2BitRankTypeFromString(V0Package.eINSTANCE.getSGrBool2BitRankType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrBool2BitRankTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrBool2BitRankTypeToString(V0Package.eINSTANCE.getSGrBool2BitRankType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrDeviceKindType createSGrDeviceKindTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrDeviceKindTypeFromString(V0Package.eINSTANCE.getSGrDeviceKindType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrDeviceKindTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrDeviceKindTypeToString(V0Package.eINSTANCE.getSGrDeviceKindType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGReadyStateLv1Type createSGReadyStateLv1TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGReadyStateLv1TypeFromString(V0Package.eINSTANCE.getSGReadyStateLv1Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGReadyStateLv1TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGReadyStateLv1TypeToString(V0Package.eINSTANCE.getSGReadyStateLv1Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGReadyStateLv2Type createSGReadyStateLv2TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGReadyStateLv2TypeFromString(V0Package.eINSTANCE.getSGReadyStateLv2Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGReadyStateLv2TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGReadyStateLv2TypeToString(V0Package.eINSTANCE.getSGReadyStateLv2Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVSEStateLv1Type createSGrEVSEStateLv1TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrEVSEStateLv1TypeFromString(V0Package.eINSTANCE.getSGrEVSEStateLv1Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVSEStateLv1TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrEVSEStateLv1TypeToString(V0Package.eINSTANCE.getSGrEVSEStateLv1Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVSEStateLv2Type createSGrEVSEStateLv2TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrEVSEStateLv2TypeFromString(V0Package.eINSTANCE.getSGrEVSEStateLv2Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVSEStateLv2TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrEVSEStateLv2TypeToString(V0Package.eINSTANCE.getSGrEVSEStateLv2Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrEVStateType createSGrEVStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrEVStateTypeFromString(V0Package.eINSTANCE.getSGrEVStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrEVStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrEVStateTypeToString(V0Package.eINSTANCE.getSGrEVStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHCOpModeType createSGrHCOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrHCOpModeTypeFromString(V0Package.eINSTANCE.getSGrHCOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHCOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrHCOpModeTypeToString(V0Package.eINSTANCE.getSGrHCOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SgrHCOpStateType createSgrHCOpStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSgrHCOpStateTypeFromString(V0Package.eINSTANCE.getSgrHCOpStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSgrHCOpStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSgrHCOpStateTypeToString(V0Package.eINSTANCE.getSgrHCOpStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHPOpModeType createSGrHPOpModeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrHPOpModeTypeFromString(V0Package.eINSTANCE.getSGrHPOpModeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHPOpModeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrHPOpModeTypeToString(V0Package.eINSTANCE.getSGrHPOpModeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrHPOpstateType createSGrHPOpstateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrHPOpstateTypeFromString(V0Package.eINSTANCE.getSGrHPOpstateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrHPOpstateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrHPOpstateTypeToString(V0Package.eINSTANCE.getSGrHPOpstateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrLanguageType createSGrLanguageTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrLanguageTypeFromString(V0Package.eINSTANCE.getSGrLanguageType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrLanguageTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrLanguageTypeToString(V0Package.eINSTANCE.getSGrLanguageType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrManufacturerIDType createSGrManufacturerIDTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrManufacturerIDTypeFromString(V0Package.eINSTANCE.getSGrManufacturerIDType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrManufacturerIDTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrManufacturerIDTypeToString(V0Package.eINSTANCE.getSGrManufacturerIDType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMeasValueSourceType createSGrMeasValueSourceTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrMeasValueSourceTypeFromString(V0Package.eINSTANCE.getSGrMeasValueSourceType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMeasValueSourceTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrMeasValueSourceTypeToString(V0Package.eINSTANCE.getSGrMeasValueSourceType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMeasValueType createSGrMeasValueTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrMeasValueTypeFromString(V0Package.eINSTANCE.getSGrMeasValueType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMeasValueTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrMeasValueTypeToString(V0Package.eINSTANCE.getSGrMeasValueType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrModbusLayer6DeviationType createSGrModbusLayer6DeviationTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrModbusLayer6DeviationTypeFromString(V0Package.eINSTANCE.getSGrModbusLayer6DeviationType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrModbusLayer6DeviationTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrModbusLayer6DeviationTypeToString(V0Package.eINSTANCE.getSGrModbusLayer6DeviationType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrMROPresenceLevelIndicationType createSGrMROPresenceLevelIndicationTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrMROPresenceLevelIndicationTypeFromString(V0Package.eINSTANCE.getSGrMROPresenceLevelIndicationType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrMROPresenceLevelIndicationTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrMROPresenceLevelIndicationTypeToString(V0Package.eINSTANCE.getSGrMROPresenceLevelIndicationType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrObligLvlType createSGrObligLvlTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrObligLvlTypeFromString(V0Package.eINSTANCE.getSGrObligLvlType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrObligLvlTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrObligLvlTypeToString(V0Package.eINSTANCE.getSGrObligLvlType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrOCPPStateType createSGrOCPPStateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrOCPPStateTypeFromString(V0Package.eINSTANCE.getSGrOCPPStateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrOCPPStateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrOCPPStateTypeToString(V0Package.eINSTANCE.getSGrOCPPStateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrPowerSourceType createSGrPowerSourceTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrPowerSourceTypeFromString(V0Package.eINSTANCE.getSGrPowerSourceType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrPowerSourceTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrPowerSourceTypeToString(V0Package.eINSTANCE.getSGrPowerSourceType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Double createSGrPrecisionTypeFromString(EDataType eDataType, String initialValue) {
		return (Double)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.DOUBLE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrPrecisionTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.DOUBLE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Double createSGrPrecisionTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrPrecisionTypeFromString(V0Package.eINSTANCE.getSGrPrecisionType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrPrecisionTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrPrecisionTypeToString(V0Package.eINSTANCE.getSGrPrecisionType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrReleaseState createSGrReleaseStateObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrReleaseStateFromString(V0Package.eINSTANCE.getSGrReleaseState(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrReleaseStateObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrReleaseStateToString(V0Package.eINSTANCE.getSGrReleaseState(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIAuthenticationEnumMethodType createSGrRestAPIAuthenticationEnumMethodTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrRestAPIAuthenticationEnumMethodTypeFromString(V0Package.eINSTANCE.getSGrRestAPIAuthenticationEnumMethodType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIAuthenticationEnumMethodTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrRestAPIAuthenticationEnumMethodTypeToString(V0Package.eINSTANCE.getSGrRestAPIAuthenticationEnumMethodType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIdataTypeType createSGrRestAPIdataTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrRestAPIdataTypeTypeFromString(V0Package.eINSTANCE.getSGrRestAPIdataTypeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIdataTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrRestAPIdataTypeTypeToString(V0Package.eINSTANCE.getSGrRestAPIdataTypeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRestAPIInterfaceSelectiontype createSGrRestAPIInterfaceSelectiontypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrRestAPIInterfaceSelectiontypeFromString(V0Package.eINSTANCE.getSGrRestAPIInterfaceSelectiontype(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRestAPIInterfaceSelectiontypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrRestAPIInterfaceSelectiontypeToString(V0Package.eINSTANCE.getSGrRestAPIInterfaceSelectiontype(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrRWPType createSGrRWPTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrRWPTypeFromString(V0Package.eINSTANCE.getSGrRWPType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrRWPTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrRWPTypeToString(V0Package.eINSTANCE.getSGrRWPType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPFeedInStateLv2Type createSGrSGCPFeedInStateLv2TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrSGCPFeedInStateLv2TypeFromString(V0Package.eINSTANCE.getSGrSGCPFeedInStateLv2Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPFeedInStateLv2TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrSGCPFeedInStateLv2TypeToString(V0Package.eINSTANCE.getSGrSGCPFeedInStateLv2Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPLoadStateLv2Type createSGrSGCPLoadStateLv2TypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrSGCPLoadStateLv2TypeFromString(V0Package.eINSTANCE.getSGrSGCPLoadStateLv2Type(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPLoadStateLv2TypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrSGCPLoadStateLv2TypeToString(V0Package.eINSTANCE.getSGrSGCPLoadStateLv2Type(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSGCPServiceType createSGrSGCPServiceTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrSGCPServiceTypeFromString(V0Package.eINSTANCE.getSGrSGCPServiceType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSGCPServiceTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrSGCPServiceTypeToString(V0Package.eINSTANCE.getSGrSGCPServiceType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createSGrSpecialQualityReqTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSpecialQualityReqTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrSunspStateCodesType createSGrSunspStateCodesTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrSunspStateCodesTypeFromString(V0Package.eINSTANCE.getSGrSunspStateCodesType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrSunspStateCodesTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrSunspStateCodesTypeToString(V0Package.eINSTANCE.getSGrSunspStateCodesType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrTransportServicesUsedListType createSGrTransportServicesUsedListTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrTransportServicesUsedListTypeFromString(V0Package.eINSTANCE.getSGrTransportServicesUsedListType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrTransportServicesUsedListTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrTransportServicesUsedListTypeToString(V0Package.eINSTANCE.getSGrTransportServicesUsedListType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGrUnits createSGrUnitsObjectFromString(EDataType eDataType, String initialValue) {
		return createSGrUnitsFromString(V0Package.eINSTANCE.getSGrUnits(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSGrUnitsObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSGrUnitsToString(V0Package.eINSTANCE.getSGrUnits(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StiHPOpstateType createStiHPOpstateTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createStiHPOpstateTypeFromString(V0Package.eINSTANCE.getStiHPOpstateType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStiHPOpstateTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertStiHPOpstateTypeToString(V0Package.eINSTANCE.getStiHPOpstateType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubProfileTypeEnumType createSubProfileTypeEnumTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSubProfileTypeEnumTypeFromString(V0Package.eINSTANCE.getSubProfileTypeEnumType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSubProfileTypeEnumTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSubProfileTypeEnumTypeToString(V0Package.eINSTANCE.getSubProfileTypeEnumType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Short createTBitRankFromString(EDataType eDataType, String initialValue) {
		return (Short)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.UNSIGNED_BYTE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTBitRankToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.UNSIGNED_BYTE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Short createTBitRankObjectFromString(EDataType eDataType, String initialValue) {
		return createTBitRankFromString(V0Package.eINSTANCE.getTBitRank(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTBitRankObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTBitRankToString(V0Package.eINSTANCE.getTBitRank(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumConversionFct createTEnumConversionFctObjectFromString(EDataType eDataType, String initialValue) {
		return createTEnumConversionFctFromString(V0Package.eINSTANCE.getTEnumConversionFct(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumConversionFctObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTEnumConversionFctToString(V0Package.eINSTANCE.getTEnumConversionFct(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumExceptionCodeType createTEnumExceptionCodeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTEnumExceptionCodeTypeFromString(V0Package.eINSTANCE.getTEnumExceptionCodeType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumExceptionCodeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTEnumExceptionCodeTypeToString(V0Package.eINSTANCE.getTEnumExceptionCodeType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TEnumObjectType createTEnumObjectTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTEnumObjectTypeFromString(V0Package.eINSTANCE.getTEnumObjectType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTEnumObjectTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTEnumObjectTypeToString(V0Package.eINSTANCE.getTEnumObjectType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createTextElementTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTextElementTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long createTIPportNrTypeFromString(EDataType eDataType, String initialValue) {
		return (Long)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.UNSIGNED_INT, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTIPportNrTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.UNSIGNED_INT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Long createTIPportNrTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTIPportNrTypeFromString(V0Package.eINSTANCE.getTIPportNrType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTIPportNrTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTIPportNrTypeToString(V0Package.eINSTANCE.getTIPportNrType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer createTSlaveIDTypeFromString(EDataType eDataType, String initialValue) {
		return (Integer)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.UNSIGNED_SHORT, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTSlaveIDTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.UNSIGNED_SHORT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer createTSlaveIDTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTSlaveIDTypeFromString(V0Package.eINSTANCE.getTSlaveIDType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTSlaveIDTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTSlaveIDTypeToString(V0Package.eINSTANCE.getTSlaveIDType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public V0Package getV0Package() {
		return (V0Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static V0Package getPackage() {
		return V0Package.eINSTANCE;
	}

} //V0FactoryImpl
