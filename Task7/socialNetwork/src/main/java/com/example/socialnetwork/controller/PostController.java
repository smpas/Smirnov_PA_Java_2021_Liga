package com.example.socialnetwork.controller;

import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/posts", params = "userId")
    public ResponseEntity<List<Post>> getAllPostsOfClient(@RequestParam Long userId) {
        List<Post> posts = postService.getPostsByClientId(userId);

        if (posts != null) {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/posts", params = "userId")
    public ResponseEntity<Post> writeNewPost(@RequestParam Long userId, @RequestBody Post post) {
        Post addedPost = postService.writeNewPost(userId, post);

        if (addedPost != null) {
            return new ResponseEntity<>(addedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/posts", params = "userId")
    public ResponseEntity<Post> editPost(@RequestParam Long userId, @RequestBody Post post) {
        Post editedPost = postService.editPost(userId, post);

        if (editedPost != null) {
            return new ResponseEntity<>(editedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Post deletedPost = postService.deletePost(id);

        if (deletedPost != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
