/*
 * HttpApiDataService.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.jenkinsci.plugins.stackify.data.json.JsonAppEnv;
import org.jenkinsci.plugins.stackify.data.json.JsonDeployment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.java.Log;

/**
 * HttpApiDataService
 * @author Eric Martin
 */
@Log
public class HttpApiDataService implements ApiDataService {

    /**
     * Jackson object mapper for reading and writing json
     */
    private final ObjectMapper jsonMapper;

    /**
     * Apache http client instance
     */
    private final HttpClient httpClient;
    
    /**
     * Default constructor
     */
    protected HttpApiDataService() {
        this.jsonMapper = new ObjectMapper();
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * For testing
     * @param jsonMapper Jackson object mapper for reading and writing json
     * @param httpClient Apache http client instance
     */
    protected HttpApiDataService(final ObjectMapper jsonMapper, final HttpClient httpClient) {
        this.jsonMapper = jsonMapper;
        this.httpClient = httpClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AppEnv> getAppEnvs(final String apiKey) throws IOException {
        log.fine("HttpApiDataService.getAppEnvs()");
        
        String url = "https://api.stackify.com/api/apps/envs";
        log.fine(url);

        List<AppEnv> apps = new ArrayList<AppEnv>();

        HttpGet request = new HttpGet(url);
        request.addHeader("X-Stackify-Key", apiKey);
                        
        ResponseHandler<String> responseHandler = new ApiResponseHandler(url);
        String response = httpClient.execute(request, responseHandler);
                
        ObjectReader appEnvsReader = jsonMapper.readerFor(new TypeReference<List<JsonAppEnv>>(){});
        List<JsonAppEnv> appEnvs = appEnvsReader.readValue(response);                

        for (JsonAppEnv appEnv : appEnvs) {
            AppEnv app = new AppEnv();
            app.setApp(appEnv.getAppName());
            app.setEnv(appEnv.getEnvName());

            apps.add(app);
        }
        
        log.info(apps.toString());
        
        return apps;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void postDeploymentCompete(final String apiKey, final String application, final String environment, final String version, final String name, final String branch, final String commit, final String uri) throws IOException {
        log.fine("HttpApiDataService.postDeploymentCompete()");

        String url = "https://api.stackify.net/api/v1/deployments/complete";
        log.fine(url);

        HttpPost request = new HttpPost(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, "ApiKey " + apiKey);
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        
        JsonDeployment deployment = new JsonDeployment();
        deployment.setAppName(application);
        deployment.setEnvironmentName(environment);
        deployment.setVersion(version);
        deployment.setName(name);
        deployment.setBranch(branch);
        deployment.setCommit(commit);
        deployment.setUri(uri);
        
        ObjectWriter depWriter = jsonMapper.writerFor(new TypeReference<JsonDeployment>(){});        
        String json = depWriter.writeValueAsString(deployment);
        
        log.fine(json);

        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);

        ResponseHandler<String> responseHandler = new ApiResponseHandler(url);
        httpClient.execute(request, responseHandler);
    }
}
