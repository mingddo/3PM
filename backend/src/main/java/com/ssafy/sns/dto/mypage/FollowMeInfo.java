package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class FollowMeInfo {

    private Long user_id;
    private String profilesrc;
    private String username;

    public FollowMeInfo(User user) {
        this.user_id = user.getId();
        this.profilesrc = user.getImg();
        this.username = user.getNickname();
    }
}
