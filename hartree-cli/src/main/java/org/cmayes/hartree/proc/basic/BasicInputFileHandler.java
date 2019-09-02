package org.cmayes.hartree.proc.basic;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.cmayes.hartree.proc.FileProcessor;
import org.cmayes.hartree.proc.InputFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.file.AcceptAllFilter;
import com.cmayes.common.file.ExtensionFilter;

/**
 * Handles file type filtering and output file naming.
 * 
 * @author cmayes
 */
public class BasicInputFileHandler implements InputFileHandler {
    private final FilenameFilter fileFilter;
    private final File inDir;
    private final File outDir;
    private PrintStream sysOut = System.out;

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Zero-arg constructor that uses an accept all filter.
     */
    public BasicInputFileHandler() {
        this.fileFilter = new AcceptAllFilter();
        this.inDir = null;
        this.outDir = null;
    }

    /**
     * Creates a handler using the given filter, source, and destination.
     * 
     * @param filter
     *            The filter for the file names.
     * @param in
     *            The source.
     * @param out
     *            The target.
     */
    public BasicInputFileHandler(final FilenameFilter filter, final File in,
            final File out) {
        this.fileFilter = asNotNull(filter, "Filter is null");
        this.inDir = in;
        this.outDir = out;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.InputFileHandler#handle(java.io.File,
     *      org.cmayes.hartree.proc.FileProcessor)
     */
    @Override
    public void handle(final File processDir,
            final FileProcessor<?> fileProcessor) {
        if (processDir.isDirectory()) {
            final String[] children = processDir.list(fileFilter);
            Arrays.sort(children);
            for (int i = 0; i < children.length; i++) {
                handle(new File(processDir, children[i]), fileProcessor);
            }
        } else {
            fileProcessor.display(processDir);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.proc.InputFileHandler#createOutWriter(File,
     *      String, String)
     */
    @Override
    public Writer createOutWriter(final File inFile, final String cmdName,
            final String outExt) {
        if (MediaType.RDBMS.getAllExtensions().contains(outExt)) {
            logger.debug(String
                    .format("Extension '%s' matches an RDBMS extension; returning null for out writer.",
                            outExt));
            return null;
        }

        if (outDir == null) {
            return new OutputStreamWriter(sysOut);
        }

        final File tgtOutDir = processTgtOutDir(inFile);
        if (tgtOutDir.exists() || !tgtOutDir.mkdirs()) {
            logger.warn("Could not create out dir " + tgtOutDir);
        }

        final File outFile = new File(tgtOutDir, String.format("%s-%s.%s",
                processFileName(inFile.getName()), cmdName, outExt));

        try {
            if (outFile.exists() || !outFile.createNewFile()) {
                logger.warn("Could not create out file "
                        + outFile.getAbsolutePath());
            }
            return new FileWriter(outFile);
        } catch (final IOException e) {
            throw new EnvironmentException("Problems creating out file "
                    + outFile, e);
        }
    }

    /**
     * Strips off the filename's extension if it is one of the configured input
     * extensions.
     * 
     * @param inFile
     *            The input file to process.
     * @return The base name of the file minus a known extension.
     */
    private String processFileName(final String inFile) {
        if (fileFilter instanceof ExtensionFilter) {
            for (String ext : ((ExtensionFilter) fileFilter).getExtList()) {
                if (inFile.endsWith(ext)) {
                    return inFile.substring(0, inFile.length() - ext.length());
                }
            }
        }
        return inFile;
    }

    /**
     * Produces (but does not create) a base output directory based on the input
     * file's location relative to the input directory.
     * 
     * @param inFile
     *            The file to process.
     * @return The base output directory.
     */
    private File processTgtOutDir(final File inFile) {
        if (inDir == null) {
            return outDir;
        }
        final String parentInDir = asNotNull(inFile, "inFile is null")
                .getAbsoluteFile().getParent();
        if (parentInDir == null) {
            return outDir;
        }

        if (parentInDir.startsWith(inDir.getAbsolutePath())) {
            return new File(outDir, parentInDir.replaceFirst(
                    Pattern.quote(inDir.getAbsolutePath()), ""));
        }
        return outDir;
    }

    /**
     * Sets the stream used for system.out (mainly for testing).
     * 
     * @param out
     *            The stream to use for testing.
     */
    public void setSysOut(final PrintStream out) {
        this.sysOut = out;
    }

    /**
     * Returns the configured input directory.
     * 
     * @return the inDir
     */
    public File getInDir() {
        return inDir;
    }

    /**
     * Returns the configured output directory.
     * 
     * @return the outDir
     */
    public File getOutDir() {
        return outDir;
    }
}
