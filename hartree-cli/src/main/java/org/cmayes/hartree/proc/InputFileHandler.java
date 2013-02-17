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
     * @param fileProcessor
     *            The processor to use when handling files in the target dir.
     * @see FileProcessor#display(File)
     */
    void handle(final File processDir, FileProcessor<?> fileProcessor);

    /**
     * Creates a target writer for the given input file.
     * 
     * @param inFile
     *            The filename to process.
     * @param cmdName
     *            The name of the Hartree command being processed.
     * @param outExt
     *            The extension for the output file.
     * @return A target writer for the given input file.
     */
    Writer createOutWriter(File inFile, String cmdName, String outExt);

    /**
     * Returns the configured input directory.
     * 
     * @return the inDir
     */
    File getInDir();

    /**
     * Returns the configured output directory.
     * 
     * @return the outDir
     */
    File getOutDir();
}