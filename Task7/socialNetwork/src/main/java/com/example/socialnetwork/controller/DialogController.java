package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.DialogDTO;
import com.example.socialnetwork.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {
    private final DialogService dialogService;

    @GetMapping(value = "/list/{userId}")
    public Page<DialogDTO> getDialogsByClientId(@PathVariable Long userId,
                                                @PageableDefault(sort = {"id"},
                                                        direction = Sort.Direction.ASC) Pageable pageable) {
        return dialogService.getDialogsByClientId(userId, pageable);
    }

    @PostMapping()
    public DialogDTO createDialog(@RequestBody DialogDTO dialog) {
        return dialogService.createDialog(dialog);
    }

    @DeleteMapping("/{id}")
    public void deleteDialog(@PathVariable Long id) {
        dialogService.deleteDialog(id);
    }

    @PutMapping(value = "/{dialog_id}/new-user/{userId}")
    public DialogDTO addUserToDialog(@PathVariable Long dialog_id, @PathVariable Long userId) {
        return dialogService.addUserToDialog(dialog_id, userId);
    }
}
