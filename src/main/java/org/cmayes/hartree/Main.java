package org.cmayes.hartree;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;
import static com.cmayes.common.CommonConstants.NL;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.disp.txt.NormalModeTextDisplay;
import org.cmayes.hartree.loader.Loader;
import org.cmayes.hartree.loader.gaussian.CalcResultLoader;
import org.cmayes.hartree.loader.gaussian.NormalModeLoader;
import org.cmayes.hartree.proc.FileProcessor;
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
    /** Receives leftover command line parameters. */
    @Argument
    private final List<String> arguments = new ArrayList<String>();
    private File file;
    private File directory;
    private File outDir;
    private Class<T> targetType;
    private MediaType targetMedia = MediaType.TEXT;

    static {
        HAND_TYPE_MAP.put(HandlingType.THERM.getValueClass(),
                new CalcResultLoader());
        HAND_TYPE_MAP.put(HandlingType.NORMAL_MODE.getValueClass(),
                new NormalModeLoader());
        DISP_TYPE_TBL.put(HandlingType.NORMAL_MODE.getValueClass(),
                MediaType.TEXT, new NormalModeTextDisplay());
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
     * Sets the out directory. Attempts to create the directory if it does not
     * exist.
     * 
     * @param dir
     *            The base directory to start from.
     */
    @Option(aliases = { "-o" }, name = "--outdir", usage = "The output directory for result files")
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
            System.err.println(e.getMessage());
            System.err.printf("java %s [options...] (%s)%s",
                    Main.class.getName(), getHandlerNames(), NL);
            e.getParser().printUsage(System.err);
            System.err.println();
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

        final HandlingType hType = HandlingType
                .valueOfCommand(arguments.get(0));

        targetType = (Class<T>) hType.getValueClass();

        final FileProcessor<T> proc = new BasicFileProcessor<T>(hType,
                getLoader(), getDisplay(), outDir);
        if (file != null) {
            proc.display(file);
        } else if (directory != null) {
            proc.displayAll(directory);
        } else {
            throw new CmdLineException(parser, "Unknown argument "
                    + arguments.get(0));
        }
    }

    /**
     * Returns the loader for the target type.
     * 
     * @return The loader for the target type.
     */
    @SuppressWarnings("unchecked")
    private Loader<T> getLoader() {
        return (Loader<T>) asNotNull(HAND_TYPE_MAP.get(targetType),
                "No loader for type " + targetType.getName());
    }

    /**
     * Return the display for the target type and media.
     * 
     * @return The display for the target type and media.
     */
    @SuppressWarnings("unchecked")
    private Display<T> getDisplay() {
        return (Display<T>) asNotNull(
                DISP_TYPE_TBL.get(targetType, targetMedia), String.format(
                        "No display for media %s on type %s", targetMedia,
                        targetType.getName()));
    }

    /**
     * Returns the names of the handler commands.
     * 
     * @return The names of the handler commands.
     */
    private static String getHandlerNames() {
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
}
