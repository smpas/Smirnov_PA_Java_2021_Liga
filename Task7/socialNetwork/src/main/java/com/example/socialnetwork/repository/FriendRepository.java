package com.example.socialnetwork.repository;

import com.example.socialnetwork.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
