/*
 * JobsTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import hudson.model.Item;
import hudson.model.Job;

/**
 * Jobs JUnit Test
 * @author Eric Martin
 */
public class JobsTest {

    /**
     * testCanConfigureWithPermission
     */
    @Test
    public void testCanConfigureWithPermission() {
        Job<?,?> job = Mockito.mock(Job.class);
        Mockito.when(job.hasPermission(Item.CONFIGURE)).thenReturn(true);

        Assert.assertTrue(Jobs.canConfigure(job));
    }
    
    /**
     * testCanConfigureWithoutPermission
     */
    @Test
    public void testCanConfigureWithoutPermission() {
        Job<?,?> job = Mockito.mock(Job.class);
        Mockito.when(job.hasPermission(Item.CONFIGURE)).thenReturn(false);
        
        Assert.assertFalse(Jobs.canConfigure(job));
    }
    
    /**
     * testCanConfigureWithNull
     */
    @Test
    public void testCanConfigureWithNull() {
        Assert.assertFalse(Jobs.canConfigure(null));
    }
}
