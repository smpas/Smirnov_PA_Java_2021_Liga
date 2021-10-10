package com.example.socialnetwork.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "header")
    private String header;

    @Column(name = "text")
    private String text;

    public Post() {
    }

    public Post(String header, String text) {
        this.header = header;
        this.text = text;
    }
}
