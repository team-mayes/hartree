package org.cmayes.hartree.calc.impl;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.FileReader;

import org.cmayes.hartree.loader.gaussian.SnapshotLoader;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.junit.Test;

/**
 * Tests for {@link ACDihedralAngleCalculation}.
 * 
 * @author cmayes
 */
public class TestACDihedralAngleCalculation {
    private static final String BMAN_LOG = "bman_oe_256con2relaxb3lrel.log";
    private static final String NAG_LOG = "nag_e2_442relaxrelaxtsb3lyptsb3ltstircropt.log";

    private static final double ERR_MARGIN = .01;
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/snapshot/";
    private static final GlucoseRingCalculation RING_CALC = new GlucoseRingCalculation();
    private static final ACDihedralAngleCalculation AC_CALC = new ACDihedralAngleCalculation();

    // BMAN //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testBmanDihedralAngles() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) AC_CALC
                .calculate(loadTarget(BMAN_LOG));

        assertNull(bondCalc.getAcArmAngle1());
        assertNull(bondCalc.getAcArmAngle2());
    }

    // NAG //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testNagDihedralAngles() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) AC_CALC
                .calculate(loadTarget(NAG_LOG));

        assertThat(bondCalc.getAcArmAngle1(), closeTo(-85.801, ERR_MARGIN));
        assertThat(bondCalc.getAcArmAngle2(), closeTo(149.165, ERR_MARGIN));
    }

    /**
     * Loads a {@link BaseResult} instance and passes it through a glucose ring
     * calculation, generating a {@link CpCalculationSnapshot}.
     * 
     * @param tgtLog
     *            The name of the log file to read.
     * @return A snapshot to test against.
     * @throws Exception
     *             If there are problems.
     */
    private CpCalculationSnapshot loadTarget(final String tgtLog) throws Exception {
        return (CpCalculationSnapshot) RING_CALC.calculate(LOADER.load(
                "glucNa3eO4areacttwater.out", new FileReader(FILE_DIR_PFX
                        + tgtLog)));
    }
}
