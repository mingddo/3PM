package com.ssafy.sns.domain.Like;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.nested.IndoorNestedComment;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IndoorNestedCommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_nested_comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoor_nested_comment_id")
    private IndoorNestedComment indoorNestedComment;
}
