package org.cmayes.hartree.model.def;

import java.util.Map;
import java.util.TreeMap;

import org.cmayes.hartree.model.DihedralPair;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Summary of a normal mode frequency.
 * 
 * @author cmayes
 */
public class DefaultNormalModeSummary {
    private Double angleBendingWeight;
    private Double bondStretchingWeight;
    private Map<DihedralPair, Double> dihedralPairWeights = new TreeMap<DihedralPair, Double>();

    /**
     * @return the angleBendingWeight
     */
    public Double getAngleBendingWeight() {
        return angleBendingWeight;
    }

    /**
     * @param weight
     *            the angleBendingWeight to set
     */
    public void setAngleBendingWeight(final Double weight) {
        this.angleBendingWeight = weight;
    }

    /**
     * @return the bondStretchingWeight
     */
    public Double getBondStretchingWeight() {
        return bondStretchingWeight;
    }

    /**
     * @param weight
     *            the bondStretchingWeight to set
     */
    public void setBondStretchingWeight(final Double weight) {
        this.bondStretchingWeight = weight;
    }

    /**
     * @return the dihedralPairWeights
     */
    public Map<DihedralPair, Double> getDihedralPairWeights() {
        return dihedralPairWeights;
    }

    /**
     * @param weights
     *            the dihedralPairWeights to set
     */
    public void setDihedralPairWeights(final Map<DihedralPair, Double> weights) {
        this.dihedralPairWeights = weights;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultNormalModeSummary)) {
            return false;
        }
        final DefaultNormalModeSummary rhs = (DefaultNormalModeSummary) object;
        return new EqualsBuilder()
                .append(this.bondStretchingWeight, rhs.bondStretchingWeight)
                .append(this.dihedralPairWeights, rhs.dihedralPairWeights)
                .append(this.angleBendingWeight, rhs.angleBendingWeight)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(262712407, 1406686309)
                .append(this.bondStretchingWeight)
                .append(this.dihedralPairWeights)
                .append(this.angleBendingWeight).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("bondStretchingWeight", this.bondStretchingWeight)
                .append("dihedralPairWeights", this.dihedralPairWeights)
                .append("angleBendingWeight", this.angleBendingWeight)
                .toString();
    }
}
