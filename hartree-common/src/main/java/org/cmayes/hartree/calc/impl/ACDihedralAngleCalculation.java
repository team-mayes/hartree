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
 * Calculates the dihedral angle for the N-Acetyl arm of the given molecule.
 * 
 * <ul>
 * <li>
 * <b>acArmAngle1</b>: C1 - C2 - N - C7</li>
 * <li>
 * <b>acArmAngle2</b>: C3 - C2 - N - C7</li>
 * </ul>
 * 
 * @author cmayes
 * 
 */
public class ACDihedralAngleCalculation implements Calculation {
    private static final int C1_LOC = 1;
    private static final int C2_LOC = 2;
    private static final int C3_LOC = 3;
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
                logger.warn("No glucose ring for AC arm calc {}, skipping.",
                        cpSnap.getSourceName());
                return cpSnap;
            }

            try {
                fillACDihedralAngles(cpSnap);
            } catch (final NotFoundException e) {
                logger.warn("Missing atoms for AC arm calc {}, skipping.",
                        cpSnap.getSourceName(), e);
            }
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Fills in acArmAngle1 and acArmAngle2 on the given snap instance.
     * 
     * <ul>
     * <li>
     * <b>acArmAngle1</b>: C1 - C2 - N - C7</li>
     * <li>
     * <b>acArmAngle2</b>: C3 - C2 - N - C7</li>
     * </ul>
     * 
     * @param cpSnap
     *            The {@link CpCalculationSnapshot} to process and fill in.
     */
    private void fillACDihedralAngles(final CpCalculationSnapshot cpSnap) {
        final List<Atom> glucoseRing = cpSnap.getGlucoseRing();
        final List<Atom> otherAtoms = new ArrayList<Atom>(cpSnap.getAtoms());
        otherAtoms.removeAll(glucoseRing);
        final Atom acArmNitro = findSingleBondAtom(glucoseRing.get(C2_LOC),
                otherAtoms, AtomicElement.NITROGEN);
        final Atom acArmC7 = findSingleBondAtom(acArmNitro, otherAtoms,
                AtomicElement.CARBON);
        cpSnap.setAcArmAngle1(calcDihedralAngle(glucoseRing.get(C1_LOC),
                glucoseRing.get(C2_LOC), acArmNitro, acArmC7));
        cpSnap.setAcArmAngle2(calcDihedralAngle(glucoseRing.get(C3_LOC),
                glucoseRing.get(C2_LOC), acArmNitro, acArmC7));
    }
}
