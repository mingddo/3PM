package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Insider extends Feed {

    // 기능 추가 예정 ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Insider(FeedRequestDto feedRequestDto, User user, Group group) {
        super(feedRequestDto.getContent(), user);
        this.group = group;
    }
}
