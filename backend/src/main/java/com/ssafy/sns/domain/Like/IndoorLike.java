package com.ssafy.sns.domain.Like;

import com.ssafy.sns.domain.BaseTimeEntity;
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
public class IndoorLike extends BaseTimeEntity {

    // 뉴스피드 좋아요 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_like_id")
    private Long id;

    // 유저 정보
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 뉴스피드 정보
    @ManyToOne
    @JoinColumn(name = "indoor_id")
    private Indoor indoor;
}
