package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.CalculationSnapshot;

/**
 * Default implementation of a value object for a calculation snapshot.
 * 
 * @author cmayes
 */
public class DefaultCalculationSnapshot extends BaseCalculationResult implements
        CalculationSnapshot {
    private Double elecEn;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationSnapshot#getElecEn()
     */
    public Double getElecEn() {
        return elecEn;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationSnapshot#setElecEn(java.lang.Double)
     */
    public void setElecEn(final Double energies) {
        this.elecEn = energies;
    }
}
