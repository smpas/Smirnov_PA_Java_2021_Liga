package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialogRepository extends CrudRepository<Dialog, Long> {
    List<Dialog> findDialogsByClientsContains(Client client);
}
