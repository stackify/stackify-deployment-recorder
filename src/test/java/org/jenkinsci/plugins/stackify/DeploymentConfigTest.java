/*
 * DeploymentConfigTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify;

import java.io.IOException;
import java.util.Collections;

import org.jenkinsci.plugins.stackify.data.ApiDataService;
import org.jenkinsci.plugins.stackify.data.AppEnv;
import org.jenkinsci.plugins.stackify.data.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hudson.model.Item;
import hudson.model.Job;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;

/**
 * DeploymentConfig JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ServiceLocator.class)
public class DeploymentConfigTest {

    /**
     * testConstructor
     */
    @Test
    public void testConstructor() {
        String apiKey = "apiKey";
        String appEnv = "appEnv";
        String version = "version";
        String name = "name";
        String branch = "branch";
        String commit = "commit";
        String uri = "uri";
        
        DeploymentConfig config = new DeploymentConfig(apiKey, appEnv, version, name, branch, commit, uri);
        
        Assert.assertEquals(apiKey, config.getApiKey());
        Assert.assertEquals(appEnv, config.getAppEnv());
        Assert.assertEquals(version, config.getVersion());
        Assert.assertEquals(name, config.getName());
        Assert.assertEquals(branch, config.getBranch());
        Assert.assertEquals(commit, config.getCommit());
        Assert.assertEquals(uri, config.getUri());
    }
    
    /**
     * testToString
     */
    @Test
    public void testToString() {
        DeploymentConfig config = new DeploymentConfig("apiKey", "appEnv", "version", "name", "branch", "commit", "uri");
        
        Assert.assertNotNull(config.toString());
    }
    
    /**
     * testDescriptorImplDoCheckApiKey
     */
    @Test
    public void testDescriptorImplDoCheckApiKey() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckApiKey("my-api-key");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    /**
     * testDescriptorImplDoCheckApiKeyNull
     */
    @Test
    public void testDescriptorImplDoCheckApiKeyNull() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckApiKey(null);
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDescriptorImplDoCheckApiKeyEmpty
     */
    @Test
    public void testDescriptorImplDoCheckApiKeyEmpty() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckApiKey("");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDoFillAppEnvItems
     * @throws IOException 
     */
    @Test
    public void testDoFillAppEnvItems() throws IOException {
        AppEnv appEnv = new AppEnv();
        appEnv.setApp("myapp");
        appEnv.setEnv("myenv");

        ApiDataService dataService = Mockito.mock(ApiDataService.class);
        Mockito.when(dataService.getAppEnvs(Mockito.anyString())).thenReturn(Collections.singletonList(appEnv));
        
        PowerMockito.mockStatic(ServiceLocator.class);
        Mockito.when(ServiceLocator.getApiDataService()).thenReturn(dataService);
        
        Job<?,?> job = Mockito.mock(Job.class);
        Mockito.when(job.hasPermission(Item.CONFIGURE)).thenReturn(true);

        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();

        ListBoxModel model = descriptor.doFillAppEnvItems(job, "my-api-key");
        
        Assert.assertNotNull(model);
        Assert.assertEquals(1, model.size());
        Assert.assertEquals("myapp (myenv)", model.get(0).name);
        Assert.assertEquals("{\"app\":\"myapp\",\"env\":\"myenv\"}", model.get(0).value);
    }
    
    /**
     * testDescriptorImplDoCheckAppEnv
     */
    @Test
    public void testDescriptorImplDoCheckAppEnv() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckAppEnv("app env");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    /**
     * testDescriptorImplDoCheckAppEnvNull
     */
    @Test
    public void testDescriptorImplDoCheckAppEnvNull() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckAppEnv(null);
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDescriptorImplDoCheckAppEnvEmpty
     */
    @Test
    public void testDescriptorImplDoCheckAppEnvEmpty() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckAppEnv("");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDescriptorImplDoCheckVersion
     */
    @Test
    public void testDescriptorImplDoCheckVersion() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckVersion("v");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    /**
     * testDescriptorImplDoCheckVersionNull
     */
    @Test
    public void testDescriptorImplDoCheckVersionNull() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckVersion(null);
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDescriptorImplDoCheckVersionEmpty
     */
    @Test
    public void testDescriptorImplDoCheckVersionEmpty() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        FormValidation validation = descriptor.doCheckVersion("");
        
        Assert.assertNotNull(validation);
        Assert.assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
    
    /**
     * testDescriptorImplGetDisplayName
     */
    @Test
    public void testDescriptorImplGetDisplayName() {
        DeploymentConfig.DescriptorImpl descriptor = new DeploymentConfig.DescriptorImpl();
        
        Assert.assertNotNull(descriptor.getDisplayName());
    }
}
