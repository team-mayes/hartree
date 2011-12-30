package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.CalculationResult;

/**
 * Default implementation of a value object for a calculation result.
 * 
 * @author cmayes
 */
public class DefaultCalculationResult extends BaseCalculationResult implements
        CalculationResult {
    private Double elecEn;
    private Integer atomCount;
    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getElecEn()
     */
    public Double getElecEn() {
        return elecEn;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setElecEn(java.lang.Double)
     */
    public void setElecEn(final Double energies) {
        this.elecEn = energies;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getAtomCount()
     */
    public Integer getAtomCount() {
        return atomCount;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setAtomCount(java.lang.Integer
     *      )
     */
    public void setAtomCount(final Integer count) {
        this.atomCount = count;
    }

}
