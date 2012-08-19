package org.cmayes.hartree.proc.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.cmayes.hartree.HandlingType;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.model.NamedSource;
import org.cmayes.hartree.proc.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.EnvironmentException;

/**
 * A basic linear file processor.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type that is returned from file processing.
 */
public class AccumulatingFileProcessor<T> implements FileProcessor<T> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Loader<T> parser;
    private final Display<T> displayer;
    private final List<Calculation> calculations;
    private final HandlingType handlingType;
    private File outDir;
    private Writer accWriter;

    /**
     * Creates a processor that will use the given parser and display.
     * 
     * @param handType
     *            The handling type.
     * @param theParser
     *            The parser to use.
     * @param theDisp
     *            The display to use.
     * @param calcs
     *            The calculations to use.
     */
    public AccumulatingFileProcessor(final HandlingType handType,
            final Loader<T> theParser, final Display<T> theDisp,
            final List<Calculation> calcs) {
        this.handlingType = asNotNull(handType, "Handler type is null");
        this.parser = asNotNull(theParser, "Parser is null");
        this.displayer = asNotNull(theDisp, "Display is null");
        this.displayer.setWriteMulti(true);
        accWriter = new OutputStreamWriter(System.out);
        this.calculations = asNotNull(calcs, "Calculations cannot be null.");
    }

    /**
     * Creates a processor that will use the given parser and display and will
     * write into the given output directory if the value is not null.
     * 
     * @param handType
     *            The handling type.
     * @param theParser
     *            The parser to use.
     * @param theDisp
     *            The display to use.
     * @param calcs
     *            The calculations to use.
     * @param out
     *            The output directory (may be null to indicate no output
     *            directory).
     */
    public AccumulatingFileProcessor(final HandlingType handType,
            final Loader<T> theParser, final Display<T> theDisp,
            final List<Calculation> calcs, final File out) {
        this.handlingType = asNotNull(handType, "Handler type is null");
        this.parser = asNotNull(theParser, "Parser is null");
        this.displayer = asNotNull(theDisp, "Display is null");
        this.displayer.setWriteMulti(true);
        this.calculations = asNotNull(calcs, "Calculations cannot be null.");
        this.outDir = out;
        if (outDir == null) {
            accWriter = new OutputStreamWriter(System.out);
        } else {
            final File outFile = new File(String.format("%s%s%s-%s.%s", outDir
                    .getAbsolutePath(), File.separator, "accumulator",
                    handlingType.getCommandName(), displayer.getMediaType()
                            .getPrimaryExtension()));
            try {
                if (!outFile.exists() && (!outFile.createNewFile())) {
                    logger.warn("Could not create out file "
                            + outFile.getAbsolutePath());
                }
                accWriter = new FileWriter(outFile);
            } catch (final IOException e) {
                throw new EnvironmentException("Problems creating out file "
                        + outFile, e);
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.FileProcessor#display(java.io.File)
     */
    public void display(final File processMe) {
        try {
            final T procResult = applyCalcs(parser.load(processMe.getName(),
                    new FileReader(processMe)));
            displayer.write(accWriter, procResult);
        } catch (final FileNotFoundException e) {
            throw new EnvironmentException(
                    "File not found while creating reader", e);
        }
    }

    /**
     * Applies the configured calculations.
     * 
     * @param rawResult
     *            The result to process.
     * @return The processed result.
     */
    @SuppressWarnings("unchecked")
    private T applyCalcs(T rawResult) {
        T procResult = rawResult;
        for (Calculation curCalc : calculations) {
            procResult = (T) curCalc.calculate((Object) procResult);
        }
        return procResult;
    }

    /**
     * Displays all files in the given directory and its children. If the input
     * is a file, the data is processed singly.
     * 
     * @param processDir
     *            The directory (or file) to process.
     * @see #display(File)
     * @see org.cmayes.hartree.proc.FileProcessor#displayAll(java.io.File)
     */
    @Override
    public void displayAll(final File processDir) {
        if (processDir.isDirectory()) {
            final String[] children = processDir.list();
            for (int i = 0; i < children.length; i++) {
                displayAll(new File(processDir, children[i]));
            }
        } else {
            display(processDir);
        }
    }

    /**
     * @return the outDir
     */
    public File getOutDir() {
        return outDir;
    }

    /**
     * @param dir
     *            the outDir to set
     */
    public void setOutDir(final File dir) {
        this.outDir = dir;
    }

    @Override
    public void finish() {
        try {
            displayer.finish(this.accWriter);
            this.accWriter.close();
        } catch (IOException e) {
            logger.warn("Problems closing the accumulator", e);
        }
    }
}
