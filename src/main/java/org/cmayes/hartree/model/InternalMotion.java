package org.cmayes.hartree.model;

import java.util.List;

import com.cmayes.common.chem.InternalMotionType;

/**
 * Describes an internal motion of a normal vibrational mode.
 * 
 * @author cmayes
 * 
 */
public interface InternalMotion {

    /**
     * @return the type
     */
    InternalMotionType getType();

    /**
     * @param motionType
     *            the type to set
     */
    void setType(final InternalMotionType motionType);

    /**
     * @return the name
     */
    String getName();

    /**
     * @param theName
     *            the name to set
     */
    void setName(final String theName);

    /**
     * @return the members
     */
    List<Integer> getMembers();

    /**
     * @param mems
     *            the members to set
     */
    void setMembers(final List<Integer> mems);

    /**
     * @return the value
     */
    Double getValue();

    /**
     * @param val
     *            the value to set
     */
    void setValue(final Double val);

    /**
     * @return the weight
     */
    Double getWeight();

    /**
     * @param wt
     *            the weight to set
     */
    void setWeight(final Double wt);

}