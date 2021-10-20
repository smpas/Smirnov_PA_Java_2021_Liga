package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{userId}/posts")
    public Page<PostDTO> getAllPostsOfClient(@PathVariable Long userId, @PageableDefault Pageable pageable) {
        return postService.getPostsByClientId(userId, pageable);
    }

    @PostMapping(value = "/posts")
    public PostDTO writeNewPost(@RequestBody PostDTO post) {
        return postService.writeNewPost(post);
    }

    @PutMapping(value = "/posts")
    public PostDTO editPost(@RequestBody PostDTO post) {
        return postService.editPost(post);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
