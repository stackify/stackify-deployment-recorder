/*
 * AppEnvTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * AppEnv JUnit Test
 * @author Eric Martin
 */
public class AppEnvTest {

    /**
     * testDefaultConstructor
     */
    @Test
    public void testDefaultConstructor() {
        AppEnv appEnv = new AppEnv();

        Assert.assertNull(appEnv.getApp());
        Assert.assertNull(appEnv.getEnv());
    }
    
    /**
     * testEqualsHashCode
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(AppEnv.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }
    
    /**
     * testToString
     */
    @Test
    public void testToString() {
        AppEnv appEnv = new AppEnv();

        Assert.assertTrue(0 < appEnv.toString().length());
    }
    
    /**
     * testApp
     */
    @Test
    public void testApp() {
        AppEnv appEnv = new AppEnv();
        Assert.assertNull(appEnv.getApp());
        
        String app = "testApp";
        appEnv.setApp(app);
        
        Assert.assertEquals(app, appEnv.getApp());
    }

    /**
     * testEnv
     */
    @Test
    public void testEnv() {
        AppEnv appEnv = new AppEnv();
        Assert.assertNull(appEnv.getEnv());
        
        String env = "testEnv";
        appEnv.setEnv(env);
        
        Assert.assertEquals(env, appEnv.getEnv());
    }
}
