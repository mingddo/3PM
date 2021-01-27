package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FollowMeInfo {

    private String email;
    private String imgAddr;
    private String username;

    public FollowMeInfo(User user) {
        this.email = user.getEmail();
        this.imgAddr = user.getImg();
        this.username = user.getNickname();
    }
}
