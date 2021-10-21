package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Page<Post> findPostsByClientOrderByDateDesc(Client client, Pageable pageable);
}
