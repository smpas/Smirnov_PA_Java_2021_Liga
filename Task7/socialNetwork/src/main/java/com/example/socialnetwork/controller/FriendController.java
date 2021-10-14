package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping(value = "/friends", params = "user")
    public ResponseEntity<List<Client>> getUserFriends(@RequestParam Long user) {
        List<Client> friends = friendService.getUserFriends(user);

        if (friends != null) {
            return new ResponseEntity<>(friends, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/friends", params = {"user1", "user2"})
    public ResponseEntity<?> deleteFriend(@RequestParam Long user1, @RequestParam Long user2) {
        Client client = friendService.deleteFriend(user1, user2);

        if (client != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/friends", params = {"user1", "user2"})
    public ResponseEntity<Client> addUserToFriends(@RequestParam Long user1, @RequestParam Long user2) {
        Client newFriend = friendService.addFriend(user1, user2);

        if (newFriend != null) {
            return new ResponseEntity<>(newFriend, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
