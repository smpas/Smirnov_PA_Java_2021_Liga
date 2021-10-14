package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
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
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getUserFriends(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            return null;
        }

        List<Friend> friends = friendRepository.findFriendsByFirstClient(client.get());
        if (!friends.isEmpty()) {
            List<Client> clients = new LinkedList<>();
            for (Friend friend : friends) {
                clients.add(friend.getSecondClient());
            }
            return clients;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Client deleteFriend(Long firstClientId, Long secondClientId) {
        Optional<Client> firstClient = clientRepository.findById(firstClientId);
        Optional<Client> secondClient = clientRepository.findById(secondClientId);

        if (firstClient.isPresent() && secondClient.isPresent()) {
            friendRepository.deleteFriendByFirstClientAndSecondClient(firstClient.get(), secondClient.get());
            friendRepository.deleteFriendByFirstClientAndSecondClient(secondClient.get(), firstClient.get());
            return secondClient.get();
        } else {
            return null;
        }
    }

    @Override
    public Client addFriend(Long firstClient, Long secondClient) {
        Optional<Client> client1 = clientRepository.findById(firstClient);
        Optional<Client> client2 = clientRepository.findById(secondClient);

        if (client1.isPresent() && client2.isPresent()) {
            friendRepository.save(new Friend(client2.get(), client1.get()));
            friendRepository.save(new Friend(client1.get(), client2.get()));
            return client2.get();
        } else {
            return null;
        }
    }
}
