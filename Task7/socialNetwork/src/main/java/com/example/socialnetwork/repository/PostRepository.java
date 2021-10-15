package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findPostsByClientOrderByDateDesc(Client client);
}
