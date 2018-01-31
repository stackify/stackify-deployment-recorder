/*
 * AppEnvs.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import java.io.IOException;

import org.jenkinsci.plugins.stackify.data.AppEnv;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.experimental.UtilityClass;

/**
 * AppEnvs
 * @author Eric Martin
 */
@UtilityClass
public class AppEnvs {

    /**
     * Jackson object mapper for reading and writing json
     */
    private final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Builds the display string for this app environment
     * @param appEnv The app / env object
     * @return Display string for this app environment
     */
    public String getDisplay(final AppEnv appEnv) {
        return appEnv.getApp() + " (" + appEnv.getEnv() + ")";
    }
    
    /**
     * Serializes the AppEnv object to a string
     * @param appEnv The app / env object
     * @return String that represents the app / env
     * @throws IOException Problem serializing the appEnv
     */
    public String serialize(final AppEnv appEnv) throws IOException {
        ObjectWriter writer = jsonMapper.writerFor(new TypeReference<AppEnv>(){});        
        String json = writer.writeValueAsString(appEnv);

        return json;
    }
    
    /**
     * Deserializes the app env string to an object
     * @param json String that represents the app / env
     * @return The app / env object
     * @throws IOException Problem deserializing the appEnv json string
     */
    public AppEnv deserialize(final String json) throws IOException {
        ObjectReader reader = jsonMapper.readerFor(new TypeReference<AppEnv>(){});        
        AppEnv appEnv = reader.readValue(json);                

        return appEnv;
    }
}
