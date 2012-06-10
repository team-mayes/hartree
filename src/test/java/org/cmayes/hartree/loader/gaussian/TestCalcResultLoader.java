package org.cmayes.hartree.loader.gaussian;

import java.io.FileReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestCalcResultLoader {
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testThf() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        logger.debug("Result: "
                + loader.load(new FileReader(FILE_DIR_PFX + "GL_THF_rev4.log")));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testGasRad2() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "init_gas_rad2_3.out"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testThfFail() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "init_THF+negFAIL.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testWater() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "init_water+pos4.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testCamAgain() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "init2thfTS+camAgain3.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void test8spTooLittleData() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX
                + "levoglucosan8spTooLittleData.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testGasm062x() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "m-glucose_gasm062x.out"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testWaterm062x() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX
                + "m-glucose_waterm062xspTooLittleData.out"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testMp4mp2TooLittleData() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX
                + "methanolmp4mp2TooLittleData.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testInit2THFm062XnormBadInput() throws Exception {
        final CalcResultLoader loader = new CalcResultLoader();
        loader.load(new FileReader(FILE_DIR_PFX + "init2_THF+m062Xnorm.log"));
    }
}
