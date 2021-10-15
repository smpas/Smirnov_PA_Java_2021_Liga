package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.MessageDTO;
import com.example.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getMessagesByDialog(Long dialogId);

    MessageDTO sendMessage(Long dialogId, MessageDTO message);

    MessageDTO editMessage(Long dialogId, MessageDTO message);

    void deleteMessage(Long messageId);
}
