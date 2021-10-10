package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getPostsByClientId(Long clientId);

    Post writeNewPost(Long clientId, Post post);

    Post editPost(Long clientId, Post post);

    Post deletePost(Long postId);
}
