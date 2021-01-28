package com.ssafy.sns.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimpleUserDto {

    private Long id;
    private String nickname;
    private String img;

    @Builder
    public SimpleUserDto(Long id, String nickname, String img) {
        this.id = id;
        this.nickname = nickname;
        this.img = img;
    }
}