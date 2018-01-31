/*
 * ServiceLocator.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

/**
 * ServiceLocator
 * @author Eric Martin
 */
@Log
@UtilityClass
public class ServiceLocator {

    /**
     * Singleton data service instance
     */
    private ApiDataService dataService;
    
    /**
     * @return Data service instance
     */
    public synchronized ApiDataService getApiDataService() {
        log.fine("ServiceLocator.getApiDataService()");
        
        if (dataService == null) {
            dataService = new HttpApiDataService();
        }
        
        return dataService;        
    }
}
