/*
 * JsonDeployment.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JsonDeployment
 * @author Eric Martin
 */
@Data
public class JsonDeployment {

    /**
     * Application name
     */
    @JsonProperty("AppName")
    private String appName;

    /**
     * Environment name
     */
    @JsonProperty("EnvironmentName")
    private String environmentName;

    /**
     * Application version
     */
    @JsonProperty("Version")
    private String version;

    /**
     * Deployment name
     */
    @JsonProperty("Name")
    private String name;

    /**
     * Deployment branch
     */
    @JsonProperty("Branch")
    private String branch;

    /**
     * Last commit from the deployment branch
     */
    @JsonProperty("Commit")
    private String commit;

    /**
     * Uri for the build / deployment job
     */
    @JsonProperty("Uri")
    private String uri;
}
