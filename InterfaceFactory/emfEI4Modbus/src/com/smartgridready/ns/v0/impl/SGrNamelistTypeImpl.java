/**
 */
package com.smartgridready.ns.v0.impl;

import com.smartgridready.ns.v0.SGrNamelistType;
import com.smartgridready.ns.v0.V0Package;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SGr Namelist Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSLV1Name <em>SLV1 Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSWorkName <em>SWork Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSManufName <em>SManuf Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSIEC61850Name <em>SIEC61850 Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSSAREFName <em>SSAREF Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSEEBUSName <em>SEEBUS Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSSUNSPECName <em>SSUNSPEC Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSHPbwpName <em>SH Pbwp Name</em>}</li>
 *   <li>{@link com.smartgridready.ns.v0.impl.SGrNamelistTypeImpl#getSEN17609Name <em>SEN17609 Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SGrNamelistTypeImpl extends MinimalEObjectImpl.Container implements SGrNamelistType {
	/**
	 * The default value of the '{@link #getSLV1Name() <em>SLV1 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSLV1Name()
	 * @generated
	 * @ordered
	 */
	protected static final String SLV1_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSLV1Name() <em>SLV1 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSLV1Name()
	 * @generated
	 * @ordered
	 */
	protected String sLV1Name = SLV1_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSWorkName() <em>SWork Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSWorkName()
	 * @generated
	 * @ordered
	 */
	protected static final String SWORK_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSWorkName() <em>SWork Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSWorkName()
	 * @generated
	 * @ordered
	 */
	protected String sWorkName = SWORK_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSManufName() <em>SManuf Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSManufName()
	 * @generated
	 * @ordered
	 */
	protected static final String SMANUF_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSManufName() <em>SManuf Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSManufName()
	 * @generated
	 * @ordered
	 */
	protected String sManufName = SMANUF_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSIEC61850Name() <em>SIEC61850 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSIEC61850Name()
	 * @generated
	 * @ordered
	 */
	protected static final String SIEC61850_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSIEC61850Name() <em>SIEC61850 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSIEC61850Name()
	 * @generated
	 * @ordered
	 */
	protected String sIEC61850Name = SIEC61850_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSSAREFName() <em>SSAREF Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSSAREFName()
	 * @generated
	 * @ordered
	 */
	protected static final String SSAREF_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSSAREFName() <em>SSAREF Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSSAREFName()
	 * @generated
	 * @ordered
	 */
	protected String sSAREFName = SSAREF_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSEEBUSName() <em>SEEBUS Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSEEBUSName()
	 * @generated
	 * @ordered
	 */
	protected static final String SEEBUS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSEEBUSName() <em>SEEBUS Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSEEBUSName()
	 * @generated
	 * @ordered
	 */
	protected String sEEBUSName = SEEBUS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSSUNSPECName() <em>SSUNSPEC Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSSUNSPECName()
	 * @generated
	 * @ordered
	 */
	protected static final String SSUNSPEC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSSUNSPECName() <em>SSUNSPEC Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSSUNSPECName()
	 * @generated
	 * @ordered
	 */
	protected String sSUNSPECName = SSUNSPEC_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSHPbwpName() <em>SH Pbwp Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSHPbwpName()
	 * @generated
	 * @ordered
	 */
	protected static final String SH_PBWP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSHPbwpName() <em>SH Pbwp Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSHPbwpName()
	 * @generated
	 * @ordered
	 */
	protected String sHPbwpName = SH_PBWP_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSEN17609Name() <em>SEN17609 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSEN17609Name()
	 * @generated
	 * @ordered
	 */
	protected static final String SEN17609_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSEN17609Name() <em>SEN17609 Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSEN17609Name()
	 * @generated
	 * @ordered
	 */
	protected String sEN17609Name = SEN17609_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SGrNamelistTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return V0Package.eINSTANCE.getSGrNamelistType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSLV1Name() {
		return sLV1Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSLV1Name(String newSLV1Name) {
		String oldSLV1Name = sLV1Name;
		sLV1Name = newSLV1Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SLV1_NAME, oldSLV1Name, sLV1Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSWorkName() {
		return sWorkName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSWorkName(String newSWorkName) {
		String oldSWorkName = sWorkName;
		sWorkName = newSWorkName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SWORK_NAME, oldSWorkName, sWorkName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSManufName() {
		return sManufName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSManufName(String newSManufName) {
		String oldSManufName = sManufName;
		sManufName = newSManufName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SMANUF_NAME, oldSManufName, sManufName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSIEC61850Name() {
		return sIEC61850Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSIEC61850Name(String newSIEC61850Name) {
		String oldSIEC61850Name = sIEC61850Name;
		sIEC61850Name = newSIEC61850Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SIEC61850_NAME, oldSIEC61850Name, sIEC61850Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSSAREFName() {
		return sSAREFName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSSAREFName(String newSSAREFName) {
		String oldSSAREFName = sSAREFName;
		sSAREFName = newSSAREFName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SSAREF_NAME, oldSSAREFName, sSAREFName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSEEBUSName() {
		return sEEBUSName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSEEBUSName(String newSEEBUSName) {
		String oldSEEBUSName = sEEBUSName;
		sEEBUSName = newSEEBUSName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SEEBUS_NAME, oldSEEBUSName, sEEBUSName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSSUNSPECName() {
		return sSUNSPECName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSSUNSPECName(String newSSUNSPECName) {
		String oldSSUNSPECName = sSUNSPECName;
		sSUNSPECName = newSSUNSPECName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SSUNSPEC_NAME, oldSSUNSPECName, sSUNSPECName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSHPbwpName() {
		return sHPbwpName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSHPbwpName(String newSHPbwpName) {
		String oldSHPbwpName = sHPbwpName;
		sHPbwpName = newSHPbwpName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SH_PBWP_NAME, oldSHPbwpName, sHPbwpName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSEN17609Name() {
		return sEN17609Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSEN17609Name(String newSEN17609Name) {
		String oldSEN17609Name = sEN17609Name;
		sEN17609Name = newSEN17609Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_NAMELIST_TYPE__SEN17609_NAME, oldSEN17609Name, sEN17609Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case V0Package.SGR_NAMELIST_TYPE__SLV1_NAME:
				return getSLV1Name();
			case V0Package.SGR_NAMELIST_TYPE__SWORK_NAME:
				return getSWorkName();
			case V0Package.SGR_NAMELIST_TYPE__SMANUF_NAME:
				return getSManufName();
			case V0Package.SGR_NAMELIST_TYPE__SIEC61850_NAME:
				return getSIEC61850Name();
			case V0Package.SGR_NAMELIST_TYPE__SSAREF_NAME:
				return getSSAREFName();
			case V0Package.SGR_NAMELIST_TYPE__SEEBUS_NAME:
				return getSEEBUSName();
			case V0Package.SGR_NAMELIST_TYPE__SSUNSPEC_NAME:
				return getSSUNSPECName();
			case V0Package.SGR_NAMELIST_TYPE__SH_PBWP_NAME:
				return getSHPbwpName();
			case V0Package.SGR_NAMELIST_TYPE__SEN17609_NAME:
				return getSEN17609Name();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case V0Package.SGR_NAMELIST_TYPE__SLV1_NAME:
				setSLV1Name((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SWORK_NAME:
				setSWorkName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SMANUF_NAME:
				setSManufName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SIEC61850_NAME:
				setSIEC61850Name((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SSAREF_NAME:
				setSSAREFName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SEEBUS_NAME:
				setSEEBUSName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SSUNSPEC_NAME:
				setSSUNSPECName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SH_PBWP_NAME:
				setSHPbwpName((String)newValue);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SEN17609_NAME:
				setSEN17609Name((String)newValue);
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
			case V0Package.SGR_NAMELIST_TYPE__SLV1_NAME:
				setSLV1Name(SLV1_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SWORK_NAME:
				setSWorkName(SWORK_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SMANUF_NAME:
				setSManufName(SMANUF_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SIEC61850_NAME:
				setSIEC61850Name(SIEC61850_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SSAREF_NAME:
				setSSAREFName(SSAREF_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SEEBUS_NAME:
				setSEEBUSName(SEEBUS_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SSUNSPEC_NAME:
				setSSUNSPECName(SSUNSPEC_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SH_PBWP_NAME:
				setSHPbwpName(SH_PBWP_NAME_EDEFAULT);
				return;
			case V0Package.SGR_NAMELIST_TYPE__SEN17609_NAME:
				setSEN17609Name(SEN17609_NAME_EDEFAULT);
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
			case V0Package.SGR_NAMELIST_TYPE__SLV1_NAME:
				return SLV1_NAME_EDEFAULT == null ? sLV1Name != null : !SLV1_NAME_EDEFAULT.equals(sLV1Name);
			case V0Package.SGR_NAMELIST_TYPE__SWORK_NAME:
				return SWORK_NAME_EDEFAULT == null ? sWorkName != null : !SWORK_NAME_EDEFAULT.equals(sWorkName);
			case V0Package.SGR_NAMELIST_TYPE__SMANUF_NAME:
				return SMANUF_NAME_EDEFAULT == null ? sManufName != null : !SMANUF_NAME_EDEFAULT.equals(sManufName);
			case V0Package.SGR_NAMELIST_TYPE__SIEC61850_NAME:
				return SIEC61850_NAME_EDEFAULT == null ? sIEC61850Name != null : !SIEC61850_NAME_EDEFAULT.equals(sIEC61850Name);
			case V0Package.SGR_NAMELIST_TYPE__SSAREF_NAME:
				return SSAREF_NAME_EDEFAULT == null ? sSAREFName != null : !SSAREF_NAME_EDEFAULT.equals(sSAREFName);
			case V0Package.SGR_NAMELIST_TYPE__SEEBUS_NAME:
				return SEEBUS_NAME_EDEFAULT == null ? sEEBUSName != null : !SEEBUS_NAME_EDEFAULT.equals(sEEBUSName);
			case V0Package.SGR_NAMELIST_TYPE__SSUNSPEC_NAME:
				return SSUNSPEC_NAME_EDEFAULT == null ? sSUNSPECName != null : !SSUNSPEC_NAME_EDEFAULT.equals(sSUNSPECName);
			case V0Package.SGR_NAMELIST_TYPE__SH_PBWP_NAME:
				return SH_PBWP_NAME_EDEFAULT == null ? sHPbwpName != null : !SH_PBWP_NAME_EDEFAULT.equals(sHPbwpName);
			case V0Package.SGR_NAMELIST_TYPE__SEN17609_NAME:
				return SEN17609_NAME_EDEFAULT == null ? sEN17609Name != null : !SEN17609_NAME_EDEFAULT.equals(sEN17609Name);
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
		result.append(" (sLV1Name: ");
		result.append(sLV1Name);
		result.append(", sWorkName: ");
		result.append(sWorkName);
		result.append(", sManufName: ");
		result.append(sManufName);
		result.append(", sIEC61850Name: ");
		result.append(sIEC61850Name);
		result.append(", sSAREFName: ");
		result.append(sSAREFName);
		result.append(", sEEBUSName: ");
		result.append(sEEBUSName);
		result.append(", sSUNSPECName: ");
		result.append(sSUNSPECName);
		result.append(", sHPbwpName: ");
		result.append(sHPbwpName);
		result.append(", sEN17609Name: ");
		result.append(sEN17609Name);
		result.append(')');
		return result.toString();
	}

} //SGrNamelistTypeImpl
