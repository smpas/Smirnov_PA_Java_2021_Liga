package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.MessageDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.entity.Message;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import com.example.socialnetwork.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<MessageDTO> getMessagesByDialog(Long dialogId) {
        Dialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new EntityNotFoundException(Dialog.class.getName(), dialogId));

        List<MessageDTO> dto = new LinkedList<>();
        List<Message> messages = messageRepository.findMessagesByDialog(dialog);
        for (Message message : messages) {
            dto.add(new MessageDTO(message.getId(), message.getClient().getId(), message.getDate(),
                    message.getText()));
        }
        return dto;
    }

    @Override
    @Transactional
    public MessageDTO sendMessage(Long dialogId, MessageDTO message) {
        Dialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new EntityNotFoundException(Dialog.class.getName(), dialogId));
        Client sender = clientRepository.findById(message.getClient())
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), message.getClient()));

        Message sendingMessage = new Message(sender, dialog, new Timestamp(System.currentTimeMillis()),
                message.getText());
        sendingMessage = messageRepository.save(sendingMessage);

        return new MessageDTO(sendingMessage.getId(), sendingMessage.getClient().getId(), sendingMessage.getDate(),
                sendingMessage.getText());
    }

    @Override
    @Transactional
    public MessageDTO editMessage(Long dialogId, MessageDTO message) {
        Message editingMessage = messageRepository.findById(message.getId())
                .orElseThrow(() -> new EntityNotFoundException(Message.class.getName(), message.getId()));

        editingMessage.setText(message.getText());
        editingMessage = messageRepository.save(editingMessage);

        return new MessageDTO(editingMessage.getId(), editingMessage.getClient().getId(), editingMessage.getDate(),
                editingMessage.getText());
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException(Message.class.getName(), messageId));

        messageRepository.delete(message);
    }
}
