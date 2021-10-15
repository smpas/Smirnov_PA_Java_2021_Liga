package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Client;
import com.example.socialnetwork.entity.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findFriendsByFirstClient(Client client);
    void deleteFriendByFirstClientAndSecondClient(Client firstClient, Client secondClient);
}
