package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.ShortClientDTO;
import com.example.socialnetwork.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping(value = "/{userId}/friends")
    public ResponseEntity<List<ShortClientDTO>> getUserFriends(@PathVariable Long userId) {
        return new ResponseEntity<>(friendService.getUserFriends(userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{user1}/friends/{user2}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long user1, @PathVariable Long user2) {
        friendService.deleteFriend(user1, user2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{user1}/friends/{user2}")
    public ResponseEntity<ShortClientDTO> addUserToFriends(@PathVariable Long user1, @PathVariable Long user2) {
        return new ResponseEntity<>(friendService.addFriend(user1, user2), HttpStatus.OK);
    }
}
