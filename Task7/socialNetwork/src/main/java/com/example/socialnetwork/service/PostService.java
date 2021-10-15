package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.Post;

import java.util.List;

public interface PostService {
    List<PostDTO> getPostsByClientId(Long clientId);

    PostDTO writeNewPost(PostDTO post);

    PostDTO editPost(PostDTO post);

    void deletePost(Long postId);
}
