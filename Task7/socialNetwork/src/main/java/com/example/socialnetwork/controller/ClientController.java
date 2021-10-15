package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.ClientProfileDTO;
import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public ResponseEntity<List<ShortClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProfileDTO> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @GetMapping(params = {"name", "surname"})
    public ResponseEntity<List<ShortClientDTO>> getClientsByNameAndSurname(@RequestParam String name,
                                                                           @RequestParam String surname) {
        return new ResponseEntity<>(clientService.getClientsByNameAndSurname(name, surname), HttpStatus.OK);
    }

    @GetMapping(params = "nickname")
    public ResponseEntity<ClientProfileDTO> getClientByNickname(@RequestParam String nickname) {
        return new ResponseEntity<>(clientService.getClientByNickname(nickname), HttpStatus.OK);
    }

    @GetMapping(params = "school_id")
    public ResponseEntity<List<ShortClientDTO>> getClientsBySchoolId(@RequestParam Long school_id) {
        return new ResponseEntity<>(clientService.getAllClientsBySchoolId(school_id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ShortClientDTO> addNewClient(@RequestBody ShortClientDTO client) {
        return new ResponseEntity<>(clientService.addNewClient(client), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ShortClientDTO> updateClient(@RequestBody ShortClientDTO client) {
        return new ResponseEntity<>(clientService.updateClient(client), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
