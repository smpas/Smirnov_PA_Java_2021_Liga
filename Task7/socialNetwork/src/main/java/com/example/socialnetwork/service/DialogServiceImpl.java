package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.DialogDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class DialogServiceImpl implements DialogService {
    private final DialogRepository dialogRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DialogServiceImpl(DialogRepository dialogRepository, ClientRepository clientRepository) {
        this.dialogRepository = dialogRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<DialogDTO> getDialogsByClientId(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        List<DialogDTO> DTOs = new LinkedList<>();

        if (client.isPresent()) {
            List<Dialog> dialogs = dialogRepository.findDialogsByClientsContains(client.get());
            for (Dialog dialog : dialogs) {
                DTOs.add(convertDialogToDTO(dialog));
            }
        } else {
            throw new EntityNotFoundException("Client", clientId);
        }

        return DTOs;
    }

    @Override
    public DialogDTO createDialog(DialogDTO dto) {
        List<Client> clients = new LinkedList<>();
        Optional<Client> client;

        for (Long clientId : dto.getClients()) {
            client = clientRepository.findById(clientId);

            if (client.isPresent()) {
                clients.add(client.get());
            } else {
                throw new EntityNotFoundException("Client", clientId);
            }
        }

        Dialog newDialog = dialogRepository.save(new Dialog(dto.getName(), clients));
        return convertDialogToDTO(newDialog);
    }

    @Override
    public void deleteDialog(Long id) {
        Optional<Dialog> deletingDialog = dialogRepository.findById(id);

        if (deletingDialog.isPresent()) {
            dialogRepository.delete(deletingDialog.get());
        } else {
            throw new EntityNotFoundException("Dialog", id);
        }
    }

    @Override
    public DialogDTO addUserToDialog(Long dialogId, Long clientId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        Optional<Client> client = clientRepository.findById(clientId);
        if (dialog.isEmpty()) {
            throw new EntityNotFoundException("Dialog", dialogId);
        }
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client", clientId);
        }

        Dialog editingDialog = dialog.get();
        Client newClient = client.get();
        List<Client> clients = editingDialog.getClients();

        clients.add(newClient);
        editingDialog.setClients(clients);
        return convertDialogToDTO(dialogRepository.save(editingDialog));
    }

    private DialogDTO convertDialogToDTO(Dialog dialog) {
        List<Client> clients = dialog.getClients();
        List<Long> clientsID = new LinkedList<>();
        for (Client client : clients) {
            clientsID.add(client.getId());
        }
        return new DialogDTO(dialog.getId(), dialog.getName(), clientsID);
    }
}
