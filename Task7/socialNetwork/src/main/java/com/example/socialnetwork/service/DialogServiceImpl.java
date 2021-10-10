package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class DialogServiceImpl implements DialogService {
    @Autowired
    private DialogRepository dialogRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Dialog> getDialogsByClientId(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isPresent()) {
            List<Dialog> dialogs = dialogRepository.findDialogsByClientsContains(client.get());
            if (!dialogs.isEmpty()) {
                return dialogs;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Dialog createDialog(String name, Long[] usersId) {
        List<Client> clients = new LinkedList<>();

        for (Long userId : usersId) {
            Optional<Client> addingClient = clientRepository.findById(userId);
            if (addingClient.isPresent()) {
                clients.add(addingClient.get());
            } else {
                return null;
            }
        }

        Dialog newDialog = new Dialog(name);
        newDialog.setClients(clients);
        return dialogRepository.save(newDialog);
    }

    @Override
    public Dialog deleteDialog(Long id) {
        Optional<Dialog> deletingDialog = dialogRepository.findById(id);

        if (deletingDialog.isPresent()) {
            dialogRepository.delete(deletingDialog.get());
            return deletingDialog.get();
        } else {
            return null;
        }
    }

    @Override
    public Dialog addUserToDialog(Long dialogId, Long clientId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        Optional<Client> client = clientRepository.findById(clientId);

        if (dialog.isPresent() && client.isPresent()) {
            Dialog editingDialog = dialog.get();
            Client newClient = client.get();
            List<Client> clients = editingDialog.getClients();

            clients.add(newClient);
            editingDialog.setClients(clients);
            return dialogRepository.save(editingDialog);
        } else {
            return null;
        }
    }
}
