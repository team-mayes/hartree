package org.cmayes.hartree.disp;

import java.io.Writer;

/**
 * Defines a display writer.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type of the value to display.
 */
public interface Display<T> {
    /**
     * Writes a single value to the given writer.
     * 
     * @param writer
     *            The writer to write to.
     * @param valToDisp
     *            The value to display.
     */
    void write(Writer writer, T valToDisp);

    /**
     * Writes a single value with header info to the given writer.
     * 
     * @param writer
     *            The writer to write to.
     * @param headerInfo
     *            The header info to display (Null for no header).
     * @param valToDisp
     *            The value to display.
     */
    void write(final Writer writer, final String headerInfo, final T valToDisp);
}
