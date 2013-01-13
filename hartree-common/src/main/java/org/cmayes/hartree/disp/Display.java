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
     * Resets the state of the display and writes any closing data to the
     * writer.
     * 
     * @param writer
     *            The writer to be closed.
     */
    void finish(Writer writer);

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
     * Returns the media type of the generated display.
     * 
     * @return The media type of the generated display.
     */
    MediaType getMediaType();
    
    /**
     * Returns whether this display expects multiple objects to be written.
     * 
     * @return Whether this display expects multiple objects to be written.
     */
    boolean isWriteMulti();

    /**
     * Sets whether this display expects multiple objects to be written.
     * 
     * @param writeMulti
     *            Whether to expect multiple objects to be written.
     */
    void setWriteMulti(boolean writeMulti);
}
