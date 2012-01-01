package org.cmayes.hartree;

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.disp.txt.NormalModeTextDisplay;
import org.cmayes.hartree.loader.gaussian.NormalModeLoader;
import org.cmayes.hartree.model.NormalModeCalculation;
import org.cmayes.hartree.proc.basic.BasicFileProcessor;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main entry point for running Hartree CLI functions.
 * 
 * @author cmayes
 */
public class Main {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    /** Receives leftover command line parameters. */
    @Argument
    private List<String> arguments = new ArrayList<String>();
    private File file;
    private File directory;

    /**
     * Returns the specified file location.
     * 
     * @return The specified file location.
     */
    File getFile() {
        return file;
    }

    /**
     * Returns the specified directory location.
     * 
     * @return The specified directory location.
     */
    File getDirectory() {
        return directory;
    }

    /**
     * Sets the file if it is readable.
     * 
     * @param theFile
     *            The file to read.
     * @throws IllegalArgumentException
     *             If the file is not readable.
     */
    @Option(aliases = { "-f" }, name = "--file", usage = "The file to process")
    public void setFile(final File theFile) {
        if (theFile.canRead()) {
            this.file = theFile;
        } else {
            throw new IllegalArgumentException(String.format(
                    "File %s is not readable", theFile.getAbsolutePath()));
        }
    }

    /**
     * Sets the directory if it is a readable directory.
     * 
     * @param dir
     *            The base directory to start from.
     */
    @Option(aliases = { "-d" }, name = "--directory", usage = "The base directory of the files to process")
    public void setDirectory(final File dir) {
        if (dir.canRead() && dir.isDirectory()) {
            this.directory = dir;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Directory %s is not readable or is not a directory",
                    dir.getAbsolutePath()));
        }
    }

    /**
     * @param args
     *            The CLI arguments.
     */
    public static void main(final String... args) {
        try {
            new Main().doMain(args);
        } catch (final CmdLineException e) {
            System.err.println(e.getMessage());
            System.err
                    .println("java org.cmayes.hartree.Main [options...] arguments...");
            e.getParser().printUsage(System.err);
            System.err.println();
            System.err.println("  Example: java org.cmayes.hartree.Main "
                    + e.getParser().printExample(ALL));
            System.exit(1);
        } catch (final Exception e) {
            LOGGER.error("Top-level exception caught", e);
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }

    /**
     * Performs argument processing and command dispatching.
     * 
     * @param args
     *            The arguments to process.
     * @throws CmdLineException
     *             When there are problems processing the command line.
     */
    public void doMain(final String... args) throws CmdLineException {
        final CmdLineParser parser = new CmdLineParser(this);

        parser.parseArgument(args);

        final BasicFileProcessor<NormalModeCalculation> proc = new BasicFileProcessor<NormalModeCalculation>(
                new NormalModeLoader(), new NormalModeTextDisplay());
        if (file != null) {
            proc.display(file);
        }
        // after parsing arguments, you should check
        // if enough arguments are given.
        // if (arguments.isEmpty()) {
        // throw new CmdLineException(parser, "No argument is given");
        // }
    }
}
