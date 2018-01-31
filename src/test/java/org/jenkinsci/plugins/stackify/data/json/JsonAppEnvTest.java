/*
 * JsonAppEnvTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data.json;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * JsonAppEnv JUnit Test
 * @author Eric Martin
 */
public class JsonAppEnvTest {

    /**
     * testDefaultConstructor
     */
    @Test
    public void testDefaultConstructor() {
        JsonAppEnv appEnv = new JsonAppEnv();

        Assert.assertNull(appEnv.getAppName());
        Assert.assertNull(appEnv.getAppEnvId());
        Assert.assertNull(appEnv.getAppId());
        Assert.assertNull(appEnv.getEnvName());
        Assert.assertNull(appEnv.getEnvId());
    }
    
    /**
     * testEqualsHashCode
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(JsonAppEnv.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }
    
    /**
     * testToString
     */
    @Test
    public void testToString() {
        JsonAppEnv appEnv = new JsonAppEnv();

        Assert.assertTrue(0 < appEnv.toString().length());
    }
    
    /**
     * testAppName
     */
    @Test
    public void testAppName() {
        JsonAppEnv appEnv = new JsonAppEnv();
        Assert.assertNull(appEnv.getAppName());
        
        String appName = "testAppName";
        appEnv.setAppName(appName);
        
        Assert.assertEquals(appName, appEnv.getAppName());
    }
    
    /**
     * testAppEnvId
     */
    @Test
    public void testAppEnvId() {
        JsonAppEnv appEnv = new JsonAppEnv();
        Assert.assertNull(appEnv.getAppEnvId());
        
        UUID appEnvId = UUID.randomUUID();
        appEnv.setAppEnvId(appEnvId);
        
        Assert.assertEquals(appEnvId, appEnv.getAppEnvId());
    }

    /**
     * testAppId
     */
    @Test
    public void testAppId() {
        JsonAppEnv appEnv = new JsonAppEnv();
        Assert.assertNull(appEnv.getAppId());
        
        UUID appId = UUID.randomUUID();
        appEnv.setAppId(appId);
        
        Assert.assertEquals(appId, appEnv.getAppId());
    }

    /**
     * testEnvName
     */
    @Test
    public void testEnvName() {
        JsonAppEnv appEnv = new JsonAppEnv();
        Assert.assertNull(appEnv.getEnvName());
        
        String envName = "testEnvName";
        appEnv.setEnvName(envName);
        
        Assert.assertEquals(envName, appEnv.getEnvName());
    }
    
    /**
     * testEnvId
     */
    @Test
    public void testEnvId() {
        JsonAppEnv appEnv = new JsonAppEnv();
        Assert.assertNull(appEnv.getEnvId());
        
        Long envId = Long.valueOf(System.currentTimeMillis());
        appEnv.setEnvId(envId);
        
        Assert.assertEquals(envId, appEnv.getEnvId());
    }
}
