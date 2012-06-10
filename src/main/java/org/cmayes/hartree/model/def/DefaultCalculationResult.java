package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.CalculationResult;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultCalculationResult)) {
            return false;
        }
        final DefaultCalculationResult rhs = (DefaultCalculationResult) object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.atomCount, rhs.atomCount)
                .append(this.elecEn, rhs.elecEn).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1166389701, -879447091)
                .appendSuper(super.hashCode()).append(this.atomCount)
                .append(this.elecEn).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("symmetricTop", this.isSymmetricTop())
                .append("transPart", this.getTransPart())
                .append("elecEn", this.elecEn).append("atoms", this.getAtoms())
                .append("frequencyValues", this.getFrequencyValues())
                .append("cpuTimes", this.getCpuTimes())
                .append("mult", this.getMult())
                .append("terminationDates", this.getTerminationDates())
                .append("rotPart", this.getRotPart())
                .append("atomCount", this.atomCount).toString();
    }

}
