package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/users")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> allClients = clientService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);

        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/users", params = {"name", "surname"})
    public ResponseEntity<List<Client>> getClientsByNameAndSurname(@RequestParam String name,
                                                                   @RequestParam String surname) {
        List<Client> clients = clientService.getClientsByNameAndSurname(name, surname);

        if (clients != null) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/users", params = "nickname")
    public ResponseEntity<Client> getClientByNickname(@RequestParam String nickname) {
        Client client = clientService.getClientByNickname(nickname);

        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/users", params = "schoolId")
    public ResponseEntity<List<Client>> getClientsBySchoolId(@RequestParam Long schoolId) {
        List<Client> clients = clientService.getAllClientsBySchoolId(schoolId);

        if (clients != null) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Client> addNewClient(@RequestBody Client client) {
        Client addedClient = clientService.addNewClient(client);
        return new ResponseEntity<>(addedClient, HttpStatus.OK);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client changedClient = clientService.updateClient(client);

        if (changedClient != null) {
            return new ResponseEntity<>(changedClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Client deletedClient = clientService.deleteClient(id);

        if (deletedClient != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
