package org.cmayes.hartree.proc.basic;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.Writer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.cmayes.common.file.ExtensionFilter;
import com.cmayes.common.util.EnvUtils;

public class TestBasicInputFileHandler {
    private static final File INDIR = new File(CommonConstants.TMPDIR,
            "basicfpin");
    private static final File OUTDIR = new File(CommonConstants.TMPDIR,
            "basicfpout");
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
     */
    @Test
    public void testBaseDir() throws Exception {
        File inFile = new File(INDIR, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        BasicInputFileHandler proc = new BasicInputFileHandler(
                new ExtensionFilter(MediaType.LOG.getPrimaryExtension()),
                INDIR, OUTDIR);
        Writer outWriter = proc.createOutWriter(inFile, "test",
                MediaType.LOG.getPrimaryExtension());
        outWriter.write("Hello!");
        outWriter.close();
        File outFile = new File(OUTDIR, "someInFile-test.log");
        assertTrue(outFile.exists());
    }

    /**
     * Tests creating an outfile for an infile in the INDIR base directory.
     * 
     * @throws Exception
     */
    @Test
    public void testSubDir() throws Exception {
        File inSubDir = new File(INDIR, "subDir");
        if (!inSubDir.mkdirs()) {
            logger.warn("Could not create subdir " + inSubDir);
        }
        File inFile = new File(inSubDir, "someInFile"
                + MediaType.LOG.getPrimaryExtension());
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        BasicInputFileHandler proc = new BasicInputFileHandler(
                new ExtensionFilter(MediaType.LOG.getPrimaryExtension()),
                INDIR, OUTDIR);
        Writer outWriter = proc.createOutWriter(inFile, "test",
                MediaType.LOG.getPrimaryExtension());
        outWriter.write("Hello!");
        outWriter.close();
        File outSubDir = new File(OUTDIR, "subDir");
        File outFile = new File(outSubDir, "someInFile-test."
                + MediaType.LOG.getPrimaryExtension());
        assertTrue(outFile.exists());
    }
}
