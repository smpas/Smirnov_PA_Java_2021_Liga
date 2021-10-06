package com.example.socialnetwork.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(Friend.FriendKey.class)
public class Friend {

    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "first_client_id")
    private Client first_client;

    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "second_client_id")
    private Client second_client;

    @EqualsAndHashCode
    @Getter
    @Setter
    public static class FriendKey implements Serializable {
        public Long first_client;
        public Long second_client;

        public FriendKey() {
        }

        public FriendKey(Long first_client, Long second_client) {
            this.first_client = first_client;
            this.second_client = second_client;
        }
    }

    public Friend() {
    }

    public Friend(Client first_client, Client second_client) {
        this.first_client = first_client;
        this.second_client = second_client;
    }
}
