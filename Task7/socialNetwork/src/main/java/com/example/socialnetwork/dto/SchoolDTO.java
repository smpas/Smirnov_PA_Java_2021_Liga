package com.example.socialnetwork.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SchoolDTO {
    private Long id;
    private String name;
    private String address;
    private List<Long> clients;

    public SchoolDTO() {
    }

    public SchoolDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public SchoolDTO(Long id, String name, String address, List<Long> clients) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.clients = clients;
    }
}
