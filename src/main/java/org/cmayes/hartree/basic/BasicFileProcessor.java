package org.cmayes.hartree.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.cmayes.hartree.parser.Parser;

import com.cmayes.common.exception.EnvironmentException;

/**
 * A basic linear file processor.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type that is returned from file processing.
 */
public class BasicFileProcessor<T> {
    private final Parser<T> parser;

    /**
     * Creates a processor that will use the given parser.
     * 
     * @param theParser
     *            The parser to use.
     */
    public BasicFileProcessor(final Parser<T> theParser) {
        parser = asNotNull(theParser, "Parser is null");
    }

    /**
     * Processes a single file.
     * 
     * @param processMe
     *            The file to process.
     * @return The results of the processing.
     */
    public T single(final File processMe) {
        try {
            return parser.parse(new FileReader(processMe));
        } catch (final FileNotFoundException e) {
            throw new EnvironmentException(
                    "File not found while creating reader", e);
        }
    }
}
