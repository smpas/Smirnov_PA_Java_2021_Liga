package com.example.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class DialogDTO {
    private Long id;
    private String name;
    private List<Long> clients;
}
