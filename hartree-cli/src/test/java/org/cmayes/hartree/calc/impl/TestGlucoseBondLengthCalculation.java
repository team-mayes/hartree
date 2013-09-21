package org.cmayes.hartree.calc.impl;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.util.List;

import org.cmayes.hartree.loader.gaussian.SnapshotLoader;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.junit.Test;

/**
 * Tests for {@link GlucoseBondLengthCalculation}.
 * 
 * @author cmayes
 */
public class TestGlucoseBondLengthCalculation {
    private static final String SODIUM_LOG = "glucNa1cO2Bareactwater.log";
    private static final String BXYLOSE_LOG = "bxyl_4h5_38m062xm06relb3lypbigcon2b3ltstts.log";
    private static final String NAG_LOG = "nag_1c4_244relaxrelaxtsb3lyptsb3ltsircropt.log";
    
    private static final double ERR_MARGIN = .01;
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/snapshot/";
    private static final GlucoseRingCalculation RING_CALC = new GlucoseRingCalculation();
    private static final GlucoseBondLengthCalculation BOND_CALC = new GlucoseBondLengthCalculation();

    // NAG //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testNagCarbonLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(NAG_LOG));
        final List<Double> carbonDistances = bondCalc.getCarbonDistances();
        assertEquals(6, carbonDistances.size());
        assertThat(carbonDistances.get(0), closeTo(1.40905, ERR_MARGIN));
        assertThat(carbonDistances.get(1), closeTo(1.54057, ERR_MARGIN));
        assertThat(carbonDistances.get(2), closeTo(1.54651, ERR_MARGIN));
        assertThat(carbonDistances.get(3), closeTo(1.53909, ERR_MARGIN));
        assertThat(carbonDistances.get(4), closeTo(1.53793, ERR_MARGIN));
        assertThat(carbonDistances.get(5), closeTo(1.43488, ERR_MARGIN));
    }

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testNagOxyLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(NAG_LOG));
        final List<Double> oxygenDistances = bondCalc.getOxygenDistances();
        assertEquals(5, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(1.42980, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(1.45647, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(1.40613, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(1.43464, ERR_MARGIN));
        assertThat(oxygenDistances.get(4), closeTo(1.41877, ERR_MARGIN));
    }
    
    
    // Sodium //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testSodiumCarbonLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(SODIUM_LOG));
        final List<Double> carbonDistances = bondCalc.getCarbonDistances();
        assertEquals(6, carbonDistances.size());
        assertThat(carbonDistances.get(0), closeTo(1.41851, ERR_MARGIN));
        assertThat(carbonDistances.get(1), closeTo(1.52432, ERR_MARGIN));
        assertThat(carbonDistances.get(2), closeTo(1.51855, ERR_MARGIN));
        assertThat(carbonDistances.get(3), closeTo(1.51977, ERR_MARGIN));
        assertThat(carbonDistances.get(4), closeTo(1.52831, ERR_MARGIN));
        assertThat(carbonDistances.get(5), closeTo(1.42168, ERR_MARGIN));
    }

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testSodiumOxyLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(SODIUM_LOG));
        final List<Double> oxygenDistances = bondCalc.getOxygenDistances();
        assertEquals(5, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(1.38340, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(1.40775, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(1.41092, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(1.40892, ERR_MARGIN));
        assertThat(oxygenDistances.get(4), closeTo(1.42184, ERR_MARGIN));
    }

    // Beta Xylose (no non-ring carbon) //

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testBxyloseCarbonLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(BXYLOSE_LOG));
        final List<Double> carbonDistances = bondCalc.getCarbonDistances();
        assertEquals(6, carbonDistances.size());
        assertThat(carbonDistances.get(0), closeTo(1.40615, ERR_MARGIN));
        assertThat(carbonDistances.get(1), closeTo(1.52832, ERR_MARGIN));
        assertThat(carbonDistances.get(2), closeTo(1.51420, ERR_MARGIN));
        assertThat(carbonDistances.get(3), closeTo(1.51854, ERR_MARGIN));
        assertThat(carbonDistances.get(4), closeTo(1.53170, ERR_MARGIN));
        assertThat(carbonDistances.get(5), closeTo(1.43387, ERR_MARGIN));
    }

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testBxyloseOxyLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget(BXYLOSE_LOG));
        final List<Double> oxygenDistances = bondCalc.getOxygenDistances();
        assertEquals(4, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(1.41224, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(1.42240, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(1.41885, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(1.41795, ERR_MARGIN));
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
