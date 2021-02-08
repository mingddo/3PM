package com.ssafy.sns.dto.user;

import com.ssafy.sns.domain.user.User;
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
    public SimpleUserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.img = user.getImg();
    }
}
