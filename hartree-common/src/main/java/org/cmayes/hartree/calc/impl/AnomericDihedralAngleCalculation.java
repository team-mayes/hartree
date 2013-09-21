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
 * Calculates the dihedral angle for some other part of the molecule.
 * 
 * <ul>
 * <li>
 * <b>thirdAngle1</b>: C5 - O5 - C1 - O1</li>
 * <li>
 * <b>thirdAngle2</b>: C3 - C2 - C1 - O1</li>
 * </ul>
 * 
 * @author cmayes
 * 
 */
public class AnomericDihedralAngleCalculation implements Calculation {
    private static final int O5_LOC = 0;
    private static final int C1_LOC = 1;
    private static final int C2_LOC = 2;
    private static final int C3_LOC = 3;
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
                logger.warn("No glucose ring for third arm calc {}, skipping.",
                        cpSnap.getSourceName());
                return cpSnap;
            }

            try {
                fillThirdDihedralAngles(cpSnap);
            } catch (final NotFoundException e) {
                logger.warn("Missing atoms for third arm calc {}, skipping.",
                        cpSnap.getSourceName(), e);
            }
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Fills in thirdAngle1 and thirdAngle2 on the given snap instance.
     * 
     * <ul>
     * <li>
     * <b>thirdAngle1</b>: C5 - O5 - C1 - O1</li>
     * <li>
     * <b>thirdAngle2</b>: C3 - C2 - C1 - O1</li>
     * </ul>
     * 
     * @param cpSnap
     *            The {@link CpCalculationSnapshot} to process and fill in.
     */
    private void fillThirdDihedralAngles(final CpCalculationSnapshot cpSnap) {
        final List<Atom> glucoseRing = cpSnap.getGlucoseRing();
        final List<Atom> otherAtoms = new ArrayList<Atom>(cpSnap.getAtoms());
        otherAtoms.removeAll(glucoseRing);
        final Atom oxy1 = findSingleBondAtom(glucoseRing.get(C1_LOC),
                otherAtoms, AtomicElement.OXYGEN);
        cpSnap.setAnoAngle1(calcDihedralAngle(glucoseRing.get(C5_LOC),
                glucoseRing.get(O5_LOC), glucoseRing.get(C1_LOC), oxy1));
        cpSnap.setAnoAngle2(calcDihedralAngle(glucoseRing.get(C3_LOC),
                glucoseRing.get(C2_LOC), glucoseRing.get(C1_LOC), oxy1));
    }
}
