package com.example.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "text")
    private String text;

    public Message() {
    }

    public Message(Client client, Dialog dialog, Timestamp date, String text) {
        this.client = client;
        this.dialog = dialog;
        this.date = date;
        this.text = text;
    }
}
