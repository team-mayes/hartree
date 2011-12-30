package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cmayes.hartree.model.Atom;
import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.InternalMotion;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.NormalModeSummary;

import com.google.common.base.Joiner;

/**
 * Default implementation of a normal mode summary.
 * 
 * @author cmayes
 */
public class DefaultNormalModeCalculation extends BaseCalculationResult
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
     */
    public Map<NormalMode, NormalModeSummary> calculateSummaries() {
        final Map<NormalMode, NormalModeSummary> summaries = new LinkedHashMap<NormalMode, NormalModeSummary>();
        for (NormalMode curMode : normalModes) {
            summaries.put(curMode, calculateSummary(curMode));
        }
        return summaries;
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
            return new DihedralPair(getAtomById(curMot.getMembers().get(1)),
                    getAtomById(curMot.getMembers().get(2)));
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    String.format(
                            "index mismatch when getting inner two atoms from list (%s)",
                            Joiner.on(',').join(curMot.getMembers())), e);
        }
    }

    /**
     * Looks up the atom with the given ID by pulling the atom in the atoms
     * field by the ID - 1.
     * 
     * @param id
     *            The atom to pull.
     * @return The atom at the zero-based index equivalent of the ID.
     */
    Atom getAtomById(final int id) {
        try {
            return getAtoms().get(id - 1);
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No atom with ID " + id, e);
        }
    }
}
