package com.ssafy.sns.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Long id;
    private String nickname;
    private String img;

    @Builder
    public UserDto(Long id, String nickname, String img) {
        this.id = id;
        this.nickname = nickname;
        this.img = img;
    }
}
