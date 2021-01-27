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

    private String username;
    private String user_id;
    private String imgAddr;
    private String introduce;

    public ProfileDto(User user) {
        this.username = user.getNickname();
        this.user_id = user.getEmail();
        this.imgAddr = user.getImg();
        this.introduce = user.getBio();
    }
}
