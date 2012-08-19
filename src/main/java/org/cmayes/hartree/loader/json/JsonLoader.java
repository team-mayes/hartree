package org.cmayes.hartree.loader.json;

import java.io.IOException;
import java.io.Reader;

import org.cmayes.hartree.loader.Loader;

import com.cmayes.common.exception.EnvironmentException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class JsonLoader<T> implements Loader<T> {
    private final ObjectReader classReader;

    public JsonLoader(Class<T> targetClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        classReader = objectMapper.reader(targetClass);
    }

    @Override
    public T load(String srcName, Reader reader) {
        try {
            return classReader.readValue(reader);
        } catch (JsonGenerationException e) {
            throw new EnvironmentException("Problems reading JSON ", e);
        } catch (IOException e) {
            throw new EnvironmentException("Problems reading JSON ", e);
        }
    }
}
