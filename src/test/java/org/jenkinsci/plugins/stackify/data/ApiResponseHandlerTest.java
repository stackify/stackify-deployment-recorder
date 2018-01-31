/*
 * ApiResponseHandlerTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * ApiResponseHandler JUnit Test
 * @author Eric Martin
 */
public class ApiResponseHandlerTest {

    /**
     * testConstructor
     */
    @Test
    public void testConstructor() {
        String url = "url";
        
        ApiResponseHandler handler = new ApiResponseHandler(url);
        
        Assert.assertEquals(url,  handler.getUrl());
        Assert.assertTrue(handler.getDelegate() instanceof BasicResponseHandler);
    }
    
    /**
     * testHandleResponse
     * @throws IOException 
     */
    @Test
    public void testHandleResponse() throws IOException {
        StatusLine status = Mockito.mock(StatusLine.class);
        Mockito.when(status.getStatusCode()).thenReturn(200);
        Mockito.when(status.getReasonPhrase()).thenReturn("OK");

        HttpResponse response = Mockito.mock(HttpResponse.class);
        Mockito.when(response.getStatusLine()).thenReturn(status);
        
        ResponseHandler<String> delegate = Mockito.mock(ResponseHandler.class);
        Mockito.when(delegate.handleResponse(response)).thenReturn("testHandleResponse");

        ApiResponseHandler handler = new ApiResponseHandler("url", delegate);
        
        String stringResponse = handler.handleResponse(response);

        Assert.assertNotNull(stringResponse);
        Assert.assertEquals("testHandleResponse", stringResponse);
    }
}
