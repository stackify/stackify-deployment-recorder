/*
 * ListBoxModelsTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.stackify.data.AppEnv;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import hudson.util.ListBoxModel;

/**
 * ListBoxModels JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AppEnvs.class)
public class ListBoxModelsTest {

    /**
     * testEmpty
     */
    @Test
    public void testEmpty() {
        ListBoxModel model = ListBoxModels.empty();
        
        Assert.assertNotNull(model);
        Assert.assertTrue(model.isEmpty());
    }
    
    /**
     * testFromAppEnvList
     * @throws IOException 
     */
    @Test
    public void testFromAppEnvList() throws IOException {        
        AppEnv appEnv1 = new AppEnv();
        appEnv1.setApp("app1");
        appEnv1.setEnv("env1");
        
        AppEnv appEnv2 = new AppEnv();
        appEnv2.setApp("app2");
        appEnv2.setEnv("env2");
        
        List<AppEnv> values = new ArrayList<AppEnv>();
        values.add(appEnv1);
        values.add(appEnv2);
        
        ListBoxModel model = ListBoxModels.fromAppEnvList(values);
        
        Assert.assertNotNull(model);
        Assert.assertEquals(values.size(), model.size());
        
        for (int i = 0; i < model.size(); ++i) {
            Assert.assertEquals(AppEnvs.getDisplay(values.get(i)), model.get(i).name);
            Assert.assertEquals(AppEnvs.serialize(values.get(i)), model.get(i).value);
        }
    }
    
    /**
     * testFromAppEnvListWithNull
     */
    @Test
    public void testFromAppEnvListWithNull() {
        ListBoxModel model = ListBoxModels.fromAppEnvList(null);
        
        Assert.assertNotNull(model);
        Assert.assertTrue(model.isEmpty());
    }
    
    /**
     * testFromAppEnvListPartial
     * @throws IOException 
     */
    @Test
    public void testFromAppEnvListPartial() throws IOException {        
        AppEnv appEnv1 = new AppEnv();
        appEnv1.setApp("serialization");
        appEnv1.setEnv("failure");
        
        AppEnv appEnv2 = new AppEnv();
        appEnv2.setApp("myapp");
        appEnv2.setEnv("myenv");
        
        List<AppEnv> values = new ArrayList<AppEnv>();
        values.add(appEnv1);
        values.add(appEnv2);
        
        PowerMockito.mockStatic(AppEnvs.class);
        Mockito.when(AppEnvs.serialize(appEnv1)).thenThrow(new RuntimeException());
        Mockito.when(AppEnvs.serialize(appEnv2)).thenReturn("{\"app\":\"myapp\",\"env\":\"myenv\"}");
        
        ListBoxModel model = ListBoxModels.fromAppEnvList(values);
        
        Assert.assertNotNull(model);
        Assert.assertEquals(1, model.size());
        
        Assert.assertEquals(AppEnvs.getDisplay(values.get(1)), model.get(0).name);
        Assert.assertEquals(AppEnvs.serialize(values.get(1)), model.get(0).value);
    }
}
