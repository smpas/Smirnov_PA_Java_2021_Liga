package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping(value = "/{userId}/friends")
    public Page<ShortClientDTO> getUserFriends(@PathVariable Long userId,
                                               @PageableDefault Pageable pageable) {
        return friendService.getUserFriends(userId, pageable);
    }

    @DeleteMapping(value = "/{user1}/friends/{user2}")
    public void deleteFriend(@PathVariable Long user1, @PathVariable Long user2) {
        friendService.deleteFriend(user1, user2);
    }

    @PostMapping(value = "/{user1}/friends/{user2}")
    public ShortClientDTO addUserToFriends(@PathVariable Long user1, @PathVariable Long user2) {
        return friendService.addFriend(user1, user2);
    }
}
