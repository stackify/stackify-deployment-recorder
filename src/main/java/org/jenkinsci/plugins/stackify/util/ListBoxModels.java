/*
 * ListBoxModels.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import java.util.List;
import java.util.logging.Level;

import org.jenkinsci.plugins.stackify.data.AppEnv;

import hudson.util.ListBoxModel;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

/**
 * ListBoxModels
 * @author Eric Martin
 */
@Log
@UtilityClass
public class ListBoxModels {

    /**
     * @return Empty ListBoxModel
     */
    public ListBoxModel empty() {
        return new ListBoxModel();
    }
    
    /**
     * Creates a ListBoxModel from a list of app environments
     * @param appEnvs List of app envs
     * @return ListBoxModel with the contents of the app environment list
     */
    public ListBoxModel fromAppEnvList(final List<AppEnv> appEnvs) {
        if (appEnvs != null) {            
            ListBoxModel model = new ListBoxModel(appEnvs.size());
            
            for (AppEnv appEnv : appEnvs) {
                try {
                    String display = AppEnvs.getDisplay(appEnv);
                    String value = AppEnvs.serialize(appEnv);
                    
                    model.add(display, value);
                } catch (Exception e) {
                    log.log(Level.WARNING, "Unable to serialize application environment", e);
                }
            }   
            
            return model;
        }
        
        return new ListBoxModel();
    }
}
