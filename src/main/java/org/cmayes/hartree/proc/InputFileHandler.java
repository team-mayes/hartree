package org.cmayes.hartree.proc;

import java.io.File;
import java.io.Writer;


/**
 * Handles the bulk traversal starting with the given file or directory. Each
 * encountered file is then displayed using the given FileProcessor.
 * 
 * @author cmayes
 */
public interface InputFileHandler {
    /**
     * Handles all files in the given directory and its children. If the input
     * is a file, the data is processed singly.
     * 
     * @param processDir
     *            The directory (or file) to process.
     * @see FileProcessor#display(File)
     */
    void handle(final File processDir, FileProcessor<?> fileProcessor);

    /**
     * Creates a target writer for the given input file.
     * 
     * @param inFile
     *            The filename to process.
     * @param cmdName TODO
     * @param outExt TODO
     * @return A target writer for the given input file.
     */
    Writer createOutWriter(File inFile, String cmdName, String outExt);
}