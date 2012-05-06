package org.cmayes.hartree.loader.gaussian;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileReader;

import org.cmayes.hartree.model.CalculationSnapshot;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestSummaryLoader {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/";
    private static final SnapshotLoader LOADER = new SnapshotLoader();
    private static CalculationSnapshot calc1;

    /**
     * Load test files.
     * 
     * @throws Exception
     *             When there are problems.
     */
    @BeforeClass
    public static final void setUpClass() throws Exception {
        calc1 = LOADER.load(new FileReader(FILE_DIR_PFX
                + "glucNa3eO4areacttwater.out"));
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
    public void testElecEn() throws Exception {
        assertThat(calc1.getElecEn(), closeTo(-849.236562278, .01));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFreqVals() throws Exception {
        logger.debug("Freq vals: " + calc1.getFrequencyValues());
        
    }
}
