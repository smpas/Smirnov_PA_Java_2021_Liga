package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.DialogDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Dialog;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {
    private final DialogRepository dialogRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<DialogDTO> getDialogsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId));

        List<DialogDTO> DTOs = new LinkedList<>();
        List<Dialog> dialogs = dialogRepository.findDialogsByClientsContains(client);
        for (Dialog dialog : dialogs) {
            DTOs.add(convertDialogToDTO(dialog));
        }

        return DTOs;
    }

    @Override
    @Transactional
    public DialogDTO createDialog(DialogDTO dto) {
        List<Client> clients = new LinkedList<>();

        for (Long clientId : dto.getClients()) {
            clients.add(clientRepository.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId)));
        }

        Dialog newDialog = dialogRepository.save(new Dialog(dto.getName(), clients));
        return convertDialogToDTO(newDialog);
    }

    @Override
    @Transactional
    public void deleteDialog(Long id) {
        Dialog deletingDialog = dialogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Dialog.class.getName(), id));
        dialogRepository.delete(deletingDialog);
    }

    @Override
    @Transactional
    public DialogDTO addUserToDialog(Long dialogId, Long clientId) {
        Dialog editingDialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> new EntityNotFoundException(Dialog.class.getName(), dialogId));
        Client newClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId));

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
