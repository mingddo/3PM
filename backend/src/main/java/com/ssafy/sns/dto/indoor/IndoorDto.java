package com.ssafy.sns.dto.indoor;

import com.ssafy.sns.domain.Like.IndoorLike;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.UserDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class IndoorDto {

    private Long id;
    private String file;
    private User user;
    private List<IndoorLike> indoorLikes;

    @Builder
    public IndoorDto(Indoor indoor) {
        this.id = indoor.getId();
        this.file = indoor.getFile();
        this.user = indoor.getUser();
        this.indoorLikes = indoor.getIndoorLikes();
    }
}
