/*
 * AppEnvsTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import java.io.IOException;

import org.jenkinsci.plugins.stackify.data.AppEnv;
import org.junit.Assert;
import org.junit.Test;

/**
 * AppEnvs JUnit Test
 * @author Eric Martin
 */
public class AppEnvsTest {

    /**
     * testSerialize
     * @throws IOException 
     */
    @Test
    public void testSerialize() throws IOException {
        AppEnv appEnv = new AppEnv();
        appEnv.setApp("myapp");
        appEnv.setEnv("myenv");

        String json = AppEnvs.serialize(appEnv);
        
        Assert.assertNotNull(json);
        Assert.assertFalse(json.isEmpty());
        Assert.assertEquals("{\"app\":\"myapp\",\"env\":\"myenv\"}", json);
    }
    
    /**
     * testDeserialize
     * @throws IOException 
     */
    @Test
    public void testDeserialize() throws IOException {
        AppEnv appEnv = AppEnvs.deserialize("{\"app\":\"myapp\",\"env\":\"myenv\"}");
        
        Assert.assertNotNull(appEnv);
        Assert.assertEquals("myapp", appEnv.getApp());
        Assert.assertEquals("myenv", appEnv.getEnv());
    }
}
