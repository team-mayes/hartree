package org.cmayes.hartree.proc.basic;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cmayes.hartree.HandlingType;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.cmayes.hartree.proc.InputFileHandler;
import org.junit.Test;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;

/**
 * Tests for {@link BasicFileProcessor}.
 * 
 * @author cmayes
 */
@SuppressWarnings("unchecked")
public class TestAccumulatingFileProcessor {
    private static final String CHG_SRC_NAME = "changedSrcName";
    private static final String SRC_NAME = "testSrcName";
    private static final String OUT_FILE_NAME = "accumulator-snap.log";

    /**
     * Tests calling displayAll.
     */
    @Test
    public void testDisplayAll() {
        final Loader<BaseResult> theParser = mock(Loader.class);
        final Display<BaseResult> theDisp = mock(Display.class);
        final List<Calculation> calcs = new ArrayList<Calculation>();
        final InputFileHandler fileHandler = mock(InputFileHandler.class);
        final AccumulatingFileProcessor<BaseResult> proc = new AccumulatingFileProcessor<BaseResult>(
                HandlingType.SNAPSHOT, theParser, theDisp, calcs, fileHandler);
        final File dir = new File(CommonConstants.TMPDIR);
        proc.displayAll(dir);
        proc.finish();
        verify(fileHandler).handle(dir, proc);
    }

    /**
     * Tests calling display with no target dir.
     * 
     * @throws IOException
     *             If there's a problem closing the mock writer?
     */
    @Test
    public void testDisplayNoTgt() throws IOException {
        final Loader<BaseResult> theParser = mock(Loader.class);
        final Display<BaseResult> theDisp = mock(Display.class);
        final List<Calculation> calcs = new ArrayList<Calculation>();
        final InputFileHandler fileHandler = mock(InputFileHandler.class);
        final File tmpDir = new File(CommonConstants.TMPDIR);
        final File outFile = new File(tmpDir, OUT_FILE_NAME);
        assertFalse(outFile.exists());
        when(fileHandler.getOutDir()).thenReturn(tmpDir);
        when(theDisp.getMediaType()).thenReturn(MediaType.LOG);
        final Writer writer = mock(Writer.class);
        final AccumulatingFileProcessor<BaseResult> proc = new AccumulatingFileProcessor<BaseResult>(
                HandlingType.SNAPSHOT, theParser, theDisp, calcs, fileHandler);
        // Boo to generics.
        assertThat((FileWriter) proc.getAccWriter(), isA(FileWriter.class));
        proc.setAccWriter(writer);
        // Creates a file reader, so this needs to be a real file
        final File tgtFile = File.createTempFile("hartree",
                MediaType.LOG.getPrimaryExtension());
        try {
            final DefaultBaseResult result = new DefaultBaseResult(SRC_NAME);
            when(theParser.load(eq(tgtFile.getName()), any(FileReader.class)))
                    .thenReturn(result);
            proc.display(tgtFile);
            proc.finish();
            verify(theDisp).write(writer, result);
            verify(theDisp).finish(writer);
            verify(writer).close();
            assertTrue(outFile.exists());
        } finally {
            tgtFile.delete();
            outFile.delete();
        }
    }

    /**
     * Tests calling display with no target dir.
     * 
     * @throws IOException
     *             If there's a problem closing the mock writer?
     */
    @Test
    public void testDisplayNoOutDir() throws IOException {
        final Loader<BaseResult> theParser = mock(Loader.class);
        final Display<BaseResult> theDisp = mock(Display.class);
        final List<Calculation> calcs = new ArrayList<Calculation>();
        final InputFileHandler fileHandler = mock(InputFileHandler.class);
        final File tmpDir = new File(CommonConstants.TMPDIR);
        final File outFile = new File(tmpDir, OUT_FILE_NAME);
        assertFalse(outFile.exists());
        // when(fileHandler.getOutDir()).thenReturn(tmpDir);
        when(theDisp.getMediaType()).thenReturn(MediaType.LOG);
        final Writer writer = mock(Writer.class);
        final AccumulatingFileProcessor<BaseResult> proc = new AccumulatingFileProcessor<BaseResult>(
                HandlingType.SNAPSHOT, theParser, theDisp, calcs, fileHandler);
        // Boo to generics.
        assertThat((OutputStreamWriter) proc.getAccWriter(),
                isA(OutputStreamWriter.class));
        proc.setAccWriter(writer);
        // Creates a file reader, so this needs to be a real file
        final File tgtFile = File.createTempFile("hartree",
                MediaType.LOG.getPrimaryExtension());
        try {
            final DefaultBaseResult result = new DefaultBaseResult(SRC_NAME);
            when(theParser.load(eq(tgtFile.getName()), any(FileReader.class)))
                    .thenReturn(result);
            proc.display(tgtFile);
            proc.finish();
            verify(theDisp).write(writer, result);
            verify(theDisp).finish(writer);
            verify(writer).close();
            assertFalse(outFile.exists());
        } finally {
            tgtFile.delete();
        }
    }

    /**
     * Tests calling display with a missing file.
     * 
     * @throws IOException
     *             If there's a problem closing the mock writer?
     */
    @Test(expected = EnvironmentException.class)
    public void testDisplayNotFound() throws IOException {
        final Loader<BaseResult> theParser = mock(Loader.class);
        final Display<BaseResult> theDisp = mock(Display.class);
        final List<Calculation> calcs = new ArrayList<Calculation>();
        final InputFileHandler fileHandler = mock(InputFileHandler.class);
        final AccumulatingFileProcessor<BaseResult> proc = new AccumulatingFileProcessor<BaseResult>(
                HandlingType.SNAPSHOT, theParser, theDisp, calcs, fileHandler);
        final File tgtFile = new File("this does not exist");
        proc.display(tgtFile);
    }

    /**
     * Tests calling display with no target dir.
     * 
     * @throws IOException
     *             If there's a problem closing the mock writer?
     */
    @Test
    public void testDisplayWithCalc() throws IOException {
        final Loader<BaseResult> theParser = mock(Loader.class);
        final Display<BaseResult> theDisp = mock(Display.class);
        final Calculation calc = mock(Calculation.class);
        final List<Calculation> calcs = Arrays.asList(calc);
        final InputFileHandler fileHandler = mock(InputFileHandler.class);
        final File tmpDir = new File(CommonConstants.TMPDIR);
        final File outFile = new File(tmpDir, OUT_FILE_NAME);
        assertFalse(outFile.exists());
        // when(fileHandler.getOutDir()).thenReturn(tmpDir);
        when(theDisp.getMediaType()).thenReturn(MediaType.LOG);
        final Writer writer = mock(Writer.class);
        final AccumulatingFileProcessor<BaseResult> proc = new AccumulatingFileProcessor<BaseResult>(
                HandlingType.SNAPSHOT, theParser, theDisp, calcs, fileHandler);
        // Boo to generics.
        assertThat((OutputStreamWriter) proc.getAccWriter(),
                isA(OutputStreamWriter.class));
        proc.setAccWriter(writer);
        // Creates a file reader, so this needs to be a real file
        final File tgtFile = File.createTempFile("hartree",
                MediaType.LOG.getPrimaryExtension());
        try {
            final DefaultBaseResult result = new DefaultBaseResult(SRC_NAME);
            when(theParser.load(eq(tgtFile.getName()), any(FileReader.class)))
                    .thenReturn(result);
            final DefaultBaseResult changedResult = new DefaultBaseResult(
                    CHG_SRC_NAME);
            when(calc.calculate(result)).thenReturn(changedResult);
            proc.display(tgtFile);
            proc.finish();
            verify(theDisp).write(writer, changedResult);
            verify(theDisp).finish(writer);
            verify(writer).close();
            assertFalse(outFile.exists());
        } finally {
            tgtFile.delete();
        }
    }
}
