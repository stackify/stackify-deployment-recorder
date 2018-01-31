/*
 * JsonDeploymentTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data.json;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * JsonDeployment JUnit Test
 * @author Eric Martin
 */
public class JsonDeploymentTest {

    /**
     * testDefaultConstructor
     */
    @Test
    public void testDefaultConstructor() {
        JsonDeployment dep = new JsonDeployment();

        Assert.assertNull(dep.getAppName());
        Assert.assertNull(dep.getEnvironmentName());
        Assert.assertNull(dep.getVersion());
        Assert.assertNull(dep.getName());
        Assert.assertNull(dep.getBranch());
        Assert.assertNull(dep.getCommit());
        Assert.assertNull(dep.getUri());
    }
    
    /**
     * testEqualsHashCode
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(JsonDeployment.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }
    
    /**
     * testToString
     */
    @Test
    public void testToString() {
        JsonDeployment dep = new JsonDeployment();

        Assert.assertTrue(0 < dep.toString().length());
    }
       
    /**
     * testAppName
     */
    @Test
    public void testAppName() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getAppName());
        
        String appName = "testAppName";
        dep.setAppName(appName);
        
        Assert.assertEquals(appName, dep.getAppName());
    }
    
    /**
     * testEnvironmentName
     */
    @Test
    public void testEnvironmentName() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getEnvironmentName());
        
        String environmentName = "testEnvironmentName";
        dep.setEnvironmentName(environmentName);
        
        Assert.assertEquals(environmentName, dep.getEnvironmentName());
    }
    
    /**
     * testVersion
     */
    @Test
    public void testVersion() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getVersion());
        
        String version = "testVersion";
        dep.setVersion(version);
        
        Assert.assertEquals(version, dep.getVersion());
    }
    
    /**
     * testName
     */
    @Test
    public void testName() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getName());
        
        String name = "testName";
        dep.setName(name);
        
        Assert.assertEquals(name, dep.getName());
    }
    
    /**
     * testBranch
     */
    @Test
    public void testBranch() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getBranch());
        
        String branch = "testBranch";
        dep.setBranch(branch);
        
        Assert.assertEquals(branch, dep.getBranch());
    }
    
    /**
     * testCommit
     */
    @Test
    public void testCommit() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getCommit());
        
        String commit = "testCommit";
        dep.setCommit(commit);
        
        Assert.assertEquals(commit, dep.getCommit());
    }
    
    /**
     * testUri
     */
    @Test
    public void testUri() {
        JsonDeployment dep = new JsonDeployment();
        Assert.assertNull(dep.getUri());
        
        String uri = "testUri";
        dep.setUri(uri);
        
        Assert.assertEquals(uri, dep.getUri());
    }
}
