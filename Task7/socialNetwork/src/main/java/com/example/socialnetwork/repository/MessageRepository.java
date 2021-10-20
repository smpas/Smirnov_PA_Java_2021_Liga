package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findMessagesByDialog(Dialog dialog, Pageable pageable);
}
