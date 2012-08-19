package org.cmayes.hartree.disp.json;

import java.io.IOException;
import java.io.Writer;

import org.cmayes.hartree.disp.Display;

import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Displays the given object as JSON.
 * 
 * @author cmayes
 */
public class JsonDisplay implements Display<Object> {
    private final ObjectMapper objectMapper;

    private boolean first = true;

    private volatile boolean writeMulti = false;

    /**
     * Zero-arg constructor.
     */
    public JsonDisplay() {
        objectMapper = new ObjectMapper();
        // We close our writers in the FileProcessors.
        objectMapper.getJsonFactory().disable(
                JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        // Enable pretty printing.
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#finish(java.io.Writer)
     */
    @Override
    public void finish(final Writer writer) {
        if (writeMulti) {
            try {
                writer.write("]");
            } catch (final IOException e) {
                throw new EnvironmentException(
                        "Problems writing JSON closing value ", e);
            }
        }
        first = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final Object valToDisp) {
        try {
            if (first) {
                writer.write("[");
                first = false;
            } else {
                if (writeMulti) {
                    writer.write(",");
                }
            }
            objectMapper.writeValue(writer, valToDisp);
        } catch (final JsonGenerationException e) {
            throw new EnvironmentException("Problems writing JSON for value "
                    + valToDisp, e);
        } catch (final JsonMappingException e) {
            throw new EnvironmentException("Problems writing JSON for value "
                    + valToDisp, e);
        } catch (final IOException e) {
            throw new EnvironmentException("Problems writing JSON for value "
                    + valToDisp, e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#getMediaType()
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.JSON;
    }

    /**
     * Returns whether this display expects multiple objects to be written.
     * 
     * @return Whether this display expects multiple objects to be written.
     */
    public boolean isWriteMulti() {
        return writeMulti;
    }

    /**
     * Sets whether this display expects multiple objects to be written.
     * 
     * @param multi
     *            Whether to expect multiple objects to be written.
     */
    public void setWriteMulti(final boolean multi) {
        this.writeMulti = multi;
    }
}
