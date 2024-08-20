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

public class HttpGetUtils {

    static class Result {

        final int status;
        final String content;

        Result(final int status, final String content) {
            this.status = status;
            this.content = content;
        }

    }

    //private static final String USER_PROFILE_URL = "https://api.example.com/user/profile";

    public String prettyPrintUsingGson(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(uglyJsonString);
        String prettyJsonString = gson.toJson(jsonElement);
        return prettyJsonString;
    }

    public void fetchGithubSchema(String token) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://api.github.com/graphql");

            //httpGet.addHeader("Authentication", "Bearer " + token);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            Result result = httpClient.execute(httpGet, response -> {
                System.out.println("----------------------------------------");
                System.out.println(httpGet + "->" + new StatusLine(response));
                // Process response message and convert it into a value object
                return new Result(response.getCode(), EntityUtils.toString(response.getEntity()));
            });
            System.out.println(result.status);
            System.out.println("========================================");
            System.out.println(prettyPrintUsingGson(result.content)); 
        }
    }
}