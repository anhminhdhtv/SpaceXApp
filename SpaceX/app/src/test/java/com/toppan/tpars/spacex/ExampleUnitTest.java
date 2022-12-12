package com.toppan.tpars.spacex;

import android.media.audiofx.Equalizer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toppan.tpars.spacex.data.model.Launch;
import com.toppan.tpars.spacex.data.network.ApiClient;
import com.toppan.tpars.spacex.data.network.ApiInterface;
import com.toppan.tpars.spacex.helper.DateConverter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private MockWebServer mockWebServer;
    private ApiInterface apiInterface;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();
        apiInterface = ApiClient.getInstance(mockWebServer.url("/").toString())
                .create(ApiInterface.class);
    }


    @Test
    public void apiTest() {
        Launch launch = new Launch(
                "id",
                "name",
                Calendar.getInstance().getTime(),
                "",
                null);
        List<Launch> output = new ArrayList<>();
        List<Launch> input = new ArrayList<>();
        input.add(launch);
        Gson gson = new GsonBuilder().setDateFormat(DateConverter.DATE_UTC_FORMAT).create();

        MockResponse mockResponse = new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(gson.toJson(input));
        mockWebServer.enqueue(mockResponse);
        try {
            List<Launch> responseBody = apiInterface.getLaunchList().execute().body();
            if (responseBody != null) {
                output.addAll(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(input.get(0), output.get(0));
    }

    @After
    public void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}