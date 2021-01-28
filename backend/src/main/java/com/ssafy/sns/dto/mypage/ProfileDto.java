package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {

    private String nickname;
    private Long user_id;
    private String profilesrc;
    private String introduce;

    public ProfileDto(User user) {
        this.nickname = user.getNickname();
        this.user_id = user.getId();
        this.profilesrc = user.getImg();
        this.introduce = user.getIntroduce();
    }
}
