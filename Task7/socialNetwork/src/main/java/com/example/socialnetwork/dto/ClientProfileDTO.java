package com.example.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private String sex;
    private Long school;
    private List<Long> friends;
    private List<PostDTO> posts;

    public ClientProfileDTO(Long id, String name, String surname, String nickname, String sex, List<Long> friends, List<PostDTO> posts) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.sex = sex;
        this.friends = friends;
        this.posts = posts;
    }
}
