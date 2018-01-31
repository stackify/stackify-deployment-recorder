/*
 * StackifyDeploymentRecorderTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import org.jenkinsci.plugins.stackify.data.ApiDataService;
import org.jenkinsci.plugins.stackify.data.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hudson.EnvVars;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepMonitor;

/**
 * StackifyDeploymentRecorder JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ServiceLocator.class)
public class StackifyDeploymentRecorderTest {

    /**
     * testConstructor
     */
    @Test
    public void testConstructor() {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);
        
        Assert.assertEquals(config, recorder.getConfig());
    }
    
    /**
     * testPerform
     * @throws Exception 
     */
    @Test
    public void testPerform() throws Exception {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        Mockito.when(config.getApiKey()).thenReturn("my-api-key");
        Mockito.when(config.getAppEnv()).thenReturn("{\"app\":\"myapp\",\"env\":\"myenv\"}");
        Mockito.when(config.getVersion()).thenReturn("version");
        Mockito.when(config.getName()).thenReturn("name");
        Mockito.when(config.getBranch()).thenReturn("branch");
        Mockito.when(config.getCommit()).thenReturn("commit");
        Mockito.when(config.getUri()).thenReturn("uri");
        
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);

        EnvVars envVars = Mockito.mock(EnvVars.class);
        Mockito.when(envVars.expand(Mockito.anyString())).thenAnswer(AdditionalAnswers.<String>returnsFirstArg());
        
        AbstractBuild<?, ?> build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getResult()).thenReturn(Result.SUCCESS);
        Mockito.when(build.getEnvironment(Mockito.any(BuildListener.class))).thenReturn(envVars);
        Mockito.when(build.getBuildVariables()).thenReturn(new HashMap<String, String>());
        
        Launcher launcher = Mockito.mock(Launcher.class);
                
        PrintStream logger = Mockito.mock(PrintStream.class);
        BuildListener listener = Mockito.mock(BuildListener.class);
        Mockito.when(listener.getLogger()).thenReturn(logger);
        
        ApiDataService dataService = Mockito.mock(ApiDataService.class);
        PowerMockito.mockStatic(ServiceLocator.class);
        Mockito.when(ServiceLocator.getApiDataService()).thenReturn(dataService);
        
        boolean rc = recorder.perform(build, launcher, listener);
        
        Assert.assertTrue(rc);
        
        PowerMockito.verifyStatic(ServiceLocator.class, Mockito.times(1));
        ServiceLocator.getApiDataService(); 
        
        Mockito.verify(dataService, Mockito.times(1)).postDeploymentCompete("my-api-key", "myapp", "myenv", 
                "version", "name", "branch", "commit", "uri");
    }
    
    /**
     * testPerformWithoutBuild
     * @throws Exception 
     */
    @Test
    public void testPerformWithoutBuild() throws Exception, IOException {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);

        Launcher launcher = Mockito.mock(Launcher.class);
                
        PrintStream logger = Mockito.mock(PrintStream.class);
        BuildListener listener = Mockito.mock(BuildListener.class);
        Mockito.when(listener.getLogger()).thenReturn(logger);
        
        PowerMockito.mockStatic(ServiceLocator.class);
        
        boolean rc = recorder.perform((AbstractBuild<?, ?>) null, launcher, listener);
        
        Assert.assertFalse(rc);
        
        PowerMockito.verifyStatic(ServiceLocator.class, Mockito.times(0));
        ServiceLocator.getApiDataService();
    }
    
    /**
     * testPerformWithBuildFailure
     * @throws Exception 
     */
    @Test
    public void testPerformWithBuildFailure() throws Exception {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);

        AbstractBuild<?, ?> build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getResult()).thenReturn(Result.FAILURE);
        
        Launcher launcher = Mockito.mock(Launcher.class);
                
        PrintStream logger = Mockito.mock(PrintStream.class);
        BuildListener listener = Mockito.mock(BuildListener.class);
        Mockito.when(listener.getLogger()).thenReturn(logger);
        
        PowerMockito.mockStatic(ServiceLocator.class);
        
        boolean rc = recorder.perform(build, launcher, listener);
        
        Assert.assertFalse(rc);
        
        PowerMockito.verifyStatic(ServiceLocator.class, Mockito.times(0));
        ServiceLocator.getApiDataService();    
    }
    
    /**
     * testPerformWithBuildAborted
     * @throws Exception 
     */
    @Test
    public void testPerformWithBuildAborted() throws Exception {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);

        AbstractBuild<?, ?> build = Mockito.mock(AbstractBuild.class);
        Mockito.when(build.getResult()).thenReturn(Result.ABORTED);
        
        Launcher launcher = Mockito.mock(Launcher.class);
                
        PrintStream logger = Mockito.mock(PrintStream.class);
        BuildListener listener = Mockito.mock(BuildListener.class);
        Mockito.when(listener.getLogger()).thenReturn(logger);
        
        PowerMockito.mockStatic(ServiceLocator.class);
        
        boolean rc = recorder.perform(build, launcher, listener);
        
        Assert.assertFalse(rc);
        
        PowerMockito.verifyStatic(ServiceLocator.class, Mockito.times(0));
        ServiceLocator.getApiDataService();  
    }
    
    /**
     * testGetRequiredMonitorService
     */
    @Test
    public void testGetRequiredMonitorService() {
        DeploymentConfig config = Mockito.mock(DeploymentConfig.class);
        StackifyDeploymentRecorder recorder = new StackifyDeploymentRecorder(config);

        Assert.assertEquals(BuildStepMonitor.NONE, recorder.getRequiredMonitorService());
    }
    
    /**
     * testGetDescriptorImplIsApplicable
     */
    @Test
    public void testGetDescriptorImplIsApplicable() {
        StackifyDeploymentRecorder.DescriptorImpl descriptor = new StackifyDeploymentRecorder.DescriptorImpl();
        Assert.assertTrue(descriptor.isApplicable(null));
    }
    
    /**
     * testGetDescriptorImplGetDisplayName
     */
    @Test
    public void testGetDescriptorImplGetDisplayName() {
        StackifyDeploymentRecorder.DescriptorImpl descriptor = new StackifyDeploymentRecorder.DescriptorImpl();
        Assert.assertNotNull(descriptor.getDisplayName());
    }
}
