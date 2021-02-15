package com.ssafy.sns.domain.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class NoticeFollow extends Notice{

    @Column(name = "follow_id")
    private Long follow_id;

    @Builder
    public NoticeFollow(Long follow_id) {
        this.follow_id = follow_id;
    }
}
