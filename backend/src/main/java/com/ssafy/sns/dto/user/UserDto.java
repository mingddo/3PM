package com.ssafy.sns.dto.user;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.domain.user.UserConfig;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String nickname;
    private String bio;
    private String img;
    private UserConfig userConfig;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.img = user.getImg();
        this.userConfig = user.getUserConfig();
    }
}
