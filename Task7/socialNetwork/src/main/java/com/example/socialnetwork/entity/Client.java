package com.example.socialnetwork.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "surname", length = 30, nullable = false)
    private String surname;

    @Column(name = "nickname", length = 30, unique = true)
    private String nickname;

    @Column(name = "sex", length = 1)
    private String sex;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "client")
    private List<Post> posts;

    @OneToMany(mappedBy = "first_client")
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

    public Client(String name, String surname, String nickname, String sex, School school) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.sex = sex;
        this.school = school;
    }
}
