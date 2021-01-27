package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.domain.user.UserConfig;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SubscribeUserDto {

    private UserConfig profile;
    private String username;
    private String user_id; // email

    public SubscribeUserDto(User user) {
        this.profile = user.getUserConfig();
        this.username = user.getNickname();
        this.user_id = user.getEmail();
    }
}
