package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import com.example.socialnetwork.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DialogRepository dialogRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Message> getMessagesByDialog(Long dialogId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);

        if (dialog.isPresent()) {
            List<Message> messages = messageRepository.findMessagesByDialog(dialog.get());
            if (!messages.isEmpty()) {
                return messages;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Message sendMessage(Long dialogId, Long clientId, Message message) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        Optional<Client> client = clientRepository.findById(clientId);

        if (dialog.isPresent() && client.isPresent()) {
            message.setDialog(dialog.get());
            message.setClient(client.get());
            message.setDate(new Timestamp(System.currentTimeMillis()));
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    @Override
    public Message editMessage(Message message) {
        Optional<Message> oldMessage = messageRepository.findById(message.getId());

        if (oldMessage.isPresent()) {
            message.setDate(oldMessage.get().getDate());
            message.setDialog(oldMessage.get().getDialog());
            message.setClient(oldMessage.get().getClient());
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    @Override
    public Message deleteMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            messageRepository.delete(message.get());
            return message.get();
        } else {
            return null;
        }
    }
}
