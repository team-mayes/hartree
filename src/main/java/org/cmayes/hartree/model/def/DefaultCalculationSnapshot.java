package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.CalculationSnapshot;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultCalculationSnapshot)) {
            return false;
        }
        final DefaultCalculationSnapshot rhs = (DefaultCalculationSnapshot) object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.elecEn, rhs.elecEn).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-963930147, 1140884125)
                .appendSuper(super.hashCode()).append(this.elecEn).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("elecEn", this.elecEn)
                .append("functional", this.getFunctional())
                .append("charge", this.getCharge())
                .append("atoms", this.getAtoms())
                .append("frequencyValues", this.getFrequencyValues())
                .append("cpuTimes", this.getCpuTimes())
                .append("mult", this.getMult())
                .append("rotPart", this.getRotPart())
                .append("transPart", this.getTransPart())
                .append("symmetricTop", this.isSymmetricTop())
                .append("zpeCorrection", this.getZpeCorrection())
                .append("stoichiometry", this.getStoichiometry())
                .append("basisSet", this.getBasisSet())
                .append("fileName", this.getFileName())
                .append("dipoleMomentTotal", this.getDipoleMomentTotal())
                .append("solvent", this.getSolvent())
                .append("terminationDates", this.getTerminationDates())
                .toString();
    }
}
