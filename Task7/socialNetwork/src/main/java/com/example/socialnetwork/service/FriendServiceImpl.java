package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<ShortClientDTO> getUserFriends(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId));

        List<Friend> friends = friendRepository.findFriendsByFirstClient(client);
        List<ShortClientDTO> clients = new LinkedList<>();
        for (Friend friend : friends) {
            clients.add(convertClientToDTO(friend.getSecondClient()));
        }

        return clients;
    }

    @Override
    @Transactional
    public void deleteFriend(Long firstClientId, Long secondClientId) {
        Client firstClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), firstClientId));
        Client secondClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), secondClientId));

        friendRepository.deleteFriendByFirstClientAndSecondClient(firstClient, secondClient);
        friendRepository.deleteFriendByFirstClientAndSecondClient(secondClient, firstClient);
    }

    @Override
    @Transactional
    public ShortClientDTO addFriend(Long firstClientId, Long secondClientId) {
        Client firstClient = clientRepository.findById(firstClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), firstClientId));
        Client secondClient = clientRepository.findById(secondClientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), secondClientId));

        friendRepository.save(new Friend(firstClient, secondClient));
        friendRepository.save(new Friend(secondClient, firstClient));
        return convertClientToDTO(secondClient);
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
