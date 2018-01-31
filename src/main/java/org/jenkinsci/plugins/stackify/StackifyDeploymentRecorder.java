/*
 * StackifyDeploymentRecorder.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify;

import java.io.IOException;
import java.util.logging.Level;

import org.jenkinsci.plugins.stackify.data.ApiDataService;
import org.jenkinsci.plugins.stackify.data.AppEnv;
import org.jenkinsci.plugins.stackify.data.ServiceLocator;
import org.jenkinsci.plugins.stackify.util.AppEnvs;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import lombok.extern.java.Log;

/**
 * StackifyDeploymentRecorder
 * @author Eric Martin
 */
@Log
public class StackifyDeploymentRecorder extends Notifier {

    /**
     * Deployment configuration
     */
    private final DeploymentConfig config;
    
    /**
     * Constructor
     * @param config Deployment configuration
     */
    @DataBoundConstructor
    public StackifyDeploymentRecorder(DeploymentConfig config) {
        this.config = config;
    }

    /**
     * @return Deployment configuration
     */
    public DeploymentConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        log.fine("StackifyDeploymentRecorder.perform()");

        listener.getLogger().println("Running Stackify Deployment Recorder");

        if (build == null) {
            listener.error("Build null. Skipping Stackify deployment recording.");
            return false;
        }

        Result buildResult = build.getResult();
        
        if (buildResult != null) {
            log.info(buildResult.toString());
    
            if (buildResult == Result.FAILURE) {
                listener.error("Build failed. Skipping Stackify deployment recording.");
                return false;
            }
    
            if (buildResult == Result.ABORTED) {
                listener.error("Build aborted. Skipping Stackify deployment recording.");
                return false;
            }
        }

        log.info(config.toString());

        EnvVars envVars = build.getEnvironment(listener);
        envVars.overrideAll(build.getBuildVariables());

        String apiKey = config.getApiKey();
        AppEnv appEnv = AppEnvs.deserialize(config.getAppEnv());
        String version = envVars.expand(config.getVersion());
        String name = envVars.expand(config.getName());
        String branch = envVars.expand(config.getBranch());
        String commit = envVars.expand(config.getCommit());
        String uri = envVars.expand(config.getUri());
        
        listener.getLogger().println("Application: " + appEnv.getApp());
        listener.getLogger().println("Environment: " + appEnv.getEnv());
        listener.getLogger().println("Version: " + version);
        listener.getLogger().println("Name: " + name);
        listener.getLogger().println("Branch: " + branch);
        listener.getLogger().println("Commit: " + commit);
        listener.getLogger().println("URI: " + uri);
        
        try {
            ApiDataService apiService = ServiceLocator.getApiDataService();
            apiService.postDeploymentCompete(apiKey, appEnv.getApp(), appEnv.getEnv(), version, name, branch, commit, uri);
            
            listener.getLogger().println("Deployment has been recorded in Stackify");
            
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to record deployment in Stackify", e);
            
            listener.error("Failed to record deployment in Stackify");
            listener.error(e.getMessage());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    /**
     * DescriptorImpl
     * @author Eric Martin
     */
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Record deployment in Stackify Retrace";
        }
    }
}
