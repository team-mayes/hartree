package org.cmayes.hartree.proc;

import java.io.File;

/**
 * Defines a file processor that produces instances of type T.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type of data produced by the file processor.
 */
public interface FileProcessor<T> {

    /**
     * Process a single file.
     * 
     * @param processMe
     *            The file to process.
     * @return The results of processing the file.
     */
    T single(final File processMe);
}