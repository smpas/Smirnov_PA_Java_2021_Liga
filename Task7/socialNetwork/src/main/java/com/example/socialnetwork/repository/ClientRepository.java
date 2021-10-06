package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
