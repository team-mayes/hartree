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
    private static final double ERR_MARGIN = .01;
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/snapshot/";
    private static final GlucoseRingCalculation RING_CALC = new GlucoseRingCalculation();
    private static final GlucoseBondLengthCalculation BOND_CALC = new GlucoseBondLengthCalculation();

    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testCarbonLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget());
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
    public void testOxyLengths() throws Exception {
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) BOND_CALC
                .calculate(loadTarget());
        final List<Double> oxygenDistances = bondCalc.getOxygenDistances();
        assertEquals(5, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(1.38340, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(1.40775, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(1.41092, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(1.40892, ERR_MARGIN));
        assertThat(oxygenDistances.get(4), closeTo(1.42184, ERR_MARGIN));
    }

    /**
     * Loads a {@link BaseResult} instance and passes it through a glucose ring
     * calculation, generating a {@link CpCalculationSnapshot}.
     * 
     * @return A snapshot to test against.
     * @throws Exception
     *             If there are problems.
     */
    private CpCalculationSnapshot loadTarget() throws Exception {
        return (CpCalculationSnapshot) RING_CALC.calculate(LOADER.load(
                "glucNa3eO4areacttwater.out", new FileReader(FILE_DIR_PFX
                        + "glucNa1cO2Bareactwater.log")));
    }
}
