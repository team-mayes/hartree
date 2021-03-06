package org.cmayes.hartree.proc.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.cmayes.hartree.HandlingType;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.proc.FileProcessor;
import org.cmayes.hartree.proc.InputFileHandler;
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
public class BasicFileProcessor<T> implements FileProcessor<T> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Loader<T> parser;
    private final Display<T> displayer;
    private final List<Calculation> calculations;
    private final HandlingType handlingType;
    private final InputFileHandler inputFileHandler;

    
    
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
     * @param fileHandler
     *            The handler to use for files.
     */
    public BasicFileProcessor(final HandlingType handType,
            final Loader<T> theParser, final Display<T> theDisp,
            final List<Calculation> calcs, final InputFileHandler fileHandler) {
        this.handlingType = asNotNull(handType, "Handler type is null");
        this.parser = asNotNull(theParser, "Parser is null");
        this.displayer = asNotNull(theDisp, "Display is null");
        this.calculations = asNotNull(calcs, "Calculations cannot be null.");
        this.inputFileHandler = asNotNull(fileHandler,
                "Input file handler is null");
    }

    /**
     * {@inheritDoc}
     * 
     */
    public void displayAll(final List<File> processFiles) {
        for (File targetFile: processFiles) {
            Writer writer = null;
            FileReader fileReader = null;
            try {
                writer = inputFileHandler.createOutWriter(targetFile, handlingType
                        .getCommandName(), displayer.getMediaType()
                        .getPrimaryExtension());
                fileReader = new FileReader(targetFile);
                final T procResult = applyCalcs(parser.load(targetFile.getName(),
                        fileReader));
                displayer.write(writer, procResult);
            } catch (final FileNotFoundException e) {
                throw new EnvironmentException(
                        "File not found while creating reader", e);
            } finally {
                displayer.finish(writer);
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (final IOException e) {
                        logger.warn("Problems closing writer: " + e.getMessage());
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (final IOException e) {
                        logger.warn("Problems closing reader: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Applies the set calculations to the files.
     * 
     * @param rawResult
     *            The result to process.
     * @return The result of applying the calculations.
     */
    @SuppressWarnings("unchecked")
    private T applyCalcs(final T rawResult) {
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
     * @see #displayAll(List) 
     * @see org.cmayes.hartree.proc.FileProcessor#displayDir(java.io.File)
     */
    @Override
    public void displayDir(final File processDir) {
        inputFileHandler.handle(processDir, this);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.FileProcessor#finish()
     */
    @Override
    public void finish() {

    }
}
