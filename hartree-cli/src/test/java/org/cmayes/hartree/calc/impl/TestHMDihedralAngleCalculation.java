package org.cmayes.hartree.calc.impl;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.io.FileReader;

import org.cmayes.hartree.loader.gaussian.SnapshotLoader;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.junit.Test;

/**
 * Tests for {@link HMDihedralAngleCalculation}.
 * 
 * @author cmayes
 */
public class TestHMDihedralAngleCalculation {
    private static final String BMAN_LOG = "bman_oe_256con2relaxb3lrel.log";
    private static final String NAG_LOG = "nag_e2_442relaxrelaxtsb3lyptsb3ltstircropt.log";
    

    private static final double ERR_MARGIN = .01;
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/snapshot/";
    private static final GlucoseRingCalculation RING_CALC = new GlucoseRingCalculation();
    private static final HMDihedralAngleCalculation DH_CALC = new HMDihedralAngleCalculation();

    // BMAN //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testBmanDihedralAngles() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) DH_CALC
                .calculate(loadTarget(BMAN_LOG));

        assertThat(bondCalc.getHmArmAngle1(), closeTo(166.442, ERR_MARGIN));
        assertThat(bondCalc.getHmArmAngle2(), closeTo(-69.221, ERR_MARGIN));
    }

    // NAG //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testNagDihedralAngles() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) DH_CALC
                .calculate(loadTarget(NAG_LOG));

        assertThat(bondCalc.getHmArmAngle1(), closeTo(-71.966, ERR_MARGIN));
        assertThat(bondCalc.getHmArmAngle2(), closeTo(59.370, ERR_MARGIN));
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
    private CpCalculationSnapshot loadTarget(String tgtLog) throws Exception {
        return (CpCalculationSnapshot) RING_CALC.calculate(LOADER.load(
                "glucNa3eO4areacttwater.out", new FileReader(FILE_DIR_PFX
                        + tgtLog)));
    }
}
