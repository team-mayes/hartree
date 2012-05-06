package org.cmayes.hartree.loader;

import java.io.Reader;

import com.cmayes.common.exception.EnvironmentException;

/**
 * Defines a parser that will take input from a file to fill a data structure,
 * returning the result.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type of data structure that will be returned.
 */
public interface Loader<T> {

    /**
     * Parses the data from the reader, returning the handled result type.
     * 
     * @param reader
     *            The reader to process.
     * @return An instance of the handled result type filled with data from the
     *         reader.
     * @throws EnvironmentException
     *             If there's a problem processing the reader.
     * @throws ParseException
     *             If there is a problem parsing data from the reader.
     */
    T load(Reader reader);

    /**
     * Parses the data from the reader, returning the handled result type.
     * 
     * @param reader
     *            The reader to process.
     * @param fileName
     *            The name of the file under process (may be null).
     * @return An instance of the handled result type filled with data from the
     *         reader.
     * @throws EnvironmentException
     *             If there's a problem processing the reader.
     * @throws ParseException
     *             If there is a problem parsing data from the reader.
     */
    T load(Reader reader, String fileName);
}
