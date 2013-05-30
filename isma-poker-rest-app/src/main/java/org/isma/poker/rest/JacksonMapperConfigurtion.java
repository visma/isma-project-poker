package org.isma.poker.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

@Configuration
public class JacksonMapperConfigurtion {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        SerializationConfig config = objectMapper.getSerializationConfig().withSerializationInclusion(NON_NULL);
        objectMapper.setSerializationConfig(config);
        return objectMapper;
    }
}
