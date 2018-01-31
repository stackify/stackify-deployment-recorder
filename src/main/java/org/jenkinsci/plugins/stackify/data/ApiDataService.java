/*
 * ApiDataService.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import java.io.IOException;
import java.util.List;

/**
 * ApiDataService
 * @author Eric Martin
 */
public interface ApiDataService {

    /**
     * Retrieves a list of application environment names from Stackify
     * @param apiKey Stackify API key
     * @return List of environment names
     * @throws IOException Failed to retrieve application environments
     */
    List<AppEnv> getAppEnvs(final String apiKey) throws IOException;
    
    /**
     * Posts deployment information about an application/environment to Stackify
     * @param apiKey Stackify API key
     * @param application Application name
     * @param environment Environment name
     * @param version Application version
     * @param name Deployment name
     * @param branch Deployment branch
     * @param commit Last commit from the deployment branch
     * @param uri Uri for the build / deployment job
     * @throws IOException Failed to post deployment information
     */
    void postDeploymentCompete(final String apiKey, final String application, final String environment, final String version, final String name, final String branch, final String commit, final String uri) throws IOException;

}
