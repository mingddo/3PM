package com.ssafy.sns.domain.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class NoticeComment extends Notice{

    @Column(name = "comment_id")
    private Long comment_id;

    @Builder
    public NoticeComment(Long comment_id) {
        this.comment_id = comment_id;
    }
}
