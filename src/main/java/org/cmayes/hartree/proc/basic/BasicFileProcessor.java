package org.cmayes.hartree.proc.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    /**
     * Creates a processor that will use the given parser.
     * 
     * @param theParser
     *            The parser to use.
     */
    public BasicFileProcessor(final Loader<T> theParser) {
        parser = asNotNull(theParser, "Parser is null");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.FileProcessor#single(java.io.File)
     */
    public T single(final File processMe) {
        try {
            return parser.load(new FileReader(processMe));
        } catch (final FileNotFoundException e) {
            throw new EnvironmentException(
                    "File not found while creating reader", e);
        }
    }
}
