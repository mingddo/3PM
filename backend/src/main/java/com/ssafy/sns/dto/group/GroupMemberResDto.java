package com.ssafy.sns.dto.group;

import com.ssafy.sns.domain.group.GroupMember;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupMemberResDto {

    private Long id;
    private String nickname;
    private String profileImg;

    public GroupMemberResDto(GroupMember groupMember) {
        User user = groupMember.getUser();
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImg = user.getImg();
    }
}
