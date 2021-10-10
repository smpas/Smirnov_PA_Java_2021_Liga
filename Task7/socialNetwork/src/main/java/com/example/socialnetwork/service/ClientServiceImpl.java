package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.School;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

    @Override
    public List<Client> getClientsByNameAndSurname(String name, String surname) {
        List<Client> clients = clientRepository.findClientsByNameAndSurname(name, surname);

        if (!clients.isEmpty()) {
            return clients;
        } else {
            return null;
        }
    }

    @Override
    public Client getClientByNickname(String nickname) {
        return clientRepository.findClientByNickname(nickname);
    }

    @Override
    public List<Client> getAllClientsBySchoolId(Long schoolId) {
        School school = schoolRepository.findById(schoolId).get();
        List<Client> clients = clientRepository.findClientsBySchool(school);

        if (!clients.isEmpty()) {
            return clients;
        } else {
            return null;
        }
    }

    @Override
    public Client addNewClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        Optional<Client> changingClient = clientRepository.findById(client.getId());

        if (changingClient.isPresent()) {
            return clientRepository.save(client);
        } else {
            return null;
        }
    }

    @Override
    public Client deleteClient(Long id) {
        Optional<Client> deletingClient = clientRepository.findById(id);

        if (deletingClient.isPresent()) {
            clientRepository.delete(deletingClient.get());
            return deletingClient.get();
        } else {
            return null;
        }
    }

}
