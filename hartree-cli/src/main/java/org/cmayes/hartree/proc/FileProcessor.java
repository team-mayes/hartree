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
     */
    void display(final File processMe);

    /**
     * Displays all files in the given directory and its children. If the input
     * is a file, the data is processed singly.
     * 
     * @param processDir
     *            The directory (or file) to process.
     * @see #display(File)
     */
    void displayAll(final File processDir);

    /**
     * Indicate that all processing is done, allowing for output finishing, I/O
     * cleanup, etc.
     */
    void finish();
}