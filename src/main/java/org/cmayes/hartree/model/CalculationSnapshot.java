package org.cmayes.hartree.model;


/**
 * Defines a value object for a calculation summary.
 * 
 * @author cmayes
 */
public interface CalculationSnapshot extends BaseResult {
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

}