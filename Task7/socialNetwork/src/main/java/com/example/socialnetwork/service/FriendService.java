package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;

import java.util.List;

public interface FriendService {
    List<Client> getUserFriends(Long userId);

    Client deleteFriend(Long firstClientId, Long secondClientId);

    Client addFriend(Long firstClient, Long secondClient);
}
