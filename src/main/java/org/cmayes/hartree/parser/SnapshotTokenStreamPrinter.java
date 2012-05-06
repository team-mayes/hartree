package org.cmayes.hartree.parser;

import static org.kohsuke.args4j.ExampleMode.ALL;

import org.antlr.runtime.Lexer;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotLexer;
import org.cmayes.hartree.parser.gaussian.antlr.SnapshotParser;
import org.kohsuke.args4j.CmdLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotTokenStreamPrinter extends AntlrTokenStreamPrinter {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SnapshotTokenStreamPrinter.class);
    /**
     * Creates an instance of the lexer. Users may override this method to use a
     * different lexer.
     * 
     * @return The lexer to use for token extraction.
     */
    protected Lexer createLexer() {
        return new SnapshotLexer();
    }
    
    /**
     * Creates an instance of the lexer. Users may override this method to use a
     * different lexer.
     * 
     * @return The lexer to use for token extraction.
     */
    protected String[] getTokenNames() {
        return SnapshotParser.tokenNames;
    }
    
    /**
     * TODO: Make abstract main?
     * 
     * @param args
     *            The CLI arguments.
     */
    public static void main(final String... args) {
        try {
            new SnapshotTokenStreamPrinter().doMain(args);
        } catch (final CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println(String.format(
                    "java %s [options...] arguments...",
                    AntlrTokenStreamPrinter.class.getName()));
            e.getParser().printUsage(System.err);
            System.err.println();
            System.err.println(String.format("Example: %s %s",
                    AntlrTokenStreamPrinter.class.getName(), e.getParser()
                            .printExample(ALL)));
            System.exit(1);
        } catch (final Exception e) {
            LOGGER.error("Top-level exception caught", e);
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }
}
