package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findClientsByNameAndSurname(String name, String surname);

    Client findClientByNickname(String nickname);

    List<Client> findClientsBySchool(School school);
}
