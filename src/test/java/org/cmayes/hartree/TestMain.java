package org.cmayes.hartree;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.cmayes.hartree.model.NormalModeCalculation;
import org.junit.Test;

/**
 * Tests the main class.
 * 
 * @author cmayes
 */
public class TestMain {
    private static final String GAUSS_DIR = "src/test/resources/files/g09";
    private static final String REV4_LOC = GAUSS_DIR + "/GL_THF_rev4.log";

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testFilePresent() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-f", REV4_LOC, "norm");
        assertThat(main.getFile().getPath(), equalTo(REV4_LOC));
    }

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFileMissing() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-f", "badLoc");
    }

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testDirPresent() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-d", GAUSS_DIR, "norm");
        assertThat(main.getDirectory().getPath(), equalTo(GAUSS_DIR));
    }

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDirNotDir() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-d", REV4_LOC);
        assertThat(main.getDirectory().getPath(), equalTo(GAUSS_DIR));
    }

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDirNotPresent() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-d", "badLoc");
        assertThat(main.getDirectory().getPath(), equalTo(GAUSS_DIR));
    }
}
