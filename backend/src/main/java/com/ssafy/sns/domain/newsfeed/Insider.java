package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Insider extends Feed {

    // 기능 추가 예정 ?

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
