package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository, ClientRepository clientRepository) {
        this.friendRepository = friendRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ShortClientDTO> getUserFriends(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client", clientId);
        }

        List<Friend> friends = friendRepository.findFriendsByFirstClient(client.get());
        List<ShortClientDTO> clients = new LinkedList<>();
        for (Friend friend : friends) {
            clients.add(convertClientToDTO(friend.getSecondClient()));
        }

        return clients;
    }

    @Override
    @Transactional
    public void deleteFriend(Long firstClientId, Long secondClientId) {
        Optional<Client> firstClient = clientRepository.findById(firstClientId);
        Optional<Client> secondClient = clientRepository.findById(secondClientId);

        if (firstClient.isEmpty()) {
            throw new EntityNotFoundException("Client", firstClientId);
        }
        if (secondClient.isEmpty()) {
            throw new EntityNotFoundException("Client", secondClientId);
        }

        friendRepository.deleteFriendByFirstClientAndSecondClient(firstClient.get(), secondClient.get());
        friendRepository.deleteFriendByFirstClientAndSecondClient(secondClient.get(), firstClient.get());
    }

    @Override
    @Transactional
    public ShortClientDTO addFriend(Long firstClient, Long secondClient) {
        Optional<Client> client1 = clientRepository.findById(firstClient);
        Optional<Client> client2 = clientRepository.findById(secondClient);

        if (client1.isEmpty()) {
            throw new EntityNotFoundException("Client", firstClient);
        }
        if (client2.isEmpty()) {
            throw new EntityNotFoundException("Client", secondClient);
        }

        friendRepository.save(new Friend(client2.get(), client1.get()));
        friendRepository.save(new Friend(client1.get(), client2.get()));
        return convertClientToDTO(client2.get());
    }

    private ShortClientDTO convertClientToDTO(Client client) {
        ShortClientDTO newDTO = new ShortClientDTO(client.getId(), client.getName(), client.getSurname(),
                client.getNickname(), client.getSex());
        if (client.getSchool() != null) {
            newDTO.setSchool(client.getSchool().getId());
        }
        return newDTO;
    }
}
