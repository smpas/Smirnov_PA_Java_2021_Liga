package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findMessagesByDialog(Dialog dialog);
}
