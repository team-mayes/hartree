package org.cmayes.hartree.parser;

public class ParseException extends RuntimeException {

    /** UID. */
    private static final long serialVersionUID = 6873324155349921602L;

    /**
     * {@inheritDoc}
     */
    public ParseException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ParseException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public ParseException(String mes) {
        super(mes);
    }

    /**
     * {@inheritDoc}
     */
    public ParseException(String mes, Throwable cause) {
        super(mes, cause);
    }
}
