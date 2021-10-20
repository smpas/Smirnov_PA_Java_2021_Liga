package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.MessageDTO;
import com.example.socialnetwork.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dialogs")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{dialogId}/messages")
    public ResponseEntity<List<MessageDTO>> getMessagesByDialog(@PathVariable Long dialogId) {
        return new ResponseEntity<>(messageService.getMessagesByDialog(dialogId), HttpStatus.OK);
    }

    @PostMapping(value = "/{dialogId}/messages")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long dialogId, @RequestBody MessageDTO message) {
        return new ResponseEntity<>(messageService.sendMessage(dialogId, message), HttpStatus.OK);
    }

    @PutMapping(value = "{dialogId}/messages")
    public ResponseEntity<MessageDTO> editMessage(@PathVariable Long dialogId, @RequestBody MessageDTO message) {
        return new ResponseEntity<>(messageService.editMessage(dialogId, message), HttpStatus.OK);
    }

    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
