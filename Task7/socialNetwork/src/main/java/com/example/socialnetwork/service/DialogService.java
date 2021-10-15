package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.DialogDTO;
import com.example.socialnetwork.entity.Dialog;

import java.util.List;

public interface DialogService {
    List<DialogDTO> getDialogsByClientId(Long clientId);

    DialogDTO createDialog(DialogDTO dto);

    void deleteDialog(Long id);

    DialogDTO addUserToDialog(Long dialogId, Long clientId);
}
