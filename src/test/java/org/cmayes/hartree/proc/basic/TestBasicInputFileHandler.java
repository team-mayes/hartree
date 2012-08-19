package org.cmayes.hartree.proc.basic;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

import org.cmayes.hartree.proc.FileProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.cmayes.common.file.ExtensionFilter;
import com.cmayes.common.util.EnvUtils;

/**
 * Tests for {@link BasicInputFileHandler}.
 * 
 * @author cmayes
 */
public class TestBasicInputFileHandler {
    private static final String HELLO_STR = "Hello!";
    private static final File INDIR = new File(CommonConstants.TMPDIR,
            "basicfpin");
    private static final File OUTDIR = new File(CommonConstants.TMPDIR,
            "basicfpout");
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Cleans up and initializes the test environment.
     */
    @Before
    public void setUp() {
        if (INDIR.exists()) {
            EnvUtils.recursiveDelete(INDIR);
        }
        if (OUTDIR.exists()) {
            EnvUtils.recursiveDelete(OUTDIR);
        }
        if (!INDIR.mkdirs()) {
            logger.warn("Couldn't create " + INDIR);
        }
        if (!OUTDIR.mkdirs()) {
            logger.warn("Couldn't create " + OUTDIR);
        }
    }

    /**
     * Cleans up the test environment.
     */
    @After
    public void tearDown() {
        if (INDIR.exists()) {
            EnvUtils.recursiveDelete(INDIR);
        }
        if (OUTDIR.exists()) {
            EnvUtils.recursiveDelete(OUTDIR);
        }
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testBaseDir() throws Exception {
        final File inFile = new File(INDIR, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        final BasicInputFileHandler proc = new BasicInputFileHandler(
                new ExtensionFilter(MediaType.LOG.getPrimaryExtension()),
                INDIR, OUTDIR);
        final Writer outWriter = proc.createOutWriter(inFile, "test",
                MediaType.LOG.getPrimaryExtension());
        outWriter.write(HELLO_STR);
        outWriter.close();
        final File outFile = new File(OUTDIR, "someInFile-test.log");
        assertTrue(outFile.exists());
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testSubDir() throws Exception {
        final File inSubDir = new File(INDIR, "subDir");
        if (!inSubDir.mkdirs()) {
            logger.warn("Could not create subdir " + inSubDir);
        }
        final File inFile = new File(inSubDir, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        final BasicInputFileHandler proc = new BasicInputFileHandler(
                new ExtensionFilter(MediaType.LOG.getPrimaryExtension()),
                INDIR, OUTDIR);
        final Writer outWriter = proc.createOutWriter(inFile, "test",
                MediaType.LOG.getPrimaryExtension());
        outWriter.write(HELLO_STR);
        outWriter.close();
        final File outSubDir = new File(OUTDIR, "subDir");
        final File outFile = new File(outSubDir, "someInFile-test."
                + MediaType.LOG.getPrimaryExtension());
        assertTrue(outFile.exists());
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testFilter() throws Exception {
        final File inFile = new File(INDIR, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        final File txtFile = new File(INDIR, "someInFile"
                + MediaType.TEXT.getPrimaryExtension());
        if (!txtFile.createNewFile()) {
            logger.warn("Couldn't create " + txtFile);
        }
        final BasicInputFileHandler proc = new BasicInputFileHandler(
                new ExtensionFilter(MediaType.LOG.getPrimaryExtension()),
                INDIR, OUTDIR);
        @SuppressWarnings("unchecked")
        final FileProcessor<Object> fProc = mock(FileProcessor.class);
        proc.handle(INDIR, fProc);
        verify(fProc).display(inFile);
        verifyNoMoreInteractions(fProc);
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testDefaultNoDir() throws Exception {
        final File inFile = new File(INDIR, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        final BasicInputFileHandler proc = new BasicInputFileHandler();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(baos);
        proc.setSysOut(ps);
        final Writer outWriter = proc.createOutWriter(inFile, "test",
                MediaType.LOG.getPrimaryExtension());
        assertThat(outWriter, instanceOf(OutputStreamWriter.class));
        outWriter.write(HELLO_STR);
        outWriter.close();
        assertThat(baos.toString(), equalTo(HELLO_STR));
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testNoFilter() throws Exception {
        final File inFile = new File(INDIR, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        final File txtFile = new File(INDIR, "someInFile"
                + MediaType.TEXT.getPrimaryExtension());
        if (!txtFile.createNewFile()) {
            logger.warn("Couldn't create " + txtFile);
        }
        final BasicInputFileHandler proc = new BasicInputFileHandler();
        @SuppressWarnings("unchecked")
        final FileProcessor<Object> fProc = mock(FileProcessor.class);
        proc.handle(INDIR, fProc);
        verify(fProc).display(inFile);
        verify(fProc).display(txtFile);
        verifyNoMoreInteractions(fProc);
    }
}
