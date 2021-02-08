package com.ssafy.sns.dto.user;


import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserByFollowDto {

    Long id;
    String nickname;
    String profileImg;
    Boolean isFollow; // 내가 toUser 를 팔로우 했는지 여부

    public UserByFollowDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImg = user.getImg();
        this.isFollow = false;
    }

    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}
