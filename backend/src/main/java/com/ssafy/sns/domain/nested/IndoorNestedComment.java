package com.ssafy.sns.domain.nested;

import com.ssafy.sns.domain.comment.IndoorComment;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IndoorNestedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_nested_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoor_comment_id")
    private IndoorComment indoorComment;

    @Column(name = "indoor_nested_comment_content", nullable = false)
    private String content;
}
