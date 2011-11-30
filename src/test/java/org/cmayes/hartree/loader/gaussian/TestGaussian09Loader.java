package org.cmayes.hartree.loader.gaussian;

import java.io.FileReader;

import org.junit.Test;

/**
 * Tests load cases using different files.
 * 
 * @author cmayes
 * 
 */
public class TestGaussian09Loader {
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/";

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testThf() throws Exception {
        final Gaussian09Loader loader = new Gaussian09Loader();
        loader.load(new FileReader(FILE_DIR_PFX + "GL_THF_rev4.log"));
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testGasRad2() throws Exception {
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
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
        final Gaussian09Loader loader = new Gaussian09Loader();
        loader.load(new FileReader(FILE_DIR_PFX
                + "methanolmp4mp2TooLittleData.log"));
    }

}
