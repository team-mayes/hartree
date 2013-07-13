package org.cmayes.hartree.calc.impl;

import static com.cmayes.common.util.ChemUtils.calcDihedralAngle;
import static com.cmayes.common.util.ChemUtils.findSingleBondAtom;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.model.Atom;

/**
 * Calculates the dihedral angle for the Hydromethyl arm of the given molecule.
 * 
 * <ul>
 * <li>
 * <b>hmArmAngle1</b>: O5 - C5 - C6 - O6</li>
 * <li>
 * <b>hmArmAngle2</b>: C4 - C5 - C6 - O6</li>
 * </ul>
 * 
 * @author cmayes
 * 
 */
public class HMDihedralAngleCalculation implements Calculation {
    private static final int C4_LOC = 4;
    private static final int C5_LOC = 5;
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
            if (cpSnap.getGlucoseRing() == null) {
                logger.warn("No glucose ring for HM arm calc {}, skipping.",
                        cpSnap.getSourceName());
                return cpSnap;
            }
            try {
                fillHMDihedralAngles(cpSnap);
            } catch (final NotFoundException e) {
                logger.warn("Missing atoms for HM arm calc {}, skipping.",
                        cpSnap.getSourceName(), e);
            }
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Fills in hmArmAngle1 and hmArmAngle2 on the given snap instance.
     * 
     * <ul>
     * <li>
     * <b>hmArmAngle1</b>: O5 - C5 - C6 - O6</li>
     * <li>
     * <b>hmArmAngle2</b>: C4 - C5 - C6 - O6</li>
     * </ul>
     * 
     * @param cpSnap
     *            The {@link CpCalculationSnapshot} to process and fill in.
     */
    private void fillHMDihedralAngles(final CpCalculationSnapshot cpSnap) {
        final List<Atom> glucoseRing = cpSnap.getGlucoseRing();
        final List<Atom> otherAtoms = new ArrayList<Atom>(cpSnap.getAtoms());
        otherAtoms.removeAll(glucoseRing);
        final Atom hmArmCarbon = findSingleBondAtom(glucoseRing.get(5), otherAtoms,
                AtomicElement.CARBON);
        final Atom hmArmOxy = findSingleBondAtom(hmArmCarbon, otherAtoms,
                AtomicElement.OXYGEN);
        cpSnap.setHmArmAngle1(calcDihedralAngle(glucoseRing.get(0),
                glucoseRing.get(C5_LOC), hmArmCarbon, hmArmOxy));
        cpSnap.setHmArmAngle2(calcDihedralAngle(glucoseRing.get(C4_LOC),
                glucoseRing.get(C5_LOC), hmArmCarbon, hmArmOxy));
    }
}
