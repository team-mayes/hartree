package org.cmayes.hartree.disp;

import java.io.Writer;

import com.cmayes.common.MediaType;

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

    /**
     * Returns the media type of the generated display.
     * 
     * @return The media type of the generated display.
     */
    MediaType getMediaType();
}
