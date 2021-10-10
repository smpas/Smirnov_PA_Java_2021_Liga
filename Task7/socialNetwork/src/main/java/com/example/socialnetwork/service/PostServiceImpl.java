package com.example.socialnetwork.service;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Post;
import com.example.socialnetwork.repository.ClientRepository;
import com.example.socialnetwork.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Post> getPostsByClientId(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) return null;

        List<Post> posts = postRepository.findPostsByClientOrderByDateDesc(client.get());
        if (!posts.isEmpty()) {
            return posts;
        } else {
            return null;
        }
    }

    @Override
    public Post writeNewPost(Long clientId, Post post) {
        Optional<Client> client = clientRepository.findById(clientId);
        Timestamp date = new Timestamp(System.currentTimeMillis());

        if (client.isPresent()) {
            post.setClient(client.get());
            post.setDate(date);
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    @Override
    public Post editPost(Long clientId, Post post) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            return null;
        }

        Optional<Post> editingPost = postRepository.findById(post.getId());

        if (editingPost.isPresent()) {
            Timestamp date = editingPost.get().getDate();
            post.setClient(client.get());
            post.setDate(date);
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    @Override
    public Post deletePost(Long postId) {
        Optional<Post> deletingPost = postRepository.findById(postId);

        if (deletingPost.isPresent()) {
            postRepository.delete(deletingPost.get());
            return deletingPost.get();
        } else {
            return null;
        }
    }
}
