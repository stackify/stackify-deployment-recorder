/*
 * DeploymentConfig.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.jenkinsci.plugins.stackify.data.ApiDataService;
import org.jenkinsci.plugins.stackify.data.AppEnv;
import org.jenkinsci.plugins.stackify.data.ServiceLocator;
import org.jenkinsci.plugins.stackify.util.Jobs;
import org.jenkinsci.plugins.stackify.util.ListBoxModels;
import org.jenkinsci.plugins.stackify.util.Strings;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import lombok.extern.java.Log;

/**
 * DeploymentConfig
 * @author Eric Martin
 */
@Log
public class DeploymentConfig extends AbstractDescribableImpl<DeploymentConfig> {

    /**
     * Stackify API key
     */
    private final String apiKey;
    
    /**
     * Stackify app environment name
     */
    private final String appEnv;

    /**
     * Application version (UI Default: ${POM_VERSION})
     * Maven: ${POM_VERSION}
     * Gradle: TODO
     */
    private final String version;
    
    /**
     * Deployment name (UI Default: ${JOB_NAME})
     */
    private final String name;

    /**
     * Deployment branch (UI Default: ${GIT_BRANCH})
     * Git: ${GIT_BRANCH}
     * SVN: ${SVN_URL}
     * CVS: ${CVS_BRANCH}
     */
    private final String branch;

    /**
     * Last commit from the deployment branch (UI Default: ${GIT_COMMIT})
     * Git: ${GIT_COMMIT}
     * SVN: ${SVN_REVISION}
     * CVS: TODO
     */
    private final String commit;

    /**
     * Uri for the build / deployment job (UI Default: ${BUILD_URL})
     */
    private final String uri;

    /**
     * Constructor
     * @param apiKey Stackify API key
     * @param appEnv Stackify application environment name
     * @param version Application version
     * @param name Deployment name
     * @param branch Deployment branch
     * @param commit Last commit from the deployment branch
     * @param uri Uri for the build / deployment job
     */
    @DataBoundConstructor
    public DeploymentConfig(String apiKey, String appEnv, String version, String name, String branch, String commit, String uri) {
        this.apiKey = apiKey;
        this.appEnv = appEnv;
        this.version = version;
        this.name = name;
        this.branch = branch;
        this.commit = commit;
        this.uri = uri;        
    }

    /**
     * @return Stackify API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @return Stackify application environment name
     */
    public String getAppEnv() {
        return appEnv;
    }

    /**
     * @return Application version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return Deployment name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Deployment branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @return Last commit from the deployment branch
     */
    public String getCommit() {
        return commit;
    }

    /**
     * @return Uri for the build / deployment job
     */
    public String getUri() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DeploymentConfig [apiKey=" + apiKey + ", appEnv=" + appEnv + ", version=" + version + ", name=" + name
                + ", branch=" + branch + ", commit=" + commit + ", uri=" + uri + "]";
    }

    /**
     * DescriptorImpl
     * @author Eric Martin
     */
    @Extension
    public static final class DescriptorImpl extends Descriptor<DeploymentConfig> {

        /**
         * Validates the Stackify API key
         * @param apiKey Stackify API key
         * @return ok if api key is populated, error otherwise
         */
        public FormValidation doCheckApiKey(@QueryParameter("apiKey") String apiKey) {
            log.fine("DeploymentConfig.DescriptorImpl.doCheckApiKey()");

            if (Strings.isEmpty(apiKey)) {
                return FormValidation.error("Missing API Key");
            }
            
            return FormValidation.ok();
        }

        /**
         * Retrieves a pick list of application environment names
         * @param job The jenkins job
         * @param apiKey Stackify API key
         * @return Pick list of application environment names
         * @throws IOException Failed to retrieve application environment names
         */
        public ListBoxModel doFillAppEnvItems(@AncestorInPath Job<?,?> job, @QueryParameter("apiKey") final String apiKey) throws IOException {
            log.fine("DeploymentConfig.DescriptorImpl.doFillAppEnvItems()");

            if (Jobs.canConfigure(job)) {            
                if (Strings.hasValue(apiKey)) {                    
                    try {
                        ApiDataService dataService = ServiceLocator.getApiDataService();
                        List<AppEnv> appEnvs = dataService.getAppEnvs(apiKey);
                        
                        return ListBoxModels.fromAppEnvList(appEnvs);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Failed to get application environemnt list", e);
                    }
                }
            }
            
            return ListBoxModels.empty();
        }
        
        /**
         * Validates the application name
         * @param appEnv App environment name
         * @return ok if app environment is populated, error otherwise
         */
        public FormValidation doCheckAppEnv(@QueryParameter("appEnv") String appEnv) {
            log.fine("DeploymentConfig.DescriptorImpl.doCheckAppEnv()");

            if (Strings.isEmpty(appEnv)) {
                return FormValidation.error("Missing Application");
            }
            
            return FormValidation.ok();
        }
        
        /**
         * Validates the version
         * @param version Version
         * @return ok if version is populated, error otherwise
         */
        public FormValidation doCheckVersion(@QueryParameter("version") String version) {
            log.fine("DeploymentConfig.DescriptorImpl.doCheckVersion()");
            
            if (Strings.isEmpty(version)) {
                return FormValidation.error("Missing Version");
            }
            
            return FormValidation.ok();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return "Deployment Notification";
        }
    }
}
