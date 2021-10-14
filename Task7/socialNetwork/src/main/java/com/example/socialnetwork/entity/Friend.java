package com.example.socialnetwork.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(Friend.FriendKey.class)
public class Friend {

    @Id
    @ManyToOne
    @JoinColumn(name = "first_client_id")
    private Client firstClient;

    @Id
    @ManyToOne
    @JoinColumn(name = "second_client_id")
    private Client secondClient;

    @EqualsAndHashCode
    @Getter
    @Setter
    public static class FriendKey implements Serializable {
        public Long firstClient;
        public Long secondClient;

        public FriendKey() {
        }

        public FriendKey(Long firstClient, Long secondClient) {
            this.firstClient = firstClient;
            this.secondClient = secondClient;
        }
    }

    public Friend() {
    }

    public Friend(Client firstClient, Client secondClient) {
        this.firstClient = firstClient;
        this.secondClient = secondClient;
    }
}
