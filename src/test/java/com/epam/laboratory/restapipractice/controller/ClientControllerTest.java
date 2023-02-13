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
//
//    private final ClientService clientService = mock(ClientService.class);
//
//    private final String apiUrl = "http://test.tt";
//
//    private final ClientController clientController = new ClientController(clientService, apiUrl);
//
//    @Test
//    void getClientOrders_returnsNotFound() {
//        //given
//        var clientId = 1L;
//        when(clientService.findClientOrders(clientId)).thenReturn(null);
//
//        //when
//        var response = clientController.getClientOrders(clientId);
//
//        //then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    void readClientById_ShouldReturnClientAndOkStatus() {
//        ClientEntity client = new ClientEntity();
//        OrderEntity orderOne = new OrderEntity();
//        OrderEntity orderTwo = new OrderEntity();
//
//        orderOne.setCompleted(true);
//        orderOne.setClientName("josh");
//        orderOne.setPaymentMethod("credit-card");
//        orderOne.setDeliveryInf("today");
//        orderTwo.setCompleted(true);
//        orderTwo.setClientName("dan");
//        orderTwo.setPaymentMethod("cash");
//        orderTwo.setDeliveryInf("tomorrow");
//        List<OrderEntity> orders = new ArrayList<>();
//        orders.add(orderOne);
//        orders.add(orderTwo);
//        client.setId(1L);
//        client.setClientName("Ginger");
//        client.setPhone("777");
//        client.setOrders(orders);
//
//        when(clientService.read(anyLong())).thenReturn(Client.toModel(client));
//        ResponseEntity<Client> clientEntity = clientController.read(1L);
//        assertNotNull(clientEntity);
//        assertThat(clientEntity.getStatusCodeValue()).isEqualTo(200);
//    }
}