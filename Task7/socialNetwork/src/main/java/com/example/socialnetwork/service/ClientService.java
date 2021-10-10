package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    Client getClientById(Long id);

    List<Client> getClientsByNameAndSurname(String name, String surname);

    Client getClientByNickname(String nickname);

    List<Client> getAllClientsBySchoolId(Long schoolId);

    Client addNewClient(Client client);

    Client updateClient(Client client);

    Client deleteClient(Long id);
}
