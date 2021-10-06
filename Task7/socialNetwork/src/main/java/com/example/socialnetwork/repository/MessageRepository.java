package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
