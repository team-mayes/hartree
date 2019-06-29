package org.cmayes.hartree.loader.gaussian;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.util.Map;

import org.cmayes.hartree.model.DihedralPair;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.model.NormalModeSummary;
import org.cmayes.hartree.model.def.DefaultNormalModeSummary;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestG09NormalModeLoader {
    private static final int EXAMPLE_SUM_IDX = 41;
    private static final int NORM_COUNT = 66;
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/";
    private static final NormalModeLoader LOADER = new NormalModeLoader();
    private static NormalModeCalculation calc1;

    /**
     * Load test files.
     * 
     * @throws Exception
     *             When there are problems.
     */
    @BeforeClass
    public static final void setUpClass() throws Exception {
        calc1 = LOADER.load("glucose5m062xEtOHnorm.log", new FileReader(
                FILE_DIR_PFX + "glucose5m062xEtOHnorm.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testSizes() throws Exception {
        assertThat(calc1.getNormalModes().size(), equalTo(NORM_COUNT));
        final Map<NormalMode, NormalModeSummary> sums = calc1.generateReport()
                .getSummaries();
        assertThat(sums.size(), equalTo(NORM_COUNT));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testSummary42() throws Exception {
        assertThat(calc1.getNormalModes().size(), equalTo(NORM_COUNT));
        final Map<NormalMode, NormalModeSummary> sums = calc1.generateReport()
                .getSummaries();
        assertThat(sums.size(), equalTo(NORM_COUNT));
        assertThat(sums.get(calc1.getNormalModes().get(EXAMPLE_SUM_IDX)),
                equalTo(getSum42()));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testInit2THFm062XnormBadInput() throws Exception {
        LOADER.load("init2_THF+m062Xnorm.log", new FileReader(FILE_DIR_PFX
                + "init2_THF+m062Xnorm.log"));
    }

    /**
     * Returns a manual configuration for the 42nd normal frequency mode.
     * 
     * @return A manual configuration for the 42nd normal frequency mode.
     */
    public NormalModeSummary getSum42() {
        final DefaultNormalModeSummary sum = new DefaultNormalModeSummary();
        sum.addToBondStretchingWeight(0.4);
        sum.addToBondStretchingWeight(0.4);
        sum.addToBondStretchingWeight(0.4);
        sum.addToBondStretchingWeight(0.8);
        sum.addToBondStretchingWeight(0.4);
        sum.addToAngleBendingWeight(0.6);
        sum.addToAngleBendingWeight(0.8);
        sum.addToAngleBendingWeight(0.8);
        sum.addToAngleBendingWeight(2.3);
        sum.addToAngleBendingWeight(2.0);
        sum.addToAngleBendingWeight(0.5);
        sum.addToAngleBendingWeight(1.4);
        sum.addToAngleBendingWeight(0.7);
        sum.addToAngleBendingWeight(0.7);
        sum.addToAngleBendingWeight(0.5);
        sum.addToAngleBendingWeight(1.8);
        sum.addToAngleBendingWeight(0.8);
        sum.addToAngleBendingWeight(0.5);
        sum.addToAngleBendingWeight(4.2);
        sum.addToAngleBendingWeight(0.8);
        sum.addToAngleBendingWeight(1.5);
        sum.addToAngleBendingWeight(2.5);
        sum.addToAngleBendingWeight(0.3);
        sum.addToAngleBendingWeight(1.1);
        sum.addToAngleBendingWeight(0.7);
        sum.addToAngleBendingWeight(2.2);
        sum.addToAngleBendingWeight(1.3);
        sum.addToAngleBendingWeight(2.6);
        sum.addToAngleBendingWeight(3.7);
        sum.addToAngleBendingWeight(0.4);
        sum.addToAngleBendingWeight(1.3);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 0.7);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 0.7);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 1.6);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 2.3);
        sum.addToDihedralPairWeights(new DihedralPair(1, 2), 2.5);
        sum.addToDihedralPairWeights(new DihedralPair(1, 7), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(1, 7), 2.0);
        sum.addToDihedralPairWeights(new DihedralPair(1, 11), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(1, 11), 0.5);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 0.8);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 1.4);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 2.5);
        sum.addToDihedralPairWeights(new DihedralPair(2, 3), 2.2);
        sum.addToDihedralPairWeights(new DihedralPair(2, 8), 0.3);
        sum.addToDihedralPairWeights(new DihedralPair(2, 8), 1.4);
        sum.addToDihedralPairWeights(new DihedralPair(3, 4), 0.8);
        sum.addToDihedralPairWeights(new DihedralPair(3, 4), 0.5);
        sum.addToDihedralPairWeights(new DihedralPair(3, 4), 0.9);
        sum.addToDihedralPairWeights(new DihedralPair(3, 4), 1.0);
        sum.addToDihedralPairWeights(new DihedralPair(3, 4), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(3, 9), 0.5);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 0.8);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 1.2);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 1.8);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 1.0);
        sum.addToDihedralPairWeights(new DihedralPair(4, 5), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(4, 10), 0.7);
        sum.addToDihedralPairWeights(new DihedralPair(4, 10), 1.3);

        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 0.9);
        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 0.5);
        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 3.5);
        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 4.7);
        sum.addToDihedralPairWeights(new DihedralPair(5, 6), 4.3);

        sum.addToDihedralPairWeights(new DihedralPair(5, 11), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(5, 11), 0.4);
        sum.addToDihedralPairWeights(new DihedralPair(5, 11), 3.1);

        sum.addToDihedralPairWeights(new DihedralPair(6, 12), 0.6);
        sum.addToDihedralPairWeights(new DihedralPair(6, 12), 0.7);
        return sum;
    }
}
