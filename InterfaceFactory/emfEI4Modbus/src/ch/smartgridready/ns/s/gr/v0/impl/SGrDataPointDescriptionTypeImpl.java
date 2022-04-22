/**
 */
package ch.smartgridready.ns.s.gr.v0.impl;

import ch.smartgridready.ns.s.gr.v0.SGrBasicGenDataPointTypeType;
import ch.smartgridready.ns.s.gr.v0.SGrDataPointDescriptionType;
import ch.smartgridready.ns.s.gr.v0.SGrLegibDocumentationType;
import ch.smartgridready.ns.s.gr.v0.SGrMROPresenceLevelIndicationType;
import ch.smartgridready.ns.s.gr.v0.SGrNamelistType;
import ch.smartgridready.ns.s.gr.v0.SGrRWPType;
import ch.smartgridready.ns.s.gr.v0.SGrUnits;
import ch.smartgridready.ns.s.gr.v0.V0Package;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SGr Data Point Description Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getBasicDataType <em>Basic Data Type</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getDpNameList <em>Dp Name List</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getDpLegibDesc <em>Dp Legib Desc</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getDatapointName <em>Datapoint Name</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getMroVisibilityIndicator <em>Mro Visibility Indicator</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getRwpDatadirection <em>Rwp Datadirection</em>}</li>
 *   <li>{@link ch.smartgridready.ns.s.gr.v0.impl.SGrDataPointDescriptionTypeImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SGrDataPointDescriptionTypeImpl extends MinimalEObjectImpl.Container implements SGrDataPointDescriptionType {
	/**
	 * The cached value of the '{@link #getBasicDataType() <em>Basic Data Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicDataType()
	 * @generated
	 * @ordered
	 */
	protected SGrBasicGenDataPointTypeType basicDataType;

	/**
	 * The cached value of the '{@link #getDpNameList() <em>Dp Name List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDpNameList()
	 * @generated
	 * @ordered
	 */
	protected SGrNamelistType dpNameList;

	/**
	 * The cached value of the '{@link #getDpLegibDesc() <em>Dp Legib Desc</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDpLegibDesc()
	 * @generated
	 * @ordered
	 */
	protected EList<SGrLegibDocumentationType> dpLegibDesc;

	/**
	 * The default value of the '{@link #getDatapointName() <em>Datapoint Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDatapointName()
	 * @generated
	 * @ordered
	 */
	protected static final String DATAPOINT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDatapointName() <em>Datapoint Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDatapointName()
	 * @generated
	 * @ordered
	 */
	protected String datapointName = DATAPOINT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMroVisibilityIndicator() <em>Mro Visibility Indicator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMroVisibilityIndicator()
	 * @generated
	 * @ordered
	 */
	protected static final SGrMROPresenceLevelIndicationType MRO_VISIBILITY_INDICATOR_EDEFAULT = SGrMROPresenceLevelIndicationType.M;

	/**
	 * The cached value of the '{@link #getMroVisibilityIndicator() <em>Mro Visibility Indicator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMroVisibilityIndicator()
	 * @generated
	 * @ordered
	 */
	protected SGrMROPresenceLevelIndicationType mroVisibilityIndicator = MRO_VISIBILITY_INDICATOR_EDEFAULT;

	/**
	 * This is true if the Mro Visibility Indicator attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean mroVisibilityIndicatorESet;

	/**
	 * The default value of the '{@link #getRwpDatadirection() <em>Rwp Datadirection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRwpDatadirection()
	 * @generated
	 * @ordered
	 */
	protected static final SGrRWPType RWP_DATADIRECTION_EDEFAULT = SGrRWPType.R;

	/**
	 * The cached value of the '{@link #getRwpDatadirection() <em>Rwp Datadirection</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRwpDatadirection()
	 * @generated
	 * @ordered
	 */
	protected SGrRWPType rwpDatadirection = RWP_DATADIRECTION_EDEFAULT;

	/**
	 * This is true if the Rwp Datadirection attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rwpDatadirectionESet;

	/**
	 * The default value of the '{@link #getUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnit()
	 * @generated
	 * @ordered
	 */
	protected static final SGrUnits UNIT_EDEFAULT = SGrUnits.AMPERESQUAREMETERS;

	/**
	 * The cached value of the '{@link #getUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnit()
	 * @generated
	 * @ordered
	 */
	protected SGrUnits unit = UNIT_EDEFAULT;

	/**
	 * This is true if the Unit attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean unitESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SGrDataPointDescriptionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return V0Package.eINSTANCE.getSGrDataPointDescriptionType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrBasicGenDataPointTypeType getBasicDataType() {
		return basicDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBasicDataType(SGrBasicGenDataPointTypeType newBasicDataType, NotificationChain msgs) {
		SGrBasicGenDataPointTypeType oldBasicDataType = basicDataType;
		basicDataType = newBasicDataType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE, oldBasicDataType, newBasicDataType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBasicDataType(SGrBasicGenDataPointTypeType newBasicDataType) {
		if (newBasicDataType != basicDataType) {
			NotificationChain msgs = null;
			if (basicDataType != null)
				msgs = ((InternalEObject)basicDataType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE, null, msgs);
			if (newBasicDataType != null)
				msgs = ((InternalEObject)newBasicDataType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE, null, msgs);
			msgs = basicSetBasicDataType(newBasicDataType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE, newBasicDataType, newBasicDataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrNamelistType getDpNameList() {
		return dpNameList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDpNameList(SGrNamelistType newDpNameList, NotificationChain msgs) {
		SGrNamelistType oldDpNameList = dpNameList;
		dpNameList = newDpNameList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST, oldDpNameList, newDpNameList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDpNameList(SGrNamelistType newDpNameList) {
		if (newDpNameList != dpNameList) {
			NotificationChain msgs = null;
			if (dpNameList != null)
				msgs = ((InternalEObject)dpNameList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST, null, msgs);
			if (newDpNameList != null)
				msgs = ((InternalEObject)newDpNameList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST, null, msgs);
			msgs = basicSetDpNameList(newDpNameList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST, newDpNameList, newDpNameList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SGrLegibDocumentationType> getDpLegibDesc() {
		if (dpLegibDesc == null) {
			dpLegibDesc = new EObjectContainmentEList<SGrLegibDocumentationType>(SGrLegibDocumentationType.class, this, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC);
		}
		return dpLegibDesc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDatapointName() {
		return datapointName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDatapointName(String newDatapointName) {
		String oldDatapointName = datapointName;
		datapointName = newDatapointName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DATAPOINT_NAME, oldDatapointName, datapointName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrMROPresenceLevelIndicationType getMroVisibilityIndicator() {
		return mroVisibilityIndicator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMroVisibilityIndicator(SGrMROPresenceLevelIndicationType newMroVisibilityIndicator) {
		SGrMROPresenceLevelIndicationType oldMroVisibilityIndicator = mroVisibilityIndicator;
		mroVisibilityIndicator = newMroVisibilityIndicator == null ? MRO_VISIBILITY_INDICATOR_EDEFAULT : newMroVisibilityIndicator;
		boolean oldMroVisibilityIndicatorESet = mroVisibilityIndicatorESet;
		mroVisibilityIndicatorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR, oldMroVisibilityIndicator, mroVisibilityIndicator, !oldMroVisibilityIndicatorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMroVisibilityIndicator() {
		SGrMROPresenceLevelIndicationType oldMroVisibilityIndicator = mroVisibilityIndicator;
		boolean oldMroVisibilityIndicatorESet = mroVisibilityIndicatorESet;
		mroVisibilityIndicator = MRO_VISIBILITY_INDICATOR_EDEFAULT;
		mroVisibilityIndicatorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR, oldMroVisibilityIndicator, MRO_VISIBILITY_INDICATOR_EDEFAULT, oldMroVisibilityIndicatorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMroVisibilityIndicator() {
		return mroVisibilityIndicatorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrRWPType getRwpDatadirection() {
		return rwpDatadirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRwpDatadirection(SGrRWPType newRwpDatadirection) {
		SGrRWPType oldRwpDatadirection = rwpDatadirection;
		rwpDatadirection = newRwpDatadirection == null ? RWP_DATADIRECTION_EDEFAULT : newRwpDatadirection;
		boolean oldRwpDatadirectionESet = rwpDatadirectionESet;
		rwpDatadirectionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION, oldRwpDatadirection, rwpDatadirection, !oldRwpDatadirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetRwpDatadirection() {
		SGrRWPType oldRwpDatadirection = rwpDatadirection;
		boolean oldRwpDatadirectionESet = rwpDatadirectionESet;
		rwpDatadirection = RWP_DATADIRECTION_EDEFAULT;
		rwpDatadirectionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION, oldRwpDatadirection, RWP_DATADIRECTION_EDEFAULT, oldRwpDatadirectionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetRwpDatadirection() {
		return rwpDatadirectionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGrUnits getUnit() {
		return unit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnit(SGrUnits newUnit) {
		SGrUnits oldUnit = unit;
		unit = newUnit == null ? UNIT_EDEFAULT : newUnit;
		boolean oldUnitESet = unitESet;
		unitESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT, oldUnit, unit, !oldUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUnit() {
		SGrUnits oldUnit = unit;
		boolean oldUnitESet = unitESet;
		unit = UNIT_EDEFAULT;
		unitESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT, oldUnit, UNIT_EDEFAULT, oldUnitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUnit() {
		return unitESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE:
				return basicSetBasicDataType(null, msgs);
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST:
				return basicSetDpNameList(null, msgs);
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC:
				return ((InternalEList<?>)getDpLegibDesc()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE:
				return getBasicDataType();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST:
				return getDpNameList();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC:
				return getDpLegibDesc();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DATAPOINT_NAME:
				return getDatapointName();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR:
				return getMroVisibilityIndicator();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION:
				return getRwpDatadirection();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT:
				return getUnit();
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
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE:
				setBasicDataType((SGrBasicGenDataPointTypeType)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST:
				setDpNameList((SGrNamelistType)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC:
				getDpLegibDesc().clear();
				getDpLegibDesc().addAll((Collection<? extends SGrLegibDocumentationType>)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DATAPOINT_NAME:
				setDatapointName((String)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR:
				setMroVisibilityIndicator((SGrMROPresenceLevelIndicationType)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION:
				setRwpDatadirection((SGrRWPType)newValue);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT:
				setUnit((SGrUnits)newValue);
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
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE:
				setBasicDataType((SGrBasicGenDataPointTypeType)null);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST:
				setDpNameList((SGrNamelistType)null);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC:
				getDpLegibDesc().clear();
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DATAPOINT_NAME:
				setDatapointName(DATAPOINT_NAME_EDEFAULT);
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR:
				unsetMroVisibilityIndicator();
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION:
				unsetRwpDatadirection();
				return;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT:
				unsetUnit();
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
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__BASIC_DATA_TYPE:
				return basicDataType != null;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_NAME_LIST:
				return dpNameList != null;
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DP_LEGIB_DESC:
				return dpLegibDesc != null && !dpLegibDesc.isEmpty();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__DATAPOINT_NAME:
				return DATAPOINT_NAME_EDEFAULT == null ? datapointName != null : !DATAPOINT_NAME_EDEFAULT.equals(datapointName);
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__MRO_VISIBILITY_INDICATOR:
				return isSetMroVisibilityIndicator();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__RWP_DATADIRECTION:
				return isSetRwpDatadirection();
			case V0Package.SGR_DATA_POINT_DESCRIPTION_TYPE__UNIT:
				return isSetUnit();
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
		result.append(" (datapointName: ");
		result.append(datapointName);
		result.append(", mroVisibilityIndicator: ");
		if (mroVisibilityIndicatorESet) result.append(mroVisibilityIndicator); else result.append("<unset>");
		result.append(", rwpDatadirection: ");
		if (rwpDatadirectionESet) result.append(rwpDatadirection); else result.append("<unset>");
		result.append(", unit: ");
		if (unitESet) result.append(unit); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SGrDataPointDescriptionTypeImpl
