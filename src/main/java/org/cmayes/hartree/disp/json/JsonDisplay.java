package org.cmayes.hartree.disp.json;

import java.io.IOException;
import java.io.Writer;

import org.cmayes.hartree.disp.Display;

import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDisplay implements Display<Object> {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#finish(java.io.Writer)
     */
    @Override
    public void finish(Writer writer) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(Writer writer, Object valToDisp) {
        try {
            objectMapper.writeValue(writer, valToDisp);
        } catch (JsonGenerationException e) {
            throw new EnvironmentException("Problems writing JSON for value "
                    + valToDisp, e);
        } catch (JsonMappingException e) {
            throw new EnvironmentException("Problems writing JSON for value "
                    + valToDisp, e);
        } catch (IOException e) {
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

}
