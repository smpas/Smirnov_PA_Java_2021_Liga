package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.ShortClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendService {
    Page<ShortClientDTO> getUserFriends(Long userId, Pageable pageable);

    void deleteFriend(Long firstClientId, Long secondClientId);

    ShortClientDTO addFriend(Long firstClient, Long secondClient);
}
