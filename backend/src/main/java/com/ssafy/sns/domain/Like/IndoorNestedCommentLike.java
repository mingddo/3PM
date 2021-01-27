package com.ssafy.sns.domain.Like;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.comment.IndoorComment;
import com.ssafy.sns.domain.nested.IndoorNestedComment;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class IndoorNestedCommentLike extends BaseTimeEntity {

    // 대댓글 좋아요 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_nested_comment_like_id")
    private Long id;

    // 유저 정보
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 대댓글 정보
    @ManyToOne
    @JoinColumn(name = "indoor_nested_comment_id")
    private IndoorNestedComment indoorNestedComment;
}
