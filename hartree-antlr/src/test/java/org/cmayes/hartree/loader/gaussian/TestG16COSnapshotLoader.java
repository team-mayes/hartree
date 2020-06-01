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
public class TestG16COSnapshotLoader {
    private static final double ERR_MARGIN = .01;
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestG16COSnapshotLoader.class);
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
        calc1 = LOADER.load("co_gas.log", new FileReader(
                FILE_DIR_PFX + "co_gas.log"));
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
        assertThat(calc1.getElecEn(), closeTo(-113.322294772, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testZpeCorr() throws Exception {
        assertThat(calc1.getZpeCorrection(), closeTo(0.005192, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFreqVals() throws Exception {
        assertThat(calc1.getFrequencyValues().get(0), closeTo(2279.0095, ERR_MARGIN));
        //assertThat(calc1.getFrequencyValues().get(1), closeTo(3852.165, ERR_MARGIN));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testStoi() throws Exception {
        assertThat(calc1.getStoichiometry(), equalTo("CO"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testDipoleMoment() throws Exception {
        assertThat(calc1.getDipoleMomentTotal(), closeTo(0.0528, ERR_MARGIN));
    }
}
