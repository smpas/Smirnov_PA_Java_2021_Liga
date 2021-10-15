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

    @GetMapping(value = "/{user_id}/friends")
    public ResponseEntity<List<ShortClientDTO>> getUserFriends(@PathVariable Long user_id) {
        return new ResponseEntity<>(friendService.getUserFriends(user_id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{user_1}/friends/{user_2}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long user_1, @PathVariable Long user_2) {
        friendService.deleteFriend(user_1, user_2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{user_1}/friends/{user_2}")
    public ResponseEntity<ShortClientDTO> addUserToFriends(@PathVariable Long user_1, @PathVariable Long user_2) {
        return new ResponseEntity<>(friendService.addFriend(user_1, user_2), HttpStatus.OK);
    }
}
