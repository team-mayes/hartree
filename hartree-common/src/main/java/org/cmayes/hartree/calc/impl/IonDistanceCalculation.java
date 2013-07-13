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
}
