package com.mohit.springfirstproject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClientPostJsonExample {
    public void mainjson() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        try {
            // Specify the URL endpoint for the POST request
            //HttpPost httpPost = new HttpPost("http://example.com/api/resource");
            HttpPost httpPost = new HttpPost("http://localhost:8080/jsontest");
            
            // Set the JSON payload
            String jsonPayload = "{\"name\":\"John\", \"age\":30}";
            StringEntity stringEntity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            
            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);
            
            // Get the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            
            // Get the response body
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            
            // Close the response entity
            EntityUtils.consume(responseEntity);
            
            // Handle the response
            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the HttpClient instance
            httpClient.getConnectionManager().shutdown();
        }
    }
}