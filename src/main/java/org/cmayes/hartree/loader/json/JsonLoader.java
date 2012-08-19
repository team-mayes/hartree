package org.cmayes.hartree.loader.json;

import java.io.IOException;
import java.io.Reader;

import org.cmayes.hartree.loader.Loader;

import com.cmayes.common.exception.EnvironmentException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * Loads data from a JSON document into an instance of the given target class.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The class to target.
 */
public class JsonLoader<T> implements Loader<T> {
    private final ObjectReader classReader;

    /**
     * Creates a loader for the given class.
     * 
     * @param targetClass
     *            The class to instantiate and fill.
     */
    public JsonLoader(final Class<T> targetClass) {
        final ObjectMapper objectMapper = new ObjectMapper();
        classReader = objectMapper.reader(targetClass);
    }

    @Override
    public T load(final String srcName, final Reader reader) {
        try {
            return classReader.readValue(reader);
        } catch (final JsonGenerationException e) {
            throw new EnvironmentException("Problems reading JSON ", e);
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading JSON ", e);
        }
    }
}
