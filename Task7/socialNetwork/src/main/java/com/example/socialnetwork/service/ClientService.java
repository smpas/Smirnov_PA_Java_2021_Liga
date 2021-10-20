package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ClientProfileDTO;
import com.example.socialnetwork.dto.ShortClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientProfileDTO getClientById(Long id);

    Page<ShortClientDTO> getClientsByParameters(String name, String surname, String nickname, Long schoolId,
                                                Pageable pageable);

    ShortClientDTO addNewClient(ShortClientDTO client);

    ShortClientDTO updateClient(ShortClientDTO client);

    void deleteClient(Long id);
}
