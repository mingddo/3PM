package com.ssafy.sns.domain.notice;

import com.ssafy.sns.domain.follow.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class NoticeFollow extends Notice{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_id")
    private Follow follow;

    @Builder
    public NoticeFollow(Follow follow) {
        this.follow = follow;
    }
}
