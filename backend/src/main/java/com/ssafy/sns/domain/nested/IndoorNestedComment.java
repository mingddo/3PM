package com.ssafy.sns.domain.nested;

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
public class IndoorNestedComment {

    // 대댓글 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_nested_comment_id")
    private Long id;

    // 대댓글 작성자
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 댓글
    @ManyToOne
    @JoinColumn(name = "indoor_comment_id")
    private IndoorComment indoorComment;

    // 대댓글 내용
    @Column(name = "indoor_nested_comment_content", nullable = false)
    private String content;
}
