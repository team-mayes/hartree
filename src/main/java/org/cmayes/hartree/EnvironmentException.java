package org.cmayes.hartree;

public class EnvironmentException extends RuntimeException {

    /** UID. */
    private static final long serialVersionUID = 6873324155349921602L;

    /**
     * {@inheritDoc}
     */
    public EnvironmentException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public EnvironmentException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public EnvironmentException(String mes) {
        super(mes);
    }

    /**
     * {@inheritDoc}
     */
    public EnvironmentException(String mes, Throwable cause) {
        super(mes, cause);
    }
}
