package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ClientProfileDTO;
import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.entity.Client;

import java.util.List;

public interface ClientService {
    List<ShortClientDTO> getAllClients();

    ClientProfileDTO getClientById(Long id);

    List<ShortClientDTO> getClientsByNameAndSurname(String name, String surname);

    ClientProfileDTO getClientByNickname(String nickname);

    List<ShortClientDTO> getAllClientsBySchoolId(Long schoolId);

    ShortClientDTO addNewClient(ShortClientDTO client);

    ShortClientDTO updateClient(ShortClientDTO client);

    void deleteClient(Long id);
}
