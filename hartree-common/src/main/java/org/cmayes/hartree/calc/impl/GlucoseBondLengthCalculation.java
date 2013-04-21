package org.cmayes.hartree.calc.impl;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.util.ChemUtils;

/**
 * @author cmayes
 * 
 */
public class GlucoseBondLengthCalculation implements Calculation {
    private static final int LAST_CARBON_IDX = 5;
    private static final int FULL_RING_SIZE = 6;
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.calc.Calculation#calculate(java.lang.Object)
     */
    @Override
    public Object calculate(final Object rawInput) {
        if (rawInput instanceof CpCalculationSnapshot) {
            final CpCalculationSnapshot cpSnap = new CpCalculationSnapshot(
                    (CpCalculationSnapshot) rawInput);
            try {
                fillCarbons(cpSnap);
            } catch (final NotFoundException e) {
                logger.warn("Could not find all carbon lengths for source "
                        + cpSnap.getSourceName(), e);
            } catch (final IllegalStateException e) {
                logger.warn("Could not find all carbon lengths for source "
                        + cpSnap.getSourceName(), e);
            }

            try {
                fillOxygens(cpSnap);
            } catch (final NotFoundException e) {
                logger.warn("Could not find all oxygen lengths for atoms "
                        + cpSnap.getSourceName(), e);
            }
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Finds all of the distances between the carbons (and oxygen) in the ring.
     * 
     * @param cpSnap
     *            The instance to process.
     */
    private void fillCarbons(final CpCalculationSnapshot cpSnap) {
        final List<Atom> glucoseRing = cpSnap.getGlucoseRing();
        if (glucoseRing == null) {
            throw new IllegalStateException("Glucose ring is null");
        } else if (glucoseRing.size() < FULL_RING_SIZE) {
            throw new IllegalStateException(String.format(
                    "Glucose ring has %d atoms, which is less than six",
                    glucoseRing.size()));
        }
        final List<Double> carbLens = new ArrayList<Double>(glucoseRing.size());
        for (int i = 0; i < glucoseRing.size(); i++) {
            int nextIdx = i + 1;
            if (i == LAST_CARBON_IDX) {
                nextIdx = 0;
            }
            carbLens.add(ChemUtils.vectorForAtom(glucoseRing.get(i)).distance(
                    ChemUtils.vectorForAtom(glucoseRing.get(nextIdx))));
        }
        cpSnap.setCarbonDistances(carbLens);
    }

    /**
     * Finds all of the distances between the non-ring oxygens and the carbons.
     * Note that the last oxygen is O6; O5 is skipped.
     * 
     * @param cpSnap
     *            The instance to process.
     */
    private void fillOxygens(final CpCalculationSnapshot cpSnap) {
        final List<Atom> glucoseRing = cpSnap.getGlucoseRing();
        final List<Atom> otherAtoms = new ArrayList<Atom>(cpSnap.getAtoms());
        otherAtoms.removeAll(glucoseRing);
        final List<Double> oxyLens = new ArrayList<Double>();
        // Starting at idx 1 to skip oxygen
        for (int i = 1; i < glucoseRing.size() - 1; i++) {
            final Atom curCarb = glucoseRing.get(i);
            try {
                oxyLens.add(findLen(curCarb, otherAtoms, AtomicElement.OXYGEN));
            } catch (final NotFoundException e) {
                logger.debug(
                        "Couldn't find oxygen for carbon {}; trying to find a nitrogen instead",
                        curCarb);
                oxyLens.add(findLen(curCarb, otherAtoms, AtomicElement.NITROGEN));
            }
        }
        try {
            final Atom nonRingCarbon = findCarbon(otherAtoms);
            oxyLens.add(findLen(nonRingCarbon, otherAtoms, AtomicElement.OXYGEN));
        } catch (final NotFoundException e) {
            logger.debug(
                    "No non-ring carbon found for input "
                            + cpSnap.getSourceName(), e);
        }
        cpSnap.setOxygenDistances(oxyLens);
    }

    /**
     * Finds the first carbon in the list of non-ring atoms.
     * 
     * @param otherAtoms
     *            The atoms to search.
     * @return The first carbon.
     * @throws NotFoundException
     *             If no carbon is found.
     */
    private Atom findCarbon(final List<Atom> otherAtoms) {
        for (Atom atom : otherAtoms) {
            if (AtomicElement.CARBON.equals(atom.getType())) {
                return atom;
            }
        }
        throw new NotFoundException("No carbon found in non-ring atoms.");
    }

    /**
     * Finds the distance between the given carbon and the otherAtoms group of
     * the given type that has a bond with it the carbon.
     * 
     * @param curCarb
     *            The carb to check.
     * @param otherAtoms
     *            The list of atoms containing candidate elements (and a
     *            carbon).
     * @param elemType
     *            The element type to search for.
     * @return The distance.
     * @throws NotFoundException
     *             When the atom of the given element isn't found.
     */
    private Double findLen(final Atom curCarb, final List<Atom> otherAtoms,
            final AtomicElement elemType) {
        for (Atom atom : otherAtoms) {
            if (ChemUtils.hasBond(curCarb, atom)
                    && elemType.equals(atom.getType())) {
                return ChemUtils.vectorForAtom(curCarb).distance(
                        ChemUtils.vectorForAtom(atom));
            }
        }
        throw new NotFoundException(String.format("No %s found for carbon %s",
                elemType.name().toLowerCase(), curCarb));
    }

}
