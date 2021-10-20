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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final ClientRepository clientRepository;

    @Override
    public Page<MessageDTO> getMessagesByDialog(Long dialogId, Pageable pageable) {
        Dialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new EntityNotFoundException(Dialog.class.getName(), dialogId));

        Page<Message> messagePage = messageRepository.findMessagesByDialog(dialog, pageable);
        Page<MessageDTO> messageDTOPage = messagePage.map(new Function<Message, MessageDTO>() {
            @Override
            public MessageDTO apply(Message message) {
                return new MessageDTO(message.getId(), message.getClient().getId(), message.getDate(),
                        message.getText());
            }
        });

        return messageDTOPage;
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
