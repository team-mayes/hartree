package org.cmayes.hartree.loader.gaussian;

import org.cmayes.hartree.model.BaseResult;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestG16TiMissing {
    private static final double ERR_MARGIN = .01;
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestG16TiMissing.class);
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g16/";
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static BaseResult calc1;

    /**
     * Load test files.
     * 
     * @throws Exception
     *             When there are problems.
     */
    @BeforeClass
    public static final void setUpClass() throws Exception {
        calc1 = LOADER.load("tieg4pdc2_optfreq.log", new FileReader(
                FILE_DIR_PFX + "tieg4pdc2_optfreq.log"));
        LOGGER.debug("Calc: " + calc1);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testMult() throws Exception {
        assertThat(calc1.getMult(), equalTo(1));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testCharge() throws Exception {
        assertThat(calc1.getCharge(), equalTo(0));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testElecEn() throws Exception {
        assertThat(calc1.getElecEn(), closeTo(-2488.87921549, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testZpeCorr() throws Exception {
        assertThat(calc1.getZpeCorrection(), closeTo(0.418926, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFreqVals() throws Exception {
        assertThat(calc1.getFrequencyValues().get(0), closeTo(15.4741, ERR_MARGIN));
        assertThat(calc1.getFrequencyValues().get(1), closeTo(21.3676, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFunctional() throws Exception {
        assertThat(calc1.getFunctional(), equalTo("RM062X"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testBasisSet() throws Exception {
        assertThat(calc1.getBasisSet(), equalTo("def2TZVP"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testStoi() throws Exception {
        assertThat(calc1.getStoichiometry(), equalTo("C15H24O14Ti"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testDipoleMoment() throws Exception {
        assertThat(calc1.getDipoleMomentTotal(), closeTo(6.694, ERR_MARGIN));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testSolvent() throws Exception {
        assertEquals("1,2-EthaneDiol", calc1.getSolvent());
    }
}
