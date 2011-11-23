package org.cmayes.hartree;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the main class.
 * 
 * @author cmayes
 */
public class TestMain {
    private static final String GAUSS_DIR = "src/test/resources/files/g09";
    private static final String REV4_LOC = GAUSS_DIR + "/GL_THF_rev4.log";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testFilePresent() throws Exception {
        final Main main = new Main();
        main.doMain("-f", REV4_LOC);
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
        final Main main = new Main();
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
        final Main main = new Main();
        main.doMain("-d", GAUSS_DIR);
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
        final Main main = new Main();
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
        final Main main = new Main();
        main.doMain("-d", "badLoc");
        assertThat(main.getDirectory().getPath(), equalTo(GAUSS_DIR));
    }
}
