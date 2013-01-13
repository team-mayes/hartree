package org.cmayes.hartree.model.def;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.NormalModeSummary;

import com.google.common.collect.Maps;

/**
 * Summary of a normal mode frequency.
 * 
 * @author cmayes
 */
public class DefaultNormalModeSummary implements NormalModeSummary {
    private Double angleBendingWeight = 0.0;
    private Double bondStretchingWeight = 0.0;
    private Map<DihedralPair, Double> dihedralPairWeights = Maps.newTreeMap();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#getAngleBendingWeight()
     */
    @Override
    public Double getAngleBendingWeight() {
        return angleBendingWeight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#setAngleBendingWeight(java.lang.Double)
     */
    @Override
    public void setAngleBendingWeight(final Double weight) {
        this.angleBendingWeight = weight;
    }

    /**
     * Adds the value to the total weight.
     * 
     * @param weight
     *            The weight to add.
     */
    public void addToAngleBendingWeight(final double weight) {
        this.angleBendingWeight += weight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#getBondStretchingWeight()
     */
    @Override
    public Double getBondStretchingWeight() {
        return bondStretchingWeight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#setBondStretchingWeight(java.lang.Double)
     */
    @Override
    public void setBondStretchingWeight(final Double weight) {
        this.bondStretchingWeight = weight;
    }

    /**
     * Adds the value to the total weight.
     * 
     * @param weight
     *            The weight to add.
     */
    public void addToBondStretchingWeight(final double weight) {
        this.bondStretchingWeight += weight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#getDihedralPairWeights()
     */
    @Override
    public Map<DihedralPair, Double> getDihedralPairWeights() {
        return dihedralPairWeights;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#setDihedralPairWeights(java.util.Map)
     */
    @Override
    public void setDihedralPairWeights(final Map<DihedralPair, Double> weights) {
        this.dihedralPairWeights = weights;
    }

    /**
     * Adds the value to the total weight for the pair.
     * 
     * @param weight
     *            The weight to add.
     * @param pair
     *            The dihedral pair to add to.
     */
    public void addToDihedralPairWeights(final DihedralPair pair,
            final double weight) {
        final Double curVal = this.dihedralPairWeights.get(pair);
        final double initVal = curVal == null ? 0 : curVal;
        this.dihedralPairWeights.put(pair, initVal + weight);
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("bondStretchingWeight", this.bondStretchingWeight)
                .append("dihedralPairWeights", this.dihedralPairWeights)
                .append("angleBendingWeight", this.angleBendingWeight)
                .toString();
    }
}
