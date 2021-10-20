package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.MessageDTO;
import com.example.socialnetwork.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{dialogId}/messages")
    public Page<MessageDTO> getMessagesByDialog(@PathVariable Long dialogId,
                                                @PageableDefault(sort = {"id"},
                                                        direction = Sort.Direction.ASC) Pageable pageable) {
        return messageService.getMessagesByDialog(dialogId, pageable);
    }

    @PostMapping(value = "/{dialogId}/messages")
    public MessageDTO sendMessage(@PathVariable Long dialogId, @RequestBody MessageDTO message) {
        return messageService.sendMessage(dialogId, message);
    }

    @PutMapping(value = "{dialogId}/messages")
    public MessageDTO editMessage(@PathVariable Long dialogId, @RequestBody MessageDTO message) {
        return messageService.editMessage(dialogId, message);
    }

    @DeleteMapping(value = "messages/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
