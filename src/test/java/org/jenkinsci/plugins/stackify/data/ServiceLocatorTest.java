/*
 * ServiceLocatorTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import org.junit.Assert;
import org.junit.Test;

/**
 * ServiceLocator JUnit Test
 * @author Eric Martin
 */
public class ServiceLocatorTest {

    /**
     * testGetApiDataService
     */
    @Test
    public void testGetApiDataService() {
        ApiDataService service = ServiceLocator.getApiDataService();
        Assert.assertNotNull(service);
        Assert.assertEquals(service, ServiceLocator.getApiDataService());
    }
}
