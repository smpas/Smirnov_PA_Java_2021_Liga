package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.DialogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DialogService {
    Page<DialogDTO> getDialogsByClientId(Long clientId, Pageable pageable);

    DialogDTO createDialog(DialogDTO dto);

    void deleteDialog(Long id);

    DialogDTO addUserToDialog(Long dialogId, Long clientId);
}
