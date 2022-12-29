package com.truemega.solfa.model.view;

import java.math.BigInteger;

import java.sql.Timestamp;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 16 18:01:59 EET 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SolfaEmployeesTrackViewRowImpl extends ViewRowImpl {
    public static final int ENTITY_SOLFAEMPLOYEESTRACK = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Track_ID,
        StaffId,
        SolfaAmount,
        HasSolfa,
        CreatedDate;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int TRACK_ID = AttributesEnum.Track_ID.index();
    public static final int STAFFID = AttributesEnum.StaffId.index();
    public static final int SOLFAAMOUNT = AttributesEnum.SolfaAmount.index();
    public static final int HASSOLFA = AttributesEnum.HasSolfa.index();
    public static final int CREATEDDATE = AttributesEnum.CreatedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SolfaEmployeesTrackViewRowImpl() {
    }

    /**
     * Gets SolfaEmployeesTrack entity object.
     * @return the SolfaEmployeesTrack
     */
    public EntityImpl getSolfaEmployeesTrack() {
        return (EntityImpl) getEntity(ENTITY_SOLFAEMPLOYEESTRACK);
    }

    /**
     * Gets the attribute value for TRACKID using the alias name Track_ID.
     * @return the TRACKID
     */
    public DBSequence getTrack_ID() {
        return (DBSequence) getAttributeInternal(TRACK_ID);
    }

    /**
     * Sets <code>value</code> as attribute value for TRACKID using the alias name Track_ID.
     * @param value value to set the TRACKID
     */
    public void setTrack_ID(DBSequence value) {
        setAttributeInternal(TRACK_ID, value);
    }

    /**
     * Gets the attribute value for STAFF_ID using the alias name StaffId.
     * @return the STAFF_ID
     */
    public String getStaffId() {
        return (String) getAttributeInternal(STAFFID);
    }

    /**
     * Sets <code>value</code> as attribute value for STAFF_ID using the alias name StaffId.
     * @param value value to set the STAFF_ID
     */
    public void setStaffId(String value) {
        setAttributeInternal(STAFFID, value);
    }

    /**
     * Gets the attribute value for SOLFA_AMOUNT using the alias name SolfaAmount.
     * @return the SOLFA_AMOUNT
     */
    public String getSolfaAmount() {
        return (String) getAttributeInternal(SOLFAAMOUNT);
    }

    /**
     * Sets <code>value</code> as attribute value for SOLFA_AMOUNT using the alias name SolfaAmount.
     * @param value value to set the SOLFA_AMOUNT
     */
    public void setSolfaAmount(String value) {
        setAttributeInternal(SOLFAAMOUNT, value);
    }

    /**
     * Gets the attribute value for HAS_SOLFA using the alias name HasSolfa.
     * @return the HAS_SOLFA
     */
    public String getHasSolfa() {
        return (String) getAttributeInternal(HASSOLFA);
    }

    /**
     * Sets <code>value</code> as attribute value for HAS_SOLFA using the alias name HasSolfa.
     * @param value value to set the HAS_SOLFA
     */
    public void setHasSolfa(String value) {
        setAttributeInternal(HASSOLFA, value);
    }

    /**
     * Gets the attribute value for CREATED_DATE using the alias name CreatedDate.
     * @return the CREATED_DATE
     */
    public Date getCreatedDate() {
        return (Date) getAttributeInternal(CREATEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_DATE using the alias name CreatedDate.
     * @param value value to set the CREATED_DATE
     */
    public void setCreatedDate(Date value) {
        setAttributeInternal(CREATEDDATE, value);
    }
}

