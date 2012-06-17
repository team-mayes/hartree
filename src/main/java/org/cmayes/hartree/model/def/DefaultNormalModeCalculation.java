package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.InternalMotion;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.NormalModeReport;
import org.cmayes.hartree.model.NormalModeSummary;

import com.google.common.base.Joiner;

/**
 * Default implementation of a normal mode summary.
 * 
 * @author cmayes
 */
public class DefaultNormalModeCalculation extends DefaultBaseResult
        implements NormalModeCalculation {
    private List<NormalMode> normalModes = new ArrayList<NormalMode>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeCalculation#getNormalModes()
     */
    @Override
    public List<NormalMode> getNormalModes() {
        return normalModes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeCalculation#setNormalModes(java.util.List)
     */
    @Override
    public void setNormalModes(final List<NormalMode> norModes) {
        this.normalModes = norModes;
    }

    /**
     * Calculates and returns a map of summaries keyed to their normal modes.
     * 
     * @return A map of summaries keyed to their normal modes.
     * @see org.cmayes.hartree.model.NormalModeCalculation#generateReport()
     */
    @Override
    public NormalModeReport generateReport() {
        final Map<NormalMode, NormalModeSummary> summaries = new LinkedHashMap<NormalMode, NormalModeSummary>();
        for (NormalMode curMode : normalModes) {
            summaries.put(curMode, calculateSummary(curMode));
        }
        return new DefaultNormalModeReport(summaries);
    }

    /**
     * Produces summations of the weights of the constituent parts of the given
     * normal mode.
     * 
     * @param curMode
     *            The mode to evaluate.
     * @return A summary of the weights of each type of internal motion.
     */
    NormalModeSummary calculateSummary(final NormalMode curMode) {
        final DefaultNormalModeSummary sum = new DefaultNormalModeSummary();
        for (InternalMotion curMot : curMode.getMotions()) {
            switch (curMot.getType()) {
            case ANGLE_BENDING:
                sum.addToAngleBendingWeight(curMot.getWeight());
                break;
            case BOND_STRETCHING:
                sum.addToBondStretchingWeight(curMot.getWeight());
                break;
            case DIHEDRAL_ROTATION:
                sum.addToDihedralPairWeights(genPair(curMot),
                        curMot.getWeight());
                break;
            default:
                break;
            }
        }
        return sum;
    }

    /**
     * Returns a {@link DihedralPair} for the second and third members of the
     * given {@link InternalMotion} instance.
     * 
     * @param curMot
     *            The motion to evaluate.
     * @return A {@link DihedralPair} instance for the given instance.
     */
    DihedralPair genPair(final InternalMotion curMot) {
        try {
            return new DihedralPair(curMot.getMembers().get(1), curMot
                    .getMembers().get(2));
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    String.format(
                            "index mismatch when getting inner two atoms from list (%s)",
                            Joiner.on(',').join(curMot.getMembers())), e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultNormalModeCalculation)) {
            return false;
        }
        final DefaultNormalModeCalculation rhs = (DefaultNormalModeCalculation) object;
        return new EqualsBuilder().appendSuper(super.equals(rhs))
                .append(this.normalModes, rhs.normalModes).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-260541395, -1134444587)
                .appendSuper(super.hashCode()).append(this.normalModes)
                .toHashCode();
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
                .append("atoms", this.getAtoms())
                .append("frequencyValues", this.getFrequencyValues())
                .append("cpuTimes", this.getCpuTimes())
                .append("mult", this.getMult())
                .append("terminationDates", this.getTerminationDates())
                .append("rotPart", this.getRotPart())
                .append("normalModes", this.normalModes).toString();
    }
}
