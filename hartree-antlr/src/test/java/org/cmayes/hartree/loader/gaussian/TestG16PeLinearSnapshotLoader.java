package org.cmayes.hartree.loader.gaussian;

import org.cmayes.hartree.model.BaseResult;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestG16PeLinearSnapshotLoader {
    private static final double ERR_MARGIN = .01;
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TestG16PeLinearSnapshotLoader.class);
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
        calc1 = LOADER.load("pe_linear_4_5_cp.log", new FileReader(
                FILE_DIR_PFX + "pe_linear_4_5_cp.log"));
        LOGGER.debug("Calc: " + calc1);
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testBsse() throws Exception {
        assertThat(calc1.getBsse(), closeTo(0.000885298220, ERR_MARGIN));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testMult() throws Exception {
        assertThat(calc1.getMult(), equalTo(2));
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
        assertThat(calc1.getElecEn(), closeTo(-157.735663486, ERR_MARGIN));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFunctional() throws Exception {
        assertThat(calc1.getFunctional(), equalTo("m062x"));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testBasisSet() throws Exception {
        assertThat(calc1.getBasisSet(), equalToIgnoringCase("def2TZVP"));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testStoi() throws Exception {
        assertThat(calc1.getStoichiometry(), equalTo("C8H18"));
    }

    /**
     * Test.
     *
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testDipoleMoment() throws Exception {
        assertThat(calc1.getDipoleMomentTotal(), closeTo(0.8811, ERR_MARGIN));
    }
}
