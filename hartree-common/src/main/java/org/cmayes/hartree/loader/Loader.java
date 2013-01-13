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
     * @param srcName
     *            The name identifying the source (file name, URL, etc.).
     * @param reader
     *            The reader to process.
     * 
     * @return An instance of the handled result type filled with data from the
     *         reader.
     * @throws EnvironmentException
     *             If there's a problem processing the reader.
     * @throws ParseException
     *             If there is a problem parsing data from the reader.
     */
    T load(String srcName, Reader reader);
}
