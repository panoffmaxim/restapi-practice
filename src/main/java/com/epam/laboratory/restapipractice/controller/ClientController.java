package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import com.epam.laboratory.restapipractice.dto.ClientRequestDto;
import com.epam.laboratory.restapipractice.dto.ClientResponseDto;
import com.epam.laboratory.restapipractice.dto.ClientsListResponseDto;
import com.epam.laboratory.restapipractice.response.RandomResponse;
import com.epam.laboratory.restapipractice.service.ClientCacheService;
import com.epam.laboratory.restapipractice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ClientController {
    private final MessageSource messageSource;
    private final ClientCacheService clientCacheService;
    private final String apiUrl;
    private static final String template = "%s";
    private final ClientService clientService;

    public ClientController(ClientService clientService, ClientCacheService clientCacheService, MessageSource messageSource, @Value("${apiUrl.value}") String apiUrl) {
        this.clientService = clientService;
        this.clientCacheService = clientCacheService;
        this.messageSource = messageSource;
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
            log.error("Error creating client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Нахождение клиента", description = "Возвращает клиента по его ID")
    @LogInvocation
    public ResponseEntity<ClientResponseDto> read(@PathVariable(name = "id") Long id) {
        try {
            log.info("Controller: Fetching user with id {}", id);
            ClientResponseDto clientResponseDto = clientService.getClient(id);
            return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error reading client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Клиенты", description = "Возвращает список клиентов")
    @LogInvocation
    public ResponseEntity<ClientsListResponseDto> getAllClients() {
        try {
            final ClientsListResponseDto clientsListResponseDto = clientService.getAllClients();
            return new ResponseEntity<>(clientsListResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting all clients", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновление данных клиента", description = "Обновляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<ClientResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody ClientRequestDto clientRequestDto) {
        try {
            ClientResponseDto updatedClient = clientService.updateClient(id, clientRequestDto);
            log.info("Controller: Updating user with id {}", id);
            clientCacheService.deleteAllClientsFromCache();
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating client", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Удаление клиента", description = "Удаляет клиента с заданным ID")
    @LogInvocation
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        try {
            log.info("Controller: Deleting user with id {}", id);
            clientService.deleteClient(id);
            clientCacheService.deleteAllClientsFromCache();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting client", e);
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
