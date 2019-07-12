package org.cmayes.hartree;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class HelpMessageException extends CmdLineException {
    public HelpMessageException(CmdLineParser parser, String message) {
        super(parser, message);
    }

    public HelpMessageException(CmdLineParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public HelpMessageException(CmdLineParser parser, Throwable cause) {
        super(parser, cause);
    }
}
