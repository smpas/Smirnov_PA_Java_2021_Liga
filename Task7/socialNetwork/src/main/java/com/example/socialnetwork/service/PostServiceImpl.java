package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.PostDTO;
import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.exception.EntityNotFoundException;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ClientRepository clientRepository) {
        this.postRepository = postRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<PostDTO> getPostsByClientId(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client", clientId);
        }

        List<Post> posts = postRepository.findPostsByClientOrderByDateDesc(client.get());
        List<PostDTO> DTOs = new LinkedList<>();
        for (Post post : posts) {
            DTOs.add(new PostDTO(post.getId(), post.getClient().getId(), post.getDate(), post.getHeader(),
                    post.getText()));
        }

        return DTOs;
    }

    @Override
    public PostDTO writeNewPost(PostDTO dto) {
        Optional<Client> client = clientRepository.findById(dto.getClient());

        if (client.isPresent()) {
            Post newPost = new Post(dto.getHeader(), dto.getText());
            newPost.setDate(new Timestamp(System.currentTimeMillis()));
            newPost.setClient(client.get());
            Post addedPost = postRepository.save(newPost);
            return new PostDTO(addedPost.getId(), addedPost.getClient().getId(), addedPost.getDate(),
                    addedPost.getHeader(), addedPost.getText());
        } else {
            throw new EntityNotFoundException("Client", dto.getClient());
        }
    }

    @Override
    public PostDTO editPost(PostDTO dto) {
        Optional<Post> editingPost = postRepository.findById(dto.getId());

        if (editingPost.isPresent()) {
            Post editedPost = editingPost.get();
            editedPost.setHeader(dto.getHeader());
            editedPost.setText(dto.getText());
            Post post = postRepository.save(editedPost);
            return new PostDTO(post.getId(), post.getClient().getId(), post.getDate(), post.getHeader(), post.getText());
        } else {
            throw new EntityNotFoundException("Post", dto.getId());
        }
    }

    @Override
    public void deletePost(Long postId) {
        Optional<Post> deletingPost = postRepository.findById(postId);

        if (deletingPost.isPresent()) {
            postRepository.delete(deletingPost.get());
        } else {
            throw new EntityNotFoundException("Post", postId);
        }
    }
}
