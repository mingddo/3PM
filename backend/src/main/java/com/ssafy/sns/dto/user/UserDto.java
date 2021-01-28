package com.ssafy.sns.dto.user;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.domain.user.UserConfig;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String nickname;
    private String introduce;
    private String img;
    private UserConfig userConfig;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.img = user.getImg();
        this.userConfig = user.getUserConfig();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .introduce(introduce)
                .img(img)
                .userConfig(userConfig)
                .build();
    }
}
