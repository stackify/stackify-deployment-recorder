/*
 * Jobs.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import hudson.model.Item;
import hudson.model.Job;
import lombok.experimental.UtilityClass;

/**
 * Jobs
 * @author Eric Martin
 */
@UtilityClass
public class Jobs {

    /**
     * Determines if the current user can configure the job
     * @param job The job
     * @return True if current user can configure the job, false otherwise
     */
    public boolean canConfigure(Job<?,?> job) {
        if (job != null) {
            if (job.hasPermission(Item.CONFIGURE)) {
                return true;
            }
        }
        
        return false;
    }
}
