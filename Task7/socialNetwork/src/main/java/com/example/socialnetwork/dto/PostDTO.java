package com.example.socialnetwork.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Long client;
    private Timestamp date;
    private String header;
    private String text;

    public PostDTO(Long id, Long client, Timestamp date, String header, String text) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.header = header;
        this.text = text;
    }
}
