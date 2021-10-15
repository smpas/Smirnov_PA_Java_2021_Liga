package com.example.socialnetwork.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShortClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private String sex;
    private Long school;

    public ShortClientDTO(Long id, String name, String surname, String nickname, String sex) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.sex = sex;
    }
}

