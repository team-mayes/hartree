package org.cmayes.hartree;

import static com.cmayes.common.CommonConstants.FILE_SEP;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.cmayes.hartree.disp.csv.CalculationSnapshotCsvDisplay;
import org.cmayes.hartree.disp.txt.NormalModeTextDisplay;
import org.cmayes.hartree.loader.gaussian.NormalModeLoader;
import org.cmayes.hartree.loader.gaussian.SnapshotLoader;
import org.cmayes.hartree.model.CalculationSnapshot;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.proc.FileProcessor;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.mockito.Mockito;

import com.cmayes.common.util.EnvUtils;

/**
 * Tests the main class.
 * 
 * @author cmayes
 */
public class TestMain {
    private static final String SNAP_ARG = "snap";
    private static final String NORM_ARG = "norm";
    private static final String TEST_ARG = "test";
    private static final String TEST_OUT = "test_out";
    private static final String GAUSS_DIR = String.format(
            "src%stest%sresources%sfiles%sg09", FILE_SEP, FILE_SEP, FILE_SEP,
            FILE_SEP);
    private static final String REV4_LOC = String.format("%s%sGL_THF_rev4.log",
            GAUSS_DIR, FILE_SEP);

    /**
     * Tests specifying an existing file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testFilePresent() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-f", REV4_LOC, TEST_ARG);
        assertThat(main.getFile().getPath(), equalTo(REV4_LOC));
    }

    /**
     * Tests specifying a missing file.
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
     * Tests specifying an existing input dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testDirPresent() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-d", GAUSS_DIR, TEST_ARG);
        assertThat(main.getDirectory().getPath(), equalTo(GAUSS_DIR));
    }

    /**
     * Tests specifying a file as a directory.
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
     * Tests specifying a missing dir.
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

    /**
     * Tests specifying a missing out dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testOutDir() throws Exception {
        final File outDir = new File(TEST_OUT);
        assertFalse(outDir.exists());
        try {
            final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
            main.doMain("-f", REV4_LOC, "-o", TEST_OUT, TEST_ARG);
            assertThat(main.getOutDir().getPath(), equalTo(TEST_OUT));
            assertTrue(outDir.exists());
        } finally {
            EnvUtils.recursiveDelete(outDir);
        }
    }

    /**
     * Tests specifying an existing out dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testOutDirExists() throws Exception {
        final File outDir = new File(TEST_OUT);
        assertFalse(outDir.exists());
        if (!outDir.mkdirs()) {
            fail("Could not make dir " + TEST_OUT);
        }
        assertTrue(outDir.exists());
        try {
            final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
            main.doMain("-f", REV4_LOC, "-o", TEST_OUT, TEST_ARG);
            assertThat(main.getOutDir().getPath(), equalTo(TEST_OUT));
        } finally {
            EnvUtils.recursiveDelete(outDir);
        }
    }

    /**
     * Tests specifying an existing out dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test
    public void testPrintErrorUsage() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(baos);
        final CmdLineParser parser = mock(CmdLineParser.class);
        final CmdLineException clException = new CmdLineException(parser,
                "Test exception");
        Main.printErrorUsage(ps, clException);
        final String content = baos.toString();
        assertThat(content, containsString(Main.getHandlerNames()));
        verify(parser).printUsage(ps);
    }

    /**
     * Tests calling main without an argument.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = CmdLineException.class)
    public void testNoArg() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-f", REV4_LOC);
    }

    /**
     * Tests calling main with an invalid argument.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = CmdLineException.class)
    public void testBadArg() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain("-f", REV4_LOC, "badArg");
    }

    /**
     * Tests calls for a {@link FileProcessor} for the NORMAL_MODE handling type
     * with a file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testProcessorNormFile() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        final FileProcessor<NormalModeCalculation> fp = mock(FileProcessor.class);
        main.setTestFileProcessor(fp);
        main.doMain("-f", REV4_LOC, NORM_ARG);
        assertThat(main.getDisplay(), instanceOf(NormalModeTextDisplay.class));
        assertThat(main.getLoader(), instanceOf(NormalModeLoader.class));
        verify(fp).display(Mockito.any(File.class));
    }

    /**
     * Tests calls for a {@link FileProcessor} for the NORMAL_MODE handling type
     * with a directory.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testProcessorNormDir() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        final FileProcessor<NormalModeCalculation> fp = mock(FileProcessor.class);
        main.setTestFileProcessor(fp);
        main.doMain("-d", GAUSS_DIR, NORM_ARG);
        assertThat(main.getDisplay(), instanceOf(NormalModeTextDisplay.class));
        assertThat(main.getLoader(), instanceOf(NormalModeLoader.class));
        verify(fp).displayAll(Mockito.any(File.class));
    }

    /**
     * Tests a call for a NORMAL_MODE process without a file or dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = CmdLineException.class)
    public void testNormNone() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain(NORM_ARG);
    }

    /**
     * Tests a call for a SNAPSHOT process without a file or dir.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @Test(expected = CmdLineException.class)
    public void testSnapNone() throws Exception {
        final Main<NormalModeCalculation> main = new Main<NormalModeCalculation>();
        main.doMain(SNAP_ARG);
    }

    /**
     * Tests calls for a {@link FileProcessor} for the SNAPSHOT handling type
     * with a file.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testProcessorSnapFile() throws Exception {
        final Main<CalculationSnapshot> main = new Main<CalculationSnapshot>();
        final FileProcessor<CalculationSnapshot> fp = mock(FileProcessor.class);
        main.setTestFileProcessor(fp);
        main.doMain("-f", REV4_LOC, SNAP_ARG);
        assertThat(main.getDisplay(),
                instanceOf(CalculationSnapshotCsvDisplay.class));
        assertThat(main.getLoader(), instanceOf(SnapshotLoader.class));
        verify(fp).display(Mockito.any(File.class));
    }

    /**
     * Tests calls for a {@link FileProcessor} for the SNAPSHOT handling type
     * with a directory.
     * 
     * @throws Exception
     *             When the test throws an exception.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testProcessorSDir() throws Exception {
        final Main<CalculationSnapshot> main = new Main<CalculationSnapshot>();
        final FileProcessor<CalculationSnapshot> fp = mock(FileProcessor.class);
        main.setTestFileProcessor(fp);
        main.doMain("-d", GAUSS_DIR, SNAP_ARG);
        assertThat(main.getDisplay(),
                instanceOf(CalculationSnapshotCsvDisplay.class));
        assertThat(main.getLoader(), instanceOf(SnapshotLoader.class));
        verify(fp).displayAll(Mockito.any(File.class));
    }
}
