/*
 * ApiResponseHandler.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.jenkinsci.plugins.stackify.util.Strings;

import lombok.extern.java.Log;

/**
 * ApiResponseHandler
 * @author Eric Martin
 */
@Log
public class ApiResponseHandler implements ResponseHandler<String> {

    /**
     * Request url
     */
    private final String url;
    
    /**
     * Delegate response handler
     */
    private final ResponseHandler<String> delegate;
    
    /**
     * Constructor
     * @param url Request url
     */
    public ApiResponseHandler(final String url) {
        this.url = url;
        this.delegate = new BasicResponseHandler();
    }
    
    /**
     * Constructor for testing
     * @param url Request url
     * @param delegate Delegate response handler
     */
    public ApiResponseHandler(final String url, ResponseHandler<String> delegate) {
        this.url = url;
        this.delegate = delegate;
    }
    
    /**
     * For testing
     * @return The url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * For testing
     * @return The delegate response handler
     */
    public ResponseHandler<String> getDelegate() {
        return delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
        StatusLine status = response.getStatusLine();
        log.info(url + " " + status.getStatusCode() + " " + status.getReasonPhrase());

        String responseBody = delegate.handleResponse(response);
        
        if (Strings.hasValue(responseBody)){
            log.fine(responseBody);
        }

        return responseBody;
    }
}
