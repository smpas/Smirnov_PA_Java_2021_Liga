package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.Message;
import com.example.socialnetwork.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/messages", params = "dialogId")
    public ResponseEntity<List<Message>> getMessagesByDialog(@RequestParam Long dialogId) {
        List<Message> messages = messageService.getMessagesByDialog(dialogId);

        if (messages != null) {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/messages", params = {"dialogId", "userId"})
    public ResponseEntity<Message> sendMessage(@RequestParam Long dialogId,
                                               @RequestParam Long userId,
                                               @RequestBody Message message) {
        Message sentMessage = messageService.sendMessage(dialogId, userId, message);

        if (sentMessage != null) {
            return new ResponseEntity<>(sentMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/messages")
    public ResponseEntity<Message> editMessage(@RequestBody Message message) {
        Message editedMessage = messageService.editMessage(message);

        if (editedMessage != null) {
            return new ResponseEntity<>(editedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        Message message = messageService.deleteMessage(id);

        if (message != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
