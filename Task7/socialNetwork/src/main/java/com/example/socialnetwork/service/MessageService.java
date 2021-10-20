package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<MessageDTO> getMessagesByDialog(Long dialogId, Pageable pageable);

    MessageDTO sendMessage(Long dialogId, MessageDTO message);

    MessageDTO editMessage(Long dialogId, MessageDTO message);

    void deleteMessage(Long messageId);
}
