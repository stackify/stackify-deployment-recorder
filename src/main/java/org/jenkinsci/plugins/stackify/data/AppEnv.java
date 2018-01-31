/*
 * AppEnv.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import lombok.Data;

/**
 * AppEnv
 * @author Eric Martin
 */
@Data
public class AppEnv {

    /**
     * Application name
     */
    private String app;

    /**
     * Environment name
     */
    private String env;

}
