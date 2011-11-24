package org.cmayes.hartree.parser;

/**
 * Thrown for parsing errors.
 * 
 * @author cmayes
 */
public class ParseException extends RuntimeException {

    /** UID. */
    private static final long serialVersionUID = 6873324155349921602L;

    /**
     * Zero-arg constructor.
     */
    public ParseException() {
        super();
    }

    /**
     * Wrapped cause.
     * 
     * @param cause
     *            The wrapped cause.
     */
    public ParseException(final Throwable cause) {
        super(cause);
    }

    /**
     * Exception message.
     * 
     * @param mes
     *            Exception message.
     */
    public ParseException(final String mes) {
        super(mes);
    }

    /**
     * Cause and message.
     * 
     * @param mes
     *            Exception message.
     * @param cause
     *            The wrapped cause.
     */
    public ParseException(final String mes, final Throwable cause) {
        super(mes, cause);
    }
}
