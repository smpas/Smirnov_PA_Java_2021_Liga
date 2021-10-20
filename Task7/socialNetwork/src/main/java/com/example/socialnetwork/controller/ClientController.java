package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.ClientProfileDTO;
import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public Page<ShortClientDTO> getClientsByParameters(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String surname,
                                                       @RequestParam(required = false) String nickname,
                                                       @RequestParam(required = false) Long schoolId,
                                                       @PageableDefault(sort = {"id"},
                                                               direction = Sort.Direction.ASC) Pageable pageable) {
        return clientService.getClientsByParameters(name, surname, nickname, schoolId, pageable);
    }

    @GetMapping("/{id}")
    public ClientProfileDTO getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping()
    public ShortClientDTO addNewClient(@RequestBody ShortClientDTO client) {
        return clientService.addNewClient(client);
    }

    @PutMapping()
    public ShortClientDTO updateClient(@RequestBody ShortClientDTO client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
