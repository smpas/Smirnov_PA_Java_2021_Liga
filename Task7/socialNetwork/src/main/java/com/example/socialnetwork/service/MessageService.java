package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByDialog(Long dialogId);

    Message sendMessage(Long dialogId, Long clientId, Message message);

    Message editMessage(Message message);

    Message deleteMessage(Long messageId);
}
