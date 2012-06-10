package org.cmayes.hartree.model;


/**
 * Defines a value object for a calculation result.
 * 
 * @author cmayes
 */
public interface CalculationResult extends BaseResult {

    /**
     * Returns electronic energy.
     * 
     * @return electronic energy.
     */
    Double getElecEn();

    /**
     * Sets electronic energy.
     * 
     * @param energy
     *            The energy to set.
     */
    void setElecEn(final Double energy);

    /**
     * Returns the number of atoms in the calculation.
     * 
     * @return The number of atoms in the calculation.
     */
    Integer getAtomCount();

    /**
     * Sets the number of atoms in the calculation.
     * 
     * @param count
     *            the number of atoms to set.
     */
    void setAtomCount(final Integer count);
}