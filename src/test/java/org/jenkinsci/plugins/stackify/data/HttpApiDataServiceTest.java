/*
 * HttpApiDataServiceTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HttpApiDataService JUnit Test
 * @author Eric Martin
 */
public class HttpApiDataServiceTest {

    /**
     * testGetAppEnvs
     * @throws IOException 
     */
    @Test
    public void testGetAppEnvs() throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
                
        HttpClient httpClient = Mockito.mock(HttpClient.class);        
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class), Mockito.any(ApiResponseHandler.class)))
            .thenReturn("[{\"AppName\":\"app\",\"AppEnvId\":\"ee6e8860-0280-4290-9d06-606079f8ddfd\",\"AppId\":\"bb2912c7-dbab-4734-a93f-278d88b1ef28\",\"EnvName\":\"env\",\"EnvId\":1}]");
        
        HttpApiDataService service = new HttpApiDataService(jsonMapper, httpClient);
        
        List<AppEnv> appEnvs = service.getAppEnvs("my-api-key");

        Assert.assertNotNull(appEnvs);
        Assert.assertEquals(1, appEnvs.size());        
        Assert.assertEquals("app", appEnvs.get(0).getApp());
        Assert.assertEquals("env", appEnvs.get(0).getEnv());

        ArgumentCaptor<HttpGet> httpGetCaptor = ArgumentCaptor.forClass(HttpGet.class);
        ArgumentCaptor<ApiResponseHandler> responseHandlerCaptor = ArgumentCaptor.forClass(ApiResponseHandler.class);
        Mockito.verify(httpClient).execute(httpGetCaptor.capture(), responseHandlerCaptor.capture());

        HttpGet httpGet = httpGetCaptor.getValue();

        Assert.assertEquals("https://api.stackify.com/api/apps/envs", httpGet.getURI().toString());
        Assert.assertEquals("my-api-key", httpGet.getFirstHeader("X-Stackify-Key").getValue());        
    }
    
    /**
     * testPostDeploymentCompete
     * @throws IOException 
     */
    @Test
    public void testPostDeploymentCompete() throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        
        HttpClient httpClient = Mockito.mock(HttpClient.class);        
        Mockito.when(httpClient.execute(Mockito.any(HttpPost.class), Mockito.any(ApiResponseHandler.class))).thenReturn("");
        
        HttpApiDataService service = new HttpApiDataService(jsonMapper, httpClient);
        
        service.postDeploymentCompete("my-api-key", "app", "env", "version", "name", "branch", "commit", "uri");

        ArgumentCaptor<HttpPost> httpPostCaptor = ArgumentCaptor.forClass(HttpPost.class);
        ArgumentCaptor<ApiResponseHandler> responseHandlerCaptor = ArgumentCaptor.forClass(ApiResponseHandler.class);
        Mockito.verify(httpClient).execute(httpPostCaptor.capture(), responseHandlerCaptor.capture());

        HttpPost httpPost = httpPostCaptor.getValue();
        
        Assert.assertEquals("https://api.stackify.net/api/v1/deployments/complete", httpPost.getURI().toString());
        Assert.assertEquals("ApiKey my-api-key", httpPost.getFirstHeader("Authorization").getValue());        
        Assert.assertEquals("application/json", httpPost.getFirstHeader("Content-Type").getValue());        
        
        String postBody = (new BufferedReader(new InputStreamReader(httpPost.getEntity().getContent()))).readLine();        
        Assert.assertEquals("{\"AppName\":\"app\",\"EnvironmentName\":\"env\",\"Version\":\"version\",\"Name\":\"name\",\"Branch\":\"branch\",\"Commit\":\"commit\",\"Uri\":\"uri\"}", postBody);
    }
}
