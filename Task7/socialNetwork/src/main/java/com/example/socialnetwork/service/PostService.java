package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDTO> getPostsByClientId(Long clientId, Pageable pageable);

    PostDTO writeNewPost(PostDTO post);

    PostDTO editPost(PostDTO post);

    void deletePost(Long postId);
}
