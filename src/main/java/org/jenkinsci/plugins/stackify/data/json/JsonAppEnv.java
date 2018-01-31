/*
 * JsonAppEnv.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data.json;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JsonAppEnv
 * @author Eric Martin
 */
@Data
public class JsonAppEnv {

    /**
     * Application name
     */
    @JsonProperty("AppName")
    private String appName;

    /**
     * Application environment id
     */
    @JsonProperty("AppEnvId")
    private UUID appEnvId;

    /**
     * Application id
     */
    @JsonProperty("AppId")
    private UUID appId;

    /**
     * Environment name
     */
    @JsonProperty("EnvName")
    private String envName;

    /**
     * Environment id
     */
    @JsonProperty("EnvId")
    private Long envId;
}
