package me.wordwizard.backend.api.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.TimeZone;

@Configuration
public class MappingConfiguration {
    public static final String TO_API_MAPPER = "toClientMapper";
    public static final String FROM_API_MAPPER = "fromAPIMapper";

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        return objectMapper;
    }

    @Autowired
    @Bean(name = TO_API_MAPPER)
    public ModelMapper toApiMapper(@Qualifier(TO_API_MAPPER) List<PropertyMap<?, ?>> configList) {
        var mapper = new ModelMapper();
        configList.forEach(mapper::addMappings);
        return mapper;
    }

    @Autowired
    @Bean(name = FROM_API_MAPPER)
    public ModelMapper fromApiMapper(@Qualifier(FROM_API_MAPPER) List<PropertyMap<?, ?>> configList) {
        var mapper = new ModelMapper();
        configList.forEach(mapper::addMappings);
        return mapper;
    }
}
