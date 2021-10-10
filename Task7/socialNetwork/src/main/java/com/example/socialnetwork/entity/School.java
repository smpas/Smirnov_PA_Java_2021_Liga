package com.example.socialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class School {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", length = 30, nullable = false)
    private String name;

    @Column(name="address", length = 50)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "school")
    private List<Client> clients;

    public School() {
    }

    public School(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
