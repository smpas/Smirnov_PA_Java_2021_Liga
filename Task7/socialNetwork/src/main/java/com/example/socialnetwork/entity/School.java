package com.example.socialnetwork.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "address", length = 50)
    private String address;

    @OneToMany(mappedBy = "school")
    private List<Client> clients;

    public School() {
    }

    public School(String name, String address) {
        this.name = name;
        this.address = address;
        this.clients = new LinkedList<>();
    }

    public School(String name, String address, List<Client> clients) {
        this.name = name;
        this.address = address;
        this.clients = clients;
    }
}
