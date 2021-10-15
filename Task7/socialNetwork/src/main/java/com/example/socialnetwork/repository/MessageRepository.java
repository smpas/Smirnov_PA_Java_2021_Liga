package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findMessagesByDialog(Dialog dialog);
}
