package com.example.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private Long client;
    private Timestamp date;
    private String text;
}
