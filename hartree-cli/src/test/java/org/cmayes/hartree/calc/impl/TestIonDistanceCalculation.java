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

import com.cmayes.common.chem.AtomicElement;

/**
 * Tests for {@link GlucoseBondLengthCalculation}.
 * 
 * @author cmayes
 */
public class TestIonDistanceCalculation {
    private static final String GLCNAC_LOG = "glucANaO6c.log";
    private static final String AGLC_LOG = "aglc_1c4_Na1b3lwatmwatca.log";

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
    public void testAglcOxyLengths() throws Exception {
        final IonDistanceCalculation ionCalc = new IonDistanceCalculation(
                AtomicElement.CALCIUM);
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) ionCalc
                .calculate(loadTarget(AGLC_LOG));
        final List<Double> oxygenDistances = bondCalc.getIonDistances();
        assertEquals(6, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(3.39007, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(2.43021, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(5.15454, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(2.44420, ERR_MARGIN));
        assertThat(oxygenDistances.get(4), closeTo(2.53585, ERR_MARGIN));
        assertThat(oxygenDistances.get(5), closeTo(5.45309, ERR_MARGIN));
    }
    
    /**
     * @throws Exception
     *             If there are problems.
     */
    @Test
    public void testBxyloseOxyLengths() throws Exception {
        final IonDistanceCalculation ionCalc = new IonDistanceCalculation(
                AtomicElement.SODIUM);
        final CpCalculationSnapshot bondCalc = (CpCalculationSnapshot) ionCalc
                .calculate(loadTarget(GLCNAC_LOG));
        final List<Double> oxygenDistances = bondCalc.getIonDistances();
        assertEquals(6, oxygenDistances.size());
        assertThat(oxygenDistances.get(0), closeTo(7.14877, ERR_MARGIN));
        assertThat(oxygenDistances.get(1), closeTo(8.76770, ERR_MARGIN));
        assertThat(oxygenDistances.get(2), closeTo(7.50201, ERR_MARGIN));
        assertThat(oxygenDistances.get(3), closeTo(4.78231, ERR_MARGIN));
        assertThat(oxygenDistances.get(4), closeTo(5.66443, ERR_MARGIN));
        assertThat(oxygenDistances.get(5), closeTo(2.27183, ERR_MARGIN));
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
        return (CpCalculationSnapshot) BOND_CALC.calculate(RING_CALC
                .calculate(LOADER.load("glucNa3eO4areacttwater.out",
                        new FileReader(FILE_DIR_PFX + tgtLog))));
    }
}
