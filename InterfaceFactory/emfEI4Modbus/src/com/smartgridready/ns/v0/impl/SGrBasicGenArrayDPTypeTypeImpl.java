/**
 */
package com.smartgridready.ns.v0.impl;

import com.smartgridready.ns.v0.SGrBasicGenArrayDPTypeType;
import com.smartgridready.ns.v0.V0Package;

import java.math.BigInteger;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SGr Basic Gen Array DP Type Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt8 <em>Int8</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt16 <em>Int16</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt32 <em>Int32</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt64 <em>Int64</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt8U <em>Int8 U</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt16U <em>Int16 U</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt32U <em>Int32 U</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getInt64U <em>Int64 U</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getFloat32 <em>Float32</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getFloat64 <em>Float64</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getDateTime <em>Date Time</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrBasicGenArrayDPTypeTypeImpl#getString <em>String</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SGrBasicGenArrayDPTypeTypeImpl extends MinimalEObjectImpl.Container implements SGrBasicGenArrayDPTypeType {
	/**
	 * The cached value of the '{@link #getBoolean() <em>Boolean</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoolean()
	 * @generated
	 * @ordered
	 */
	protected EList<Boolean> boolean_;

	/**
	 * The cached value of the '{@link #getInt8() <em>Int8</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt8()
	 * @generated
	 * @ordered
	 */
	protected EList<Byte> int8;

	/**
	 * The cached value of the '{@link #getInt16() <em>Int16</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt16()
	 * @generated
	 * @ordered
	 */
	protected EList<Short> int16;

	/**
	 * The cached value of the '{@link #getInt32() <em>Int32</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt32()
	 * @generated
	 * @ordered
	 */
	protected EList<BigInteger> int32;

	/**
	 * The cached value of the '{@link #getInt64() <em>Int64</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt64()
	 * @generated
	 * @ordered
	 */
	protected EList<Long> int64;

	/**
	 * The cached value of the '{@link #getInt8U() <em>Int8 U</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt8U()
	 * @generated
	 * @ordered
	 */
	protected EList<Short> int8U;

	/**
	 * The cached value of the '{@link #getInt16U() <em>Int16 U</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt16U()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> int16U;

	/**
	 * The cached value of the '{@link #getInt32U() <em>Int32 U</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt32U()
	 * @generated
	 * @ordered
	 */
	protected EList<Long> int32U;

	/**
	 * The cached value of the '{@link #getInt64U() <em>Int64 U</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInt64U()
	 * @generated
	 * @ordered
	 */
	protected EList<BigInteger> int64U;

	/**
	 * The cached value of the '{@link #getFloat32() <em>Float32</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloat32()
	 * @generated
	 * @ordered
	 */
	protected EList<Float> float32;

	/**
	 * The cached value of the '{@link #getFloat64() <em>Float64</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloat64()
	 * @generated
	 * @ordered
	 */
	protected EList<Double> float64;

	/**
	 * The cached value of the '{@link #getDateTime() <em>Date Time</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateTime()
	 * @generated
	 * @ordered
	 */
	protected EList<XMLGregorianCalendar> dateTime;

	/**
	 * The cached value of the '{@link #getString() <em>String</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getString()
	 * @generated
	 * @ordered
	 */
	protected EList<String> string;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SGrBasicGenArrayDPTypeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return V0Package.eINSTANCE.getSGrBasicGenArrayDPTypeType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Boolean> getBoolean() {
		if (boolean_ == null) {
			boolean_ = new EDataTypeEList<Boolean>(Boolean.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__BOOLEAN);
		}
		return boolean_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Byte> getInt8() {
		if (int8 == null) {
			int8 = new EDataTypeEList<Byte>(Byte.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8);
		}
		return int8;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Short> getInt16() {
		if (int16 == null) {
			int16 = new EDataTypeEList<Short>(Short.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16);
		}
		return int16;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BigInteger> getInt32() {
		if (int32 == null) {
			int32 = new EDataTypeEList<BigInteger>(BigInteger.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32);
		}
		return int32;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Long> getInt64() {
		if (int64 == null) {
			int64 = new EDataTypeEList<Long>(Long.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64);
		}
		return int64;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Short> getInt8U() {
		if (int8U == null) {
			int8U = new EDataTypeEList<Short>(Short.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8_U);
		}
		return int8U;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getInt16U() {
		if (int16U == null) {
			int16U = new EDataTypeEList<Integer>(Integer.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16_U);
		}
		return int16U;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Long> getInt32U() {
		if (int32U == null) {
			int32U = new EDataTypeEList<Long>(Long.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32_U);
		}
		return int32U;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<BigInteger> getInt64U() {
		if (int64U == null) {
			int64U = new EDataTypeEList<BigInteger>(BigInteger.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64_U);
		}
		return int64U;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Float> getFloat32() {
		if (float32 == null) {
			float32 = new EDataTypeEList<Float>(Float.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT32);
		}
		return float32;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Double> getFloat64() {
		if (float64 == null) {
			float64 = new EDataTypeEList<Double>(Double.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT64);
		}
		return float64;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<XMLGregorianCalendar> getDateTime() {
		if (dateTime == null) {
			dateTime = new EDataTypeEList<XMLGregorianCalendar>(XMLGregorianCalendar.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__DATE_TIME);
		}
		return dateTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getString() {
		if (string == null) {
			string = new EDataTypeEList<String>(String.class, this, V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__STRING);
		}
		return string;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__BOOLEAN:
				return getBoolean();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8:
				return getInt8();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16:
				return getInt16();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32:
				return getInt32();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64:
				return getInt64();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8_U:
				return getInt8U();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16_U:
				return getInt16U();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32_U:
				return getInt32U();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64_U:
				return getInt64U();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT32:
				return getFloat32();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT64:
				return getFloat64();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__DATE_TIME:
				return getDateTime();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__STRING:
				return getString();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__BOOLEAN:
				getBoolean().clear();
				getBoolean().addAll((Collection<? extends Boolean>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8:
				getInt8().clear();
				getInt8().addAll((Collection<? extends Byte>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16:
				getInt16().clear();
				getInt16().addAll((Collection<? extends Short>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32:
				getInt32().clear();
				getInt32().addAll((Collection<? extends BigInteger>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64:
				getInt64().clear();
				getInt64().addAll((Collection<? extends Long>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8_U:
				getInt8U().clear();
				getInt8U().addAll((Collection<? extends Short>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16_U:
				getInt16U().clear();
				getInt16U().addAll((Collection<? extends Integer>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32_U:
				getInt32U().clear();
				getInt32U().addAll((Collection<? extends Long>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64_U:
				getInt64U().clear();
				getInt64U().addAll((Collection<? extends BigInteger>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT32:
				getFloat32().clear();
				getFloat32().addAll((Collection<? extends Float>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT64:
				getFloat64().clear();
				getFloat64().addAll((Collection<? extends Double>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__DATE_TIME:
				getDateTime().clear();
				getDateTime().addAll((Collection<? extends XMLGregorianCalendar>)newValue);
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__STRING:
				getString().clear();
				getString().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__BOOLEAN:
				getBoolean().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8:
				getInt8().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16:
				getInt16().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32:
				getInt32().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64:
				getInt64().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8_U:
				getInt8U().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16_U:
				getInt16U().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32_U:
				getInt32U().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64_U:
				getInt64U().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT32:
				getFloat32().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT64:
				getFloat64().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__DATE_TIME:
				getDateTime().clear();
				return;
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__STRING:
				getString().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__BOOLEAN:
				return boolean_ != null && !boolean_.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8:
				return int8 != null && !int8.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16:
				return int16 != null && !int16.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32:
				return int32 != null && !int32.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64:
				return int64 != null && !int64.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT8_U:
				return int8U != null && !int8U.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT16_U:
				return int16U != null && !int16U.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT32_U:
				return int32U != null && !int32U.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__INT64_U:
				return int64U != null && !int64U.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT32:
				return float32 != null && !float32.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__FLOAT64:
				return float64 != null && !float64.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__DATE_TIME:
				return dateTime != null && !dateTime.isEmpty();
			case V0Package.SGR_BASIC_GEN_ARRAY_DP_TYPE_TYPE__STRING:
				return string != null && !string.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (boolean: ");
		result.append(boolean_);
		result.append(", int8: ");
		result.append(int8);
		result.append(", int16: ");
		result.append(int16);
		result.append(", int32: ");
		result.append(int32);
		result.append(", int64: ");
		result.append(int64);
		result.append(", int8U: ");
		result.append(int8U);
		result.append(", int16U: ");
		result.append(int16U);
		result.append(", int32U: ");
		result.append(int32U);
		result.append(", int64U: ");
		result.append(int64U);
		result.append(", float32: ");
		result.append(float32);
		result.append(", float64: ");
		result.append(float64);
		result.append(", dateTime: ");
		result.append(dateTime);
		result.append(", string: ");
		result.append(string);
		result.append(')');
		return result.toString();
	}

} //SGrBasicGenArrayDPTypeTypeImpl