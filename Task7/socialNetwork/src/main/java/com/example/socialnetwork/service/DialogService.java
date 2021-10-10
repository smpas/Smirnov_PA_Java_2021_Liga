package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Dialog;

import java.util.List;

public interface DialogService {
    List<Dialog> getDialogsByClientId(Long clientId);

    Dialog createDialog(String name, Long[] usersId);

    Dialog deleteDialog(Long id);

    Dialog addUserToDialog(Long dialogId, Long clientId);
}
