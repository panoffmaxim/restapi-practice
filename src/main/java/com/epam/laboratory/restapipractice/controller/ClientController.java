package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.ClientEntity;
import com.epam.laboratory.restapipractice.model.Client;
import com.epam.laboratory.restapipractice.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@RequestBody ClientEntity client) {
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") Long id) {
        final Client client = clientService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody ClientEntity client) {
        final boolean updated = clientService.update(client, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = clientService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}