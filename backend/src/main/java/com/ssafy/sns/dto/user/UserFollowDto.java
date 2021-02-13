package com.ssafy.sns.dto.user;

import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserFollowDto {

    private Long id;
    private String nickname;
    private String profileImg;
    private boolean isFollow;

    public UserFollowDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        if (user.getImg() == null) {
            profileImg = "20210204162426_img1.jpg";
        } else {
            profileImg = user.getImg();
        }
        this.isFollow = false;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
