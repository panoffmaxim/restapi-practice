package com.epam.laboratory.restapipractice.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ClientControllerTest {
    private static final String RANDOMAPI_PATH = "/random";

    private WireMockServer wireMockServer = new WireMockServer();
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void callExternalAPI_returnsCorrect() throws IOException {
        wireMockServer.start();

        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo(RANDOMAPI_PATH)).willReturn(aResponse().withBody("This is random API")));

        HttpGet request = new HttpGet("http://localhost:8080/random");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo(RANDOMAPI_PATH)));
        assertEquals("This is random API", stringResponse);

        wireMockServer.stop();
    }

    private static String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String stringResponse = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return stringResponse;
    }
}