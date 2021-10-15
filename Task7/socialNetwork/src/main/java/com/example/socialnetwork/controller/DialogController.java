package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.DialogDTO;
import com.example.socialnetwork.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dialogs")
public class DialogController {
    private final DialogService dialogService;

    @Autowired
    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @GetMapping(value = "/list/{user_id}")
    public ResponseEntity<List<DialogDTO>> getDialogsByClientId(@PathVariable Long user_id) {
        return new ResponseEntity<>(dialogService.getDialogsByClientId(user_id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DialogDTO> createDialog(@RequestBody DialogDTO dialog) {
        return new ResponseEntity<>(dialogService.createDialog(dialog), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDialog(@PathVariable Long id) {
        dialogService.deleteDialog(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{dialog_id}/new-user/{user_id}")
    public ResponseEntity<DialogDTO> addUserToDialog(@PathVariable Long dialog_id, @PathVariable Long user_id) {
        return new ResponseEntity<>(dialogService.addUserToDialog(dialog_id, user_id), HttpStatus.OK);
    }
}
