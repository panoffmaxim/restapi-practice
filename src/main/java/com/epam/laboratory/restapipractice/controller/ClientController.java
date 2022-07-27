package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.response.RandomResponse;
import com.epam.laboratory.restapipractice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/clients")
@Tag(name = "Клиенты", description = "Взаимодействие с клиентами")
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final String apiUrl;
    private static final String template = "%s";

    private final ClientService clientService;

    public ClientController(ClientService clientService, @Value("${apiUrl.value}") String apiUrl) {
        this.clientService = clientService;
        this.apiUrl = apiUrl;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание клиента", description = "Позволяет создать нового клиента")
    public ResponseEntity create(@RequestBody ClientEntity client) {
        clientService.registration(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Нахождение клиента", description = "Возвращает клиента по его ID")
    public ResponseEntity read(@PathVariable(name = "id") Long id) {
        final Client client = clientService.getClient(id);
        LOGGER.info("Controller: Fetching user with id {}", id);
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Заказы клиента", description = "Возвращает доступные заказы клиента по его ID")
    public ResponseEntity<?> getAllClients() {
        final List<ClientEntity> orders = clientService.getAllClients();
        return orders != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных клиента", description = "Обновляет клиента с заданным ID")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody ClientEntity client) {
        final boolean updated = clientService.updateClient(client);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление клиента", description = "Удаляет клиента с заданным ID")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = clientService.deleteClient(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/random", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание нового экземпляра RestTemplate", description = "Возвращает ответ от внешнего API посредством RestTemplate")
    public RandomResponse randomResponse(@RequestParam(value = "service", required = false, defaultValue = "math") String service) {
        RestTemplate restTemplate = new RestTemplate();
        String resultTemplate = restTemplate.getForObject(apiUrl, String.class);
        return new RandomResponse(String.format(template, service), resultTemplate);
    }
}