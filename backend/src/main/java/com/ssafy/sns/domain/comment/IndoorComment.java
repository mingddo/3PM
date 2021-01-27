package com.ssafy.sns.domain.comment;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter // 비추천 코드
@NoArgsConstructor // 기본 생성자
@Entity
public class IndoorComment extends BaseTimeEntity {

    // 댓글 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_comment_id")
    private Long id;

    // 댓글 작성자
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 뉴스피드
    @ManyToOne
    @JoinColumn(name = "indoor_id")
    private Indoor indoor;

    // 댓글 내용
    @Column(name = "indoor_comment_content", nullable = false)
    private String content;
}
