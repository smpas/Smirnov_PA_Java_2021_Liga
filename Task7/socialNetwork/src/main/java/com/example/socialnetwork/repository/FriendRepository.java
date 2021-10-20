package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
    Page<Friend> findFriendsByFirstClient(Client client, Pageable pageable);

    void deleteFriendByFirstClientAndSecondClient(Client firstClient, Client secondClient);
}
