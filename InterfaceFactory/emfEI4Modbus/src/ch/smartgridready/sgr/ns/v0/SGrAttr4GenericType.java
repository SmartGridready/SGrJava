/**
 */
package ch.smartgridready.sgr.ns.v0;

import java.math.BigInteger;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SGr Attr4 Generic Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * these attributes are defined for the generic interface
 * 				for the application programmer. An attribute can be aligned to
 * 				either a device, a functional Profile or a datapoint
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxVal <em>Max Val</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinVal <em>Min Val</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSpecQualityRequirement <em>Spec Quality Requirement</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getPrecision <em>Precision</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getStabilityFallback <em>Stability Fallback</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSmoothTransition <em>Smooth Transition</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendDelta <em>Min Send Delta</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxSendTime <em>Max Send Time</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxReceiveTime <em>Max Receive Time</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendTime <em>Min Send Time</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLatencyTime <em>Max Latency Time</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getTimeStampLog <em>Time Stamp Log</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getTimeRange <em>Time Range</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueState <em>Value State</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueTendency <em>Value Tendency</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueSource <em>Value Source</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSampleRate <em>Sample Rate</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getCurtailment <em>Curtailment</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinLoad <em>Min Load</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLockTimeMinutes <em>Max Lock Time Minutes</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinRunTimeMinutes <em>Min Run Time Minutes</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueByTimeTableMinutes <em>Value By Time Table Minutes</em>}</li>
 *   <li>{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getFlexAssistance <em>Flex Assistance</em>}</li>
 * </ul>
 *
 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType()
 * @model extendedMetaData="name='SGrAttr4GenericType' kind='elementOnly'"
 * @generated
 */
public interface SGrAttr4GenericType extends EObject {
	/**
	 * Returns the value of the '<em><b>Max Val</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  upper range limit. Unit:inherited
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Val</em>' attribute.
	 * @see #isSetMaxVal()
	 * @see #unsetMaxVal()
	 * @see #setMaxVal(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MaxVal()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='maxVal' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMaxVal();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxVal <em>Max Val</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Val</em>' attribute.
	 * @see #isSetMaxVal()
	 * @see #unsetMaxVal()
	 * @see #getMaxVal()
	 * @generated
	 */
	void setMaxVal(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxVal <em>Max Val</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxVal()
	 * @see #getMaxVal()
	 * @see #setMaxVal(float)
	 * @generated
	 */
	void unsetMaxVal();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxVal <em>Max Val</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Val</em>' attribute is set.
	 * @see #unsetMaxVal()
	 * @see #getMaxVal()
	 * @see #setMaxVal(float)
	 * @generated
	 */
	boolean isSetMaxVal();

	/**
	 * Returns the value of the '<em><b>Min Val</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  lower range limit. Unit:inherited
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Val</em>' attribute.
	 * @see #isSetMinVal()
	 * @see #unsetMinVal()
	 * @see #setMinVal(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MinVal()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='minVal' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMinVal();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinVal <em>Min Val</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Val</em>' attribute.
	 * @see #isSetMinVal()
	 * @see #unsetMinVal()
	 * @see #getMinVal()
	 * @generated
	 */
	void setMinVal(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinVal <em>Min Val</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinVal()
	 * @see #getMinVal()
	 * @see #setMinVal(float)
	 * @generated
	 */
	void unsetMinVal();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinVal <em>Min Val</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Val</em>' attribute is set.
	 * @see #unsetMinVal()
	 * @see #getMinVal()
	 * @see #setMinVal(float)
	 * @generated
	 */
	boolean isSetMinVal();

	/**
	 * Returns the value of the '<em><b>Spec Quality Requirement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  indicates Quality requirements fullfilled like
	 * 						formal certifications
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Spec Quality Requirement</em>' attribute.
	 * @see #setSpecQualityRequirement(String)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_SpecQualityRequirement()
	 * @model dataType="ch.smartgridready.sgr.ns.v0.SGrSpecialQualityReqType"
	 *        extendedMetaData="kind='element' name='specQualityRequirement' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSpecQualityRequirement();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSpecQualityRequirement <em>Spec Quality Requirement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spec Quality Requirement</em>' attribute.
	 * @see #getSpecQualityRequirement()
	 * @generated
	 */
	void setSpecQualityRequirement(String value);

	/**
	 * Returns the value of the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  the precision of a measurement, calculation result
	 * 						or result of a controls process
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Precision</em>' attribute.
	 * @see #isSetPrecision()
	 * @see #unsetPrecision()
	 * @see #setPrecision(double)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_Precision()
	 * @model unsettable="true" dataType="ch.smartgridready.sgr.ns.v0.SGrPrecisionType"
	 *        extendedMetaData="kind='element' name='precision' namespace='##targetNamespace'"
	 * @generated
	 */
	double getPrecision();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getPrecision <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precision</em>' attribute.
	 * @see #isSetPrecision()
	 * @see #unsetPrecision()
	 * @see #getPrecision()
	 * @generated
	 */
	void setPrecision(double value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getPrecision <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPrecision()
	 * @see #getPrecision()
	 * @see #setPrecision(double)
	 * @generated
	 */
	void unsetPrecision();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getPrecision <em>Precision</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Precision</em>' attribute is set.
	 * @see #unsetPrecision()
	 * @see #getPrecision()
	 * @see #setPrecision(double)
	 * @generated
	 */
	boolean isSetPrecision();

	/**
	 * Returns the value of the '<em><b>Stability Fallback</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  A consumer or a generating system receives the
	 * 						permit for a load change for a certain period of time. This time
	 * 						is always set to 0 each time a confirmation message is received
	 * 						(HeartBeat).
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stability Fallback</em>' containment reference.
	 * @see #setStabilityFallback(SGrStabilityFallbackType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_StabilityFallback()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='stabilityFallback' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrStabilityFallbackType getStabilityFallback();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getStabilityFallback <em>Stability Fallback</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stability Fallback</em>' containment reference.
	 * @see #getStabilityFallback()
	 * @generated
	 */
	void setStabilityFallback(SGrStabilityFallbackType value);

	/**
	 * Returns the value of the '<em><b>Smooth Transition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The time behavior of a transition from a power
	 * 						adjustment (positive as well as negative) can be determined by
	 * 						several time values, so that this starts with a random time delay,
	 * 						changes via a ramp and an expiry time with return to the initial
	 * 						value.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Smooth Transition</em>' containment reference.
	 * @see #setSmoothTransition(SGrSmoothTransitionType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_SmoothTransition()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='smoothTransition' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrSmoothTransitionType getSmoothTransition();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSmoothTransition <em>Smooth Transition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Smooth Transition</em>' containment reference.
	 * @see #getSmoothTransition()
	 * @generated
	 */
	void setSmoothTransition(SGrSmoothTransitionType value);

	/**
	 * Returns the value of the '<em><b>Min Send Delta</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  A measured value difference that must lead to a
	 * 						notification . Unit: inheritted
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Send Delta</em>' attribute.
	 * @see #isSetMinSendDelta()
	 * @see #unsetMinSendDelta()
	 * @see #setMinSendDelta(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MinSendDelta()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='minSendDelta' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMinSendDelta();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendDelta <em>Min Send Delta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Send Delta</em>' attribute.
	 * @see #isSetMinSendDelta()
	 * @see #unsetMinSendDelta()
	 * @see #getMinSendDelta()
	 * @generated
	 */
	void setMinSendDelta(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendDelta <em>Min Send Delta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinSendDelta()
	 * @see #getMinSendDelta()
	 * @see #setMinSendDelta(float)
	 * @generated
	 */
	void unsetMinSendDelta();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendDelta <em>Min Send Delta</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Send Delta</em>' attribute is set.
	 * @see #unsetMinSendDelta()
	 * @see #getMinSendDelta()
	 * @see #setMinSendDelta(float)
	 * @generated
	 */
	boolean isSetMinSendDelta();

	/**
	 * Returns the value of the '<em><b>Max Send Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The maximum time between 2 notifications
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Send Time</em>' attribute.
	 * @see #setMaxSendTime(XMLGregorianCalendar)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MaxSendTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Time"
	 *        extendedMetaData="kind='element' name='maxSendTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getMaxSendTime();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxSendTime <em>Max Send Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Send Time</em>' attribute.
	 * @see #getMaxSendTime()
	 * @generated
	 */
	void setMaxSendTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Max Receive Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Receive Time</em>' attribute.
	 * @see #setMaxReceiveTime(XMLGregorianCalendar)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MaxReceiveTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Time"
	 *        extendedMetaData="kind='element' name='maxReceiveTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getMaxReceiveTime();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxReceiveTime <em>Max Receive Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Receive Time</em>' attribute.
	 * @see #getMaxReceiveTime()
	 * @generated
	 */
	void setMaxReceiveTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Min Send Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The fastest possible or allowable sequence of
	 * 						notifications
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Send Time</em>' attribute.
	 * @see #setMinSendTime(XMLGregorianCalendar)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MinSendTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Time"
	 *        extendedMetaData="kind='element' name='minSendTime' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getMinSendTime();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinSendTime <em>Min Send Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Send Time</em>' attribute.
	 * @see #getMinSendTime()
	 * @generated
	 */
	void setMinSendTime(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Max Latency Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Maximum occurring or permitted delay time, e.g. of
	 * 						a data transaction
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Latency Time</em>' attribute.
	 * @see #setMaxLatencyTime(BigInteger)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MaxLatencyTime()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.UnsignedLong"
	 *        extendedMetaData="kind='element' name='maxLatencyTime' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getMaxLatencyTime();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLatencyTime <em>Max Latency Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Latency Time</em>' attribute.
	 * @see #getMaxLatencyTime()
	 * @generated
	 */
	void setMaxLatencyTime(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Time Stamp Log</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  This is the date Time Value indicates that any
	 * 						value generation must be paired with the time of either a
	 * 						measuement was taken or where a higher controls software level
	 * 						indicates when it got the value
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Time Stamp Log</em>' attribute.
	 * @see #setTimeStampLog(XMLGregorianCalendar)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_TimeStampLog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='element' name='timeStampLog' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getTimeStampLog();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getTimeStampLog <em>Time Stamp Log</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Stamp Log</em>' attribute.
	 * @see #getTimeStampLog()
	 * @generated
	 */
	void setTimeStampLog(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Time Range</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * time range min�max
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Time Range</em>' containment reference.
	 * @see #setTimeRange(SGrTimeRangeType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_TimeRange()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='timeRange' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrTimeRangeType getTimeRange();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getTimeRange <em>Time Range</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Range</em>' containment reference.
	 * @see #getTimeRange()
	 * @generated
	 */
	void setTimeRange(SGrTimeRangeType value);

	/**
	 * Returns the value of the '<em><b>Value State</b></em>' attribute.
	 * The literals are from the enumeration {@link ch.smartgridready.sgr.ns.v0.SGrMeasValueStateType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  MeasValueState: Status / validity of the
	 * 						measurement. Possible values are "normal", "outOfRange", "error"
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value State</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueStateType
	 * @see #isSetValueState()
	 * @see #unsetValueState()
	 * @see #setValueState(SGrMeasValueStateType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_ValueState()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='valueState' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrMeasValueStateType getValueState();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueState <em>Value State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value State</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueStateType
	 * @see #isSetValueState()
	 * @see #unsetValueState()
	 * @see #getValueState()
	 * @generated
	 */
	void setValueState(SGrMeasValueStateType value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueState <em>Value State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValueState()
	 * @see #getValueState()
	 * @see #setValueState(SGrMeasValueStateType)
	 * @generated
	 */
	void unsetValueState();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueState <em>Value State</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value State</em>' attribute is set.
	 * @see #unsetValueState()
	 * @see #getValueState()
	 * @see #setValueState(SGrMeasValueStateType)
	 * @generated
	 */
	boolean isSetValueState();

	/**
	 * Returns the value of the '<em><b>Value Tendency</b></em>' attribute.
	 * The literals are from the enumeration {@link ch.smartgridready.sgr.ns.v0.SGrMeasValueTendencyType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  value trend based on timely changes, potential
	 * 						values are rising, stable, falling
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value Tendency</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueTendencyType
	 * @see #isSetValueTendency()
	 * @see #unsetValueTendency()
	 * @see #setValueTendency(SGrMeasValueTendencyType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_ValueTendency()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='valueTendency' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrMeasValueTendencyType getValueTendency();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueTendency <em>Value Tendency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Tendency</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueTendencyType
	 * @see #isSetValueTendency()
	 * @see #unsetValueTendency()
	 * @see #getValueTendency()
	 * @generated
	 */
	void setValueTendency(SGrMeasValueTendencyType value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueTendency <em>Value Tendency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValueTendency()
	 * @see #getValueTendency()
	 * @see #setValueTendency(SGrMeasValueTendencyType)
	 * @generated
	 */
	void unsetValueTendency();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueTendency <em>Value Tendency</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value Tendency</em>' attribute is set.
	 * @see #unsetValueTendency()
	 * @see #getValueTendency()
	 * @see #setValueTendency(SGrMeasValueTendencyType)
	 * @generated
	 */
	boolean isSetValueTendency();

	/**
	 * Returns the value of the '<em><b>Value Source</b></em>' attribute.
	 * The literals are from the enumeration {@link ch.smartgridready.sgr.ns.v0.SGrMeasValueSourceType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Value source kind related to SGr level 6
	 * 						applications. Potential values are measuredValue, calculatedValue,
	 * 						empiricalValue
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value Source</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueSourceType
	 * @see #isSetValueSource()
	 * @see #unsetValueSource()
	 * @see #setValueSource(SGrMeasValueSourceType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_ValueSource()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='valueSource' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrMeasValueSourceType getValueSource();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueSource <em>Value Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Source</em>' attribute.
	 * @see ch.smartgridready.sgr.ns.v0.SGrMeasValueSourceType
	 * @see #isSetValueSource()
	 * @see #unsetValueSource()
	 * @see #getValueSource()
	 * @generated
	 */
	void setValueSource(SGrMeasValueSourceType value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueSource <em>Value Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValueSource()
	 * @see #getValueSource()
	 * @see #setValueSource(SGrMeasValueSourceType)
	 * @generated
	 */
	void unsetValueSource();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueSource <em>Value Source</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value Source</em>' attribute is set.
	 * @see #unsetValueSource()
	 * @see #getValueSource()
	 * @see #setValueSource(SGrMeasValueSourceType)
	 * @generated
	 */
	boolean isSetValueSource();

	/**
	 * Returns the value of the '<em><b>Sample Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  SampleRate in milliseconds
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sample Rate</em>' attribute.
	 * @see #isSetSampleRate()
	 * @see #unsetSampleRate()
	 * @see #setSampleRate(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_SampleRate()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='sampleRate' namespace='##targetNamespace'"
	 * @generated
	 */
	float getSampleRate();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSampleRate <em>Sample Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sample Rate</em>' attribute.
	 * @see #isSetSampleRate()
	 * @see #unsetSampleRate()
	 * @see #getSampleRate()
	 * @generated
	 */
	void setSampleRate(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSampleRate <em>Sample Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSampleRate()
	 * @see #getSampleRate()
	 * @see #setSampleRate(float)
	 * @generated
	 */
	void unsetSampleRate();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getSampleRate <em>Sample Rate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sample Rate</em>' attribute is set.
	 * @see #unsetSampleRate()
	 * @see #getSampleRate()
	 * @see #setSampleRate(float)
	 * @generated
	 */
	boolean isSetSampleRate();

	/**
	 * Returns the value of the '<em><b>Curtailment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Curtailment</em>' attribute.
	 * @see #isSetCurtailment()
	 * @see #unsetCurtailment()
	 * @see #setCurtailment(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_Curtailment()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='curtailment' namespace='##targetNamespace'"
	 * @generated
	 */
	float getCurtailment();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getCurtailment <em>Curtailment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Curtailment</em>' attribute.
	 * @see #isSetCurtailment()
	 * @see #unsetCurtailment()
	 * @see #getCurtailment()
	 * @generated
	 */
	void setCurtailment(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getCurtailment <em>Curtailment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCurtailment()
	 * @see #getCurtailment()
	 * @see #setCurtailment(float)
	 * @generated
	 */
	void unsetCurtailment();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getCurtailment <em>Curtailment</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Curtailment</em>' attribute is set.
	 * @see #unsetCurtailment()
	 * @see #getCurtailment()
	 * @see #setCurtailment(float)
	 * @generated
	 */
	boolean isSetCurtailment();

	/**
	 * Returns the value of the '<em><b>Min Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Load</em>' attribute.
	 * @see #isSetMinLoad()
	 * @see #unsetMinLoad()
	 * @see #setMinLoad(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MinLoad()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='minLoad' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMinLoad();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinLoad <em>Min Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Load</em>' attribute.
	 * @see #isSetMinLoad()
	 * @see #unsetMinLoad()
	 * @see #getMinLoad()
	 * @generated
	 */
	void setMinLoad(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinLoad <em>Min Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinLoad()
	 * @see #getMinLoad()
	 * @see #setMinLoad(float)
	 * @generated
	 */
	void unsetMinLoad();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinLoad <em>Min Load</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Load</em>' attribute is set.
	 * @see #unsetMinLoad()
	 * @see #getMinLoad()
	 * @see #setMinLoad(float)
	 * @generated
	 */
	boolean isSetMinLoad();

	/**
	 * Returns the value of the '<em><b>Max Lock Time Minutes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Lock Time Minutes</em>' attribute.
	 * @see #isSetMaxLockTimeMinutes()
	 * @see #unsetMaxLockTimeMinutes()
	 * @see #setMaxLockTimeMinutes(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MaxLockTimeMinutes()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='maxLockTimeMinutes' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMaxLockTimeMinutes();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLockTimeMinutes <em>Max Lock Time Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Lock Time Minutes</em>' attribute.
	 * @see #isSetMaxLockTimeMinutes()
	 * @see #unsetMaxLockTimeMinutes()
	 * @see #getMaxLockTimeMinutes()
	 * @generated
	 */
	void setMaxLockTimeMinutes(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLockTimeMinutes <em>Max Lock Time Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxLockTimeMinutes()
	 * @see #getMaxLockTimeMinutes()
	 * @see #setMaxLockTimeMinutes(float)
	 * @generated
	 */
	void unsetMaxLockTimeMinutes();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMaxLockTimeMinutes <em>Max Lock Time Minutes</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Lock Time Minutes</em>' attribute is set.
	 * @see #unsetMaxLockTimeMinutes()
	 * @see #getMaxLockTimeMinutes()
	 * @see #setMaxLockTimeMinutes(float)
	 * @generated
	 */
	boolean isSetMaxLockTimeMinutes();

	/**
	 * Returns the value of the '<em><b>Min Run Time Minutes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Run Time Minutes</em>' attribute.
	 * @see #isSetMinRunTimeMinutes()
	 * @see #unsetMinRunTimeMinutes()
	 * @see #setMinRunTimeMinutes(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_MinRunTimeMinutes()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='minRunTimeMinutes' namespace='##targetNamespace'"
	 * @generated
	 */
	float getMinRunTimeMinutes();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinRunTimeMinutes <em>Min Run Time Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Run Time Minutes</em>' attribute.
	 * @see #isSetMinRunTimeMinutes()
	 * @see #unsetMinRunTimeMinutes()
	 * @see #getMinRunTimeMinutes()
	 * @generated
	 */
	void setMinRunTimeMinutes(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinRunTimeMinutes <em>Min Run Time Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinRunTimeMinutes()
	 * @see #getMinRunTimeMinutes()
	 * @see #setMinRunTimeMinutes(float)
	 * @generated
	 */
	void unsetMinRunTimeMinutes();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getMinRunTimeMinutes <em>Min Run Time Minutes</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Run Time Minutes</em>' attribute is set.
	 * @see #unsetMinRunTimeMinutes()
	 * @see #getMinRunTimeMinutes()
	 * @see #setMinRunTimeMinutes(float)
	 * @generated
	 */
	boolean isSetMinRunTimeMinutes();

	/**
	 * Returns the value of the '<em><b>Value By Time Table Minutes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value By Time Table Minutes</em>' attribute.
	 * @see #isSetValueByTimeTableMinutes()
	 * @see #unsetValueByTimeTableMinutes()
	 * @see #setValueByTimeTableMinutes(float)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_ValueByTimeTableMinutes()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Float"
	 *        extendedMetaData="kind='element' name='valueByTimeTableMinutes' namespace='##targetNamespace'"
	 * @generated
	 */
	float getValueByTimeTableMinutes();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueByTimeTableMinutes <em>Value By Time Table Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value By Time Table Minutes</em>' attribute.
	 * @see #isSetValueByTimeTableMinutes()
	 * @see #unsetValueByTimeTableMinutes()
	 * @see #getValueByTimeTableMinutes()
	 * @generated
	 */
	void setValueByTimeTableMinutes(float value);

	/**
	 * Unsets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueByTimeTableMinutes <em>Value By Time Table Minutes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValueByTimeTableMinutes()
	 * @see #getValueByTimeTableMinutes()
	 * @see #setValueByTimeTableMinutes(float)
	 * @generated
	 */
	void unsetValueByTimeTableMinutes();

	/**
	 * Returns whether the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getValueByTimeTableMinutes <em>Value By Time Table Minutes</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value By Time Table Minutes</em>' attribute is set.
	 * @see #unsetValueByTimeTableMinutes()
	 * @see #getValueByTimeTableMinutes()
	 * @see #setValueByTimeTableMinutes(float)
	 * @generated
	 */
	boolean isSetValueByTimeTableMinutes();

	/**
	 * Returns the value of the '<em><b>Flex Assistance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Systems with more than One communicator need a definition of the priority of the commands / demands for a flexibility
	 *           requirement.
	 *           This element defines the kind of a such a command (servicable for net (DSO), energy or system (TNO)) and its priority (SHALL / SHOULD / MAY)
	 * 		 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Flex Assistance</em>' containment reference.
	 * @see #setFlexAssistance(SGrFlexAssistanceType)
	 * @see ch.smartgridready.sgr.ns.v0.V0Package#getSGrAttr4GenericType_FlexAssistance()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='flexAssistance' namespace='##targetNamespace'"
	 * @generated
	 */
	SGrFlexAssistanceType getFlexAssistance();

	/**
	 * Sets the value of the '{@link ch.smartgridready.sgr.ns.v0.SGrAttr4GenericType#getFlexAssistance <em>Flex Assistance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flex Assistance</em>' containment reference.
	 * @see #getFlexAssistance()
	 * @generated
	 */
	void setFlexAssistance(SGrFlexAssistanceType value);

} // SGrAttr4GenericType