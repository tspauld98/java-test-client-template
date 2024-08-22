/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

package info.rx00405.test.client.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
//import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.StatusLine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

public class GraphQLAPIClient {

    private String endpointURL = "";
    private String accessToken = "";

    static class Result {

        final int status;
        final String content;

        Result(final int status, final String content) {
            this.status = status;
            //this.statusLine = statusLine;
            this.content = content;
        }

    }

    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String prettyPrintUsingGson(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(uglyJsonString);
        String prettyJsonString = gson.toJson(jsonElement);
        return prettyJsonString;
    }

    public Result getSchema() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(this.endpointURL);

            //httpGet.addHeader("Authentication", "Bearer " + token);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);

            Result result = httpClient.execute(httpGet, response -> {
                System.out.println("----------------------------------------");
                System.out.println(httpGet + "->" + new StatusLine(response));
                // Process response message and convert it into a value object
                return new Result(response.getCode(), EntityUtils.toString(response.getEntity()));
            });
            
            return result;
        }
    }
}