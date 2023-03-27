package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.response.ClientsListResponse;
import com.epam.laboratory.restapipractice.response.RandomResponse;
import com.epam.laboratory.restapipractice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/clients")
@Tag(name = "Клиенты", description = "Взаимодействие с клиентами")
@ClientBean
public class ClientController {
    @Autowired
    MessageSource messageSource;
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
    @LogInvocation
    public ResponseEntity create(@RequestBody ClientEntity client) {
        clientService.registration(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Нахождение клиента", description = "Возвращает клиента по его ID")
    @LogInvocation
    public ResponseEntity read(@PathVariable(name = "id") Long id, Locale locale) {
        LOGGER.info("Controller: Fetching user with id {}", id);
        try {
            return ResponseEntity.ok(clientService.getClient(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("error.notfound", null, locale));
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Клиенты", description = "Возвращает список клиентов")
    @LogInvocation
    public ResponseEntity<ClientsListResponse> getAllClients() {
        final ClientsListResponse clientsListResponse = clientService.getAllClients();
        return clientsListResponse != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных клиента", description = "Обновляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody ClientEntity client) {
        final boolean updated = clientService.updateClient(client);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление клиента", description = "Удаляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = clientService.deleteClient(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/random", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Создание нового экземпляра RestTemplate", description = "Возвращает ответ от внешнего API посредством RestTemplate")
    @LogInvocation
    public RandomResponse randomResponse(@RequestParam(value = "service", required = false, defaultValue = "math") String service) {
        RestTemplate restTemplate = new RestTemplate();
        String resultTemplate = restTemplate.getForObject(apiUrl, String.class);
        return new RandomResponse(String.format(template, service), resultTemplate);
    }
}