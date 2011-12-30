package org.cmayes.hartree.model;

import java.util.Map;

/**
 * Summary of the normal mode frequency's weights.
 * 
 * @author cmayes
 */
public interface NormalModeSummary {
    /**
     * @return the angleBendingWeight
     */
    Double getAngleBendingWeight();

    /**
     * @param weight
     *            the angleBendingWeight to set
     */
    void setAngleBendingWeight(final Double weight);

    /**
     * @return the bondStretchingWeight
     */
    Double getBondStretchingWeight();

    /**
     * @param weight
     *            the bondStretchingWeight to set
     */
    void setBondStretchingWeight(final Double weight);

    /**
     * @return the dihedralPairWeights
     */
    Map<DihedralPair, Double> getDihedralPairWeights();

    /**
     * @param weights
     *            the dihedralPairWeights to set
     */
    void setDihedralPairWeights(final Map<DihedralPair, Double> weights);
}