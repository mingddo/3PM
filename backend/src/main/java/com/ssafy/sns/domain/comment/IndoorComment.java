package com.ssafy.sns.domain.comment;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자
@Entity
public class IndoorComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoor_id")
    private Indoor indoor;

    @Column(name = "indoor_comment_content", nullable = false)
    private String content;
}
