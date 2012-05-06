package org.cmayes.hartree;

import static com.cmayes.common.CommonConstants.NL;
import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.disp.csv.CalculationSnapshotCsvDisplay;
import org.cmayes.hartree.disp.txt.NormalModeTextDisplay;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.loader.gaussian.CalcResultLoader;
import org.cmayes.hartree.loader.gaussian.NormalModeLoader;
import org.cmayes.hartree.loader.gaussian.SnapshotLoader;
import org.cmayes.hartree.proc.FileProcessor;
import org.cmayes.hartree.proc.basic.AccumulatingFileProcessor;
import org.cmayes.hartree.proc.basic.BasicFileProcessor;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.MediaType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * This is the main entry point for running Hartree CLI functions.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The target data type.
 */
public class Main<T> {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Map<Class<?>, Loader<?>> HAND_TYPE_MAP = new HashMap<Class<?>, Loader<?>>();
    private static final Table<Class<?>, MediaType, Display<?>> DISP_TYPE_TBL = HashBasedTable
            .create();
    private static final Map<Class<?>, MediaType> DEF_MEDIA = new HashMap<Class<?>, MediaType>();
    /** Receives leftover command line parameters. */
    @Argument
    private final List<String> arguments = new ArrayList<String>();
    private File file;
    private File directory;
    private File outDir;
    private Class<T> targetType;
    private MediaType targetMedia = null;
    private FileProcessor<T> testProcessor;

    static {
        // Assign handlers
        HAND_TYPE_MAP.put(HandlingType.THERM.getValueClass(),
                new CalcResultLoader());
        HAND_TYPE_MAP.put(HandlingType.NORMAL_MODE.getValueClass(),
                new NormalModeLoader());
        HAND_TYPE_MAP.put(HandlingType.SNAPSHOT.getValueClass(),
                new SnapshotLoader());
        // Set default media types for value classes.
        DEF_MEDIA.put(HandlingType.NORMAL_MODE.getValueClass(), MediaType.TEXT);
        DEF_MEDIA.put(HandlingType.SNAPSHOT.getValueClass(), MediaType.CSV);
        // Establish displays for a combo of value object and media type
        DISP_TYPE_TBL.put(HandlingType.NORMAL_MODE.getValueClass(),
                MediaType.TEXT, new NormalModeTextDisplay());
        DISP_TYPE_TBL.put(HandlingType.SNAPSHOT.getValueClass(), MediaType.CSV,
                new CalculationSnapshotCsvDisplay());
    }

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
    @Option(metaVar = "INFILE", aliases = { "-f" }, name = "--file",
            usage = "The file to process")
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
    @Option(metaVar = "INDIR", aliases = { "-d" }, name = "--directory",
            usage = "The base directory of the files to process")
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
     * @return the outDir
     */
    public File getOutDir() {
        return outDir;
    }

    /**
     * Sets the out directory. Attempts to create the directory if it does not
     * exist.
     * 
     * @param dir
     *            The base directory to start from.
     */
    @Option(metaVar = "OUTDIR", aliases = { "-o" }, name = "--outdir",
            usage = "The output directory for result files")
    public void setOutDir(final File dir) {
        if (dir.canRead() && dir.isDirectory()) {
            this.outDir = dir;
        } else if (dir.exists()) {
            throw new IllegalArgumentException(String.format(
                    "Directory %s is not readable or is not a directory",
                    dir.getAbsolutePath()));
        } else {
            if (dir.mkdirs()) {
                LOGGER.debug("Created out dir " + dir.getAbsolutePath());
                this.outDir = dir;
            } else {
                throw new IllegalArgumentException("Could not create out dir "
                        + dir.getAbsolutePath());
            }
        }
    }

    /**
     * @param args
     *            The CLI arguments.
     */
    @SuppressWarnings("rawtypes")
    public static void main(final String... args) {
        try {
            new Main().doMain(args);
        } catch (final CmdLineException e) {
            printErrorUsage(System.err, e);
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
    @SuppressWarnings("unchecked")
    public void doMain(final String... args) throws CmdLineException {
        final CmdLineParser parser = new CmdLineParser(this);

        parser.parseArgument(args);

        if (arguments.isEmpty()) {
            throw new CmdLineException(parser, String.format(
                    "Argument not one of (%s)", getHandlerNames()));
        }

        HandlingType hType;
        try {
            hType = HandlingType.valueOfCommand(arguments.get(0));
        } catch (final IllegalArgumentException e) {
            throw new CmdLineException(parser, String.format(
                    "Invalid argument '%s'", arguments.get(0)), e);
        }

        if (HandlingType.TEST.equals(hType)) {
            LOGGER.info("Test mode.  Performing no processing.");
            return;
        }

        targetType = (Class<T>) hType.getValueClass();

        final FileProcessor<T> proc = createProcessor(hType);
        try {
            if (file != null) {
                proc.display(file);
            } else if (directory != null) {
                proc.displayAll(directory);
            } else {
                throw new CmdLineException(parser,
                        "No input file or directory specified.");
            }
        } finally {
            proc.finish();
        }
    }

    /**
     * Returns a new {@link FileProcessor} instance.
     * 
     * @param hType
     *            The type of handler.
     * @return A new FileProcessor instance.
     */
    FileProcessor<T> createProcessor(final HandlingType hType) {
        if (testProcessor != null) {
            return testProcessor;
        }
        if (HandlingType.SNAPSHOT.equals(hType)) {
            return new AccumulatingFileProcessor<T>(hType, getLoader(),
                    getDisplay(), outDir);
        }
        return new BasicFileProcessor<T>(hType, getLoader(), getDisplay(),
                outDir);
    }

    /**
     * Returns the loader for the target type.
     * 
     * @return The loader for the target type.
     */
    @SuppressWarnings("unchecked")
    Loader<T> getLoader() {
        return (Loader<T>) asNotNull(HAND_TYPE_MAP.get(targetType),
                "No loader for type " + targetType.getName());
    }

    /**
     * Return the display for the target type and media.
     * 
     * @return The display for the target type and media.
     */
    @SuppressWarnings("unchecked")
    Display<T> getDisplay() {
        MediaType tgtMediaType = getTargetMediaType();
        return (Display<T>) asNotNull(DISP_TYPE_TBL.get(targetType,
                tgtMediaType), String.format(
                "No display for media %s on type %s", targetMedia,
                targetType.getName()));
    }

    /**
     * Returns either the user's specified target media type or the default
     * media type for the target type.
     * 
     * @return The media type to display.
     * @throws IllegalStateException
     *             If neither targetMedia nor a default mapping for the target
     *             type are set.
     */
    MediaType getTargetMediaType() {
        if (targetMedia != null) {
            return targetMedia;
        }
        MediaType mediaType = DEF_MEDIA.get(targetType);
        if (mediaType == null) {
            throw new IllegalStateException(
                    String.format(
                            "No target media type specified and no default configured for type ''",
                            targetType));
        }
        return mediaType;
    }

    /**
     * Prints a usage message to the given stream using the given exception.
     * 
     * @param outs
     *            The stream to print to.
     * @param e
     *            The exception to evaluate.
     */
    static void printErrorUsage(final PrintStream outs, final CmdLineException e) {
        outs.println(e.getMessage());
        outs.printf("java %s [options...] (%s)%s", Main.class.getName(),
                getHandlerNames(), NL);
        outs.println("Available arguments:");
        printArgs(outs);
        outs.println("Available options:");
        e.getParser().printUsage(outs);
        outs.println();
    }

    /**
     * Prints a formatted description of the available handling types.
     * 
     * @param outs
     *            The {@link PrintStream} to print to.
     */
    private static void printArgs(final PrintStream outs) {
        int maxCmd = 0;
        int maxDesc = 0;
        for (HandlingType handleType : HandlingType.values()) {
            maxCmd = Math.max(maxCmd, handleType.getCommandName().length());
            maxDesc = Math.max(maxDesc, handleType.getDescription().length());
        }
        final String argFmt = "%1$-" + maxCmd + "s : " + "%2$-" + maxDesc + "s"
                + NL;
        for (HandlingType handleType : HandlingType.values()) {
            outs.printf(argFmt, handleType.getCommandName(),
                    handleType.getDescription());
        }
    }

    /**
     * Returns the names of the handler commands.
     * 
     * @return The names of the handler commands.
     */
    static String getHandlerNames() {
        final StringBuffer buf = new StringBuffer();
        boolean isFirst = true;
        for (HandlingType curVal : HandlingType.values()) {
            if (isFirst) {
                isFirst = false;
            } else {
                buf.append(',');
            }
            buf.append(curVal.getCommandName());
        }
        return buf.toString();
    }

    /**
     * Overrides the return for {@link #createProcessor(HandlingType)}. Used for
     * setting a test processor instance.
     * 
     * @param testProc
     *            the testProcessor to set
     */
    public void setTestFileProcessor(final FileProcessor<T> testProc) {
        this.testProcessor = testProc;
    }
}
