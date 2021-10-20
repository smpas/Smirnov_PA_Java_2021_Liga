package com.example.socialnetwork.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "sex")
    private String sex;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "client")
    private List<Post> posts;

    @OneToMany(mappedBy = "firstClient")
    private List<Friend> friends;

    @ManyToMany
    @JoinTable(
            name = "client_dialog",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "dialog_id")
    )
    private List<Dialog> dialogs;

    public Client() {
    }

    public Client(String name, String surname, String nickname, String sex) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.sex = sex;
    }

    public Client(String name, String surname, String nickname, String sex, School school) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.sex = sex;
        this.school = school;
    }
}
