package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ShortClientDTO;

import java.util.List;

public interface FriendService {
    List<ShortClientDTO> getUserFriends(Long userId);

    void deleteFriend(Long firstClientId, Long secondClientId);

    ShortClientDTO addFriend(Long firstClient, Long secondClient);
}
