package org.cmayes.hartree.calc.impl;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.exception.TooManyException;
import com.cmayes.common.model.Atom;
import com.cmayes.common.util.ChemUtils;

/**
 * @author cmayes
 * 
 */
public class IonDistanceCalculation implements Calculation {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicElement ionTarget;

    /**
     * TODO: Make parameterized ion value.
     */
    public IonDistanceCalculation() {
        ionTarget = AtomicElement.SODIUM;
    }

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
                final Atom ionAtom = ChemUtils.findSingle(ionTarget,
                        cpSnap.getAtoms());
                fillIonDistances(cpSnap, ionAtom);
            } catch (final NotFoundException e) {
                logger.info(
                        "Could not find ion atom of type {}.  Skipping distance calc.",
                        ionTarget.name(), e);
            } catch (final TooManyException e) {
                logger.info(
                        "Found too many atoms of type {}.  Skipping distance calc.",
                        ionTarget.name(), e);
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
     * @param ion
     *            The ion to measure against.
     */
    private void fillIonDistances(final CpCalculationSnapshot cpSnap,
            final Atom ion) {
        final List<Atom> oxygens = cpSnap.getOxygenAtoms();
        final List<Double> ionDistances = new ArrayList<Double>(6);
        for (Atom oxyAtom : oxygens) {
            if (oxyAtom != null) {
                ionDistances.add(ChemUtils.findDistance(ion, oxyAtom));
            }
        }
        cpSnap.setIonDistances(ionDistances);
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
                oxyLens.add(ChemUtils.findBond(curCarb, otherAtoms,
                        AtomicElement.OXYGEN));
            } catch (final NotFoundException e) {
                logger.debug(
                        "Couldn't find oxygen for carbon {}; trying to find a nitrogen instead",
                        curCarb);
                oxyLens.add(ChemUtils.findBond(curCarb, otherAtoms,
                        AtomicElement.NITROGEN));
            }
        }
        try {
            final Atom nonRingCarbon = findCarbon(otherAtoms);
            oxyLens.add(ChemUtils.findBond(nonRingCarbon, otherAtoms,
                    AtomicElement.OXYGEN));
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

}
