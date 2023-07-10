package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import com.epam.laboratory.restapipractice.dto.ClientRequestDto;
import com.epam.laboratory.restapipractice.dto.ClientResponseDto;
import com.epam.laboratory.restapipractice.response.ClientsListResponse;
import com.epam.laboratory.restapipractice.response.RandomResponse;
import com.epam.laboratory.restapipractice.service.ClientCacheService;
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

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/clients")
@Tag(name = "Клиенты", description = "Взаимодействие с клиентами")
@ClientBean
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    MessageSource messageSource;
    @Autowired
    private ClientCacheService clientCacheService;
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
    public ResponseEntity<ClientResponseDto> create(@RequestBody ClientRequestDto clientRequestDto) {
        try {
            ClientResponseDto registeredClient = clientService.registration(clientRequestDto);
            clientCacheService.deleteAllClientsFromCache();
            return new ResponseEntity<>(registeredClient, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Нахождение клиента", description = "Возвращает клиента по его ID")
    @LogInvocation
    public ResponseEntity<ClientResponseDto> read(@PathVariable(name = "id") Long id) {
        try {
            LOGGER.info("Controller: Fetching user with id {}", id);
            ClientResponseDto clientResponseDto = clientService.getClient(id);
            return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error reading client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Клиенты", description = "Возвращает список клиентов")
    @LogInvocation
    public ResponseEntity<ClientsListResponse> getAllClients() {
        try {
            final ClientsListResponse clientsListResponse = clientService.getAllClients();
            return new ResponseEntity<>(clientsListResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error getting all clients", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных клиента", description = "Обновляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<ClientResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody ClientRequestDto clientRequestDto) {
        try {
            ClientResponseDto updatedClient = clientService.updateClient(clientRequestDto);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updating client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление клиента", description = "Удаляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        try {
            LOGGER.info("Controller: Deleting user with id {}", id);
            clientService.deleteClient(id);
            clientCacheService.deleteAllClientsFromCache();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error deleting client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
