package org.cmayes.hartree.jython;

import static com.cmayes.common.CommonConstants.NL;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configures and runs a Jython interpreter against the given script.
 * 
 * @author cmayes
 */
public class Runner {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    /** Receives leftover command line parameters. */

    /**
     * @param args
     *            The CLI arguments.
     */
    public static void main(final String... args) {
        try {
            new Runner().doMain(args);
        } catch (final Exception e) {
            LOGGER.error("Top-level exception caught", e);
            System.err.printf(
                    "Error: %s(%s). Check error logs for more detail.%s", e
                            .getClass().getSimpleName(), e.getMessage(), NL);
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
        if (args.length == 0) {
            throw new IllegalArgumentException("Script location required.");
        }

        final String script = args[0];

        initInterpreter(args);
        // instantiate- note that it is AFTER initialize
        try {
            new PythonInterpreter().execfile(script);
        } catch (final PyException e) {
            LOGGER.error("Problems running script " + script, e);
            System.err.printf("Error %s: '%s' while running script %s: %s",
                    e.type, e.value, script, NL);
        }
    }

    /**
     * Initializes the Jython interpreter (initially from {@link http
     * ://techbus.safaribooksonline.com/0735711119/ch09lev1sec1}).
     * 
     * @param argv
     *            The main args.
     */
    protected void initInterpreter(final String[] argv) {
        // Get preProperties postProperties, and System properties
        final Properties postProps = new Properties();
        final Properties sysProps = System.getProperties();

        // put System properties (those set with -D) in postProps
        final Enumeration<?> e = sysProps.propertyNames();
        while (e.hasMoreElements()) {
            final String name = (String) e.nextElement();
            if (name.startsWith("python.")) {
                postProps.put(name, System.getProperty(name));
            }
        }

        PythonInterpreter.initialize(sysProps, postProps, argv);
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
        outs.printf("java %s [options...] script%s", Runner.class.getName(), NL);
        outs.println("Available options:");
        e.getParser().printUsage(outs);
        outs.println();
    }
}
