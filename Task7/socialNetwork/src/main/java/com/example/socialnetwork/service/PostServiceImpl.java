package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<PostDTO> getPostsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), clientId));

        List<Post> posts = postRepository.findPostsByClientOrderByDateDesc(client);
        List<PostDTO> DTOs = new LinkedList<>();
        for (Post post : posts) {
            DTOs.add(new PostDTO(post.getId(), post.getClient().getId(), post.getDate(), post.getHeader(),
                    post.getText()));
        }

        return DTOs;
    }

    @Override
    @Transactional
    public PostDTO writeNewPost(PostDTO dto) {
        Client client = clientRepository.findById(dto.getClient())
                .orElseThrow(() -> new EntityNotFoundException(Client.class.getName(), dto.getClient()));

        Post newPost = new Post(dto.getHeader(), dto.getText());
        newPost.setDate(new Timestamp(System.currentTimeMillis()));
        newPost.setClient(client);
        Post addedPost = postRepository.save(newPost);

        return new PostDTO(addedPost.getId(), addedPost.getClient().getId(), addedPost.getDate(),
                addedPost.getHeader(), addedPost.getText());
    }

    @Override
    @Transactional
    public PostDTO editPost(PostDTO dto) {
        Post editingPost = postRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(Post.class.getName(), dto.getId()));

        editingPost.setHeader(dto.getHeader());
        editingPost.setText(dto.getText());
        Post post = postRepository.save(editingPost);
        return new PostDTO(post.getId(), post.getClient().getId(), post.getDate(), post.getHeader(), post.getText());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post deletingPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(Post.class.getName(), postId));

        postRepository.delete(deletingPost);
    }
}
