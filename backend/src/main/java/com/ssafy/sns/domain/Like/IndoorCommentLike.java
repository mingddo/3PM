package com.ssafy.sns.domain.Like;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.comment.IndoorComment;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IndoorCommentLike extends BaseTimeEntity {

    // 댓글 좋아요 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_comment_like_id")
    private Long id;

    // 유저 정보
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글 정보
    @ManyToOne
    @JoinColumn(name = "indoor_comment_id")
    private IndoorComment indoorComment;
}
