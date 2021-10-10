package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DialogController {
    @Autowired
    private DialogService dialogService;

    @GetMapping(value = "/dialogs", params = "userId")
    public ResponseEntity<List<Dialog>> getDialogsByClientId(@RequestParam Long userId) {
        List<Dialog> dialogs = dialogService.getDialogsByClientId(userId);

        if (dialogs != null) {
            return new ResponseEntity<>(dialogs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/dialogs", params = "usersId")
    public ResponseEntity<Dialog> createDialog(@RequestParam String name, @RequestParam Long[] usersId) {
        Dialog createdDialog = dialogService.createDialog(name, usersId);

        if (createdDialog != null) {
            return new ResponseEntity<>(createdDialog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/dialogs/{id}")
    public ResponseEntity<?> deleteDialog(@PathVariable Long id) {
        Dialog deletedDialog = dialogService.deleteDialog(id);

        if (deletedDialog != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/dialogs/addUser", params = {"dialogId", "userId"})
    public ResponseEntity<Dialog> addUserToDialog(@RequestParam Long dialogId, @RequestParam Long userId) {
        Dialog dialog = dialogService.addUserToDialog(dialogId, userId);

        if (dialog != null) {
            return new ResponseEntity<>(dialog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
