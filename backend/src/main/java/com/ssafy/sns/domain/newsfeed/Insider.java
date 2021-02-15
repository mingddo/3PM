package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Insider extends Feed {

    // 기능 추가 예정 ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Insider(String content, User user, Group group) {
        super(content, user);
        this.group = group;
    }
}
