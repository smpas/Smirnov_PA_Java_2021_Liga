package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{user_id}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsOfClient(@PathVariable Long user_id) {
        return new ResponseEntity<>(postService.getPostsByClientId(user_id), HttpStatus.OK);
    }

    @PostMapping(value = "/posts")
    public ResponseEntity<PostDTO> writeNewPost(@RequestBody PostDTO post) {
        return new ResponseEntity<>(postService.writeNewPost(post), HttpStatus.OK);
    }

    @PutMapping(value = "/posts")
    public ResponseEntity<PostDTO> editPost(@RequestBody PostDTO post) {
        return new ResponseEntity<>(postService.editPost(post), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
