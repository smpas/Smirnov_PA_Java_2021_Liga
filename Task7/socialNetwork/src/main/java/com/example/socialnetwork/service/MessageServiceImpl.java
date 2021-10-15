package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.MessageDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import com.example.socialnetwork.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, DialogRepository dialogRepository, ClientRepository clientRepository) {
        this.messageRepository = messageRepository;
        this.dialogRepository = dialogRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<MessageDTO> getMessagesByDialog(Long dialogId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        List<MessageDTO> dto = new LinkedList<>();

        if (dialog.isPresent()) {
            List<Message> messages = messageRepository.findMessagesByDialog(dialog.get());

            for (Message message : messages) {
                dto.add(new MessageDTO(message.getId(), message.getClient().getId(), message.getDate(),
                        message.getText()));
            }
            return dto;

        } else {
            throw new EntityNotFoundException("Dialog", dialogId);
        }
    }

    @Override
    @Transactional
    public MessageDTO sendMessage(Long dialogId, MessageDTO message) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        Optional<Client> sender = clientRepository.findById(message.getClient());

        if (dialog.isEmpty()) {
            throw new EntityNotFoundException("Dialog", dialogId);
        }
        if (sender.isEmpty()) {
            throw new EntityNotFoundException("Client", message.getClient());
        }

        Message sendingMessage = new Message(sender.get(), dialog.get(), new Timestamp(System.currentTimeMillis()),
                message.getText());
        sendingMessage = messageRepository.save(sendingMessage);

        return new MessageDTO(sendingMessage.getId(), sendingMessage.getClient().getId(), sendingMessage.getDate(),
                sendingMessage.getText());
    }

    @Override
    public MessageDTO editMessage(Long dialogId, MessageDTO message) {
        Optional<Message> oldMessage = messageRepository.findById(message.getId());

        if (oldMessage.isPresent()) {
            Message editingMessage = oldMessage.get();
            editingMessage.setText(message.getText());
            editingMessage = messageRepository.save(editingMessage);

            return new MessageDTO(editingMessage.getId(), editingMessage.getClient().getId(), editingMessage.getDate(),
                    editingMessage.getText());
        } else {
            throw new EntityNotFoundException("Dialog", dialogId);
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()) {
            messageRepository.delete(message.get());
        } else {
            throw new EntityNotFoundException("Message", messageId);
        }
    }
}
