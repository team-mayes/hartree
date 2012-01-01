package org.cmayes.hartree.proc.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.proc.FileProcessor;

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
    private final Loader<T> parser;
    private final Display<T> displayer;

    /**
     * Creates a processor that will use the given parser.
     * 
     * @param theParser
     *            The parser to use.
     * @param theDisp
     *            The display to use.
     */
    public BasicFileProcessor(final Loader<T> theParser,
            final Display<T> theDisp) {
        this.parser = asNotNull(theParser, "Parser is null");
        this.displayer = asNotNull(theDisp, "Display is null");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.FileProcessor#display(java.io.File)
     */
    public void display(final File processMe) {
        try {
            displayer.write(
                    new OutputStreamWriter(System.out),
                    String.format("Normal mode summary for file %s",
                            processMe.getName()),
                    parser.load(new FileReader(processMe)));
        } catch (final FileNotFoundException e) {
            throw new EnvironmentException(
                    "File not found while creating reader", e);
        }
    }
}
