package me.wordwizard.backend.security.auth.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class JsonSupport {
    protected final ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> T getDataFromJsonFileSource(String file, Class<T> tClass) throws IOException {
        var sourcePath = String.format("/%s%s%s", getJsonDir(), File.separator, file);
        logger.warn("Reading from {}", sourcePath);
        try (InputStream is = getClass().getResourceAsStream(sourcePath)) {
            return objectMapper.readValue(is, tClass);
        }
    }

    public <T> T getDataFromJsonFileSource(String file, TypeReference<T> reference) throws IOException {
        var sourcePath = String.format("/%s%s%s", getJsonDir(), File.separator, file);
        logger.warn("Reading from {}", sourcePath);
        try (InputStream is = getClass().getResourceAsStream(sourcePath)) {
            return objectMapper.readValue(is, reference);
        }
    }

    public String getJsonAsStringFromFileSource(String file) throws IOException {
        var sourcePath = String.format("/%s%s%s", getJsonDir(), File.separator, file);
        logger.warn("Reading from {}", sourcePath);
        try (InputStream is = getClass().getResourceAsStream(sourcePath)) {
            return new String(is.readAllBytes(), Charset.forName("UTF-8"));
        }
    }


    public String dataToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public String getJsonDir() {
        return this.getClass().getSimpleName();
    }
}
