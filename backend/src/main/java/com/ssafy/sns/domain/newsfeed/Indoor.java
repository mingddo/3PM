package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.Like.IndoorLike;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Indoor extends BaseTimeEntity {

    // 피드 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_id")
    private Long id;

    // 피드 글 내용
    @Lob
    @Column(name = "indoor_content")
    private String content;

    // 피드 첨부 파일
    @Lob
    @Column(name = "indoor_file")
    private String file;

    // 피드 작성자
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "indoor")
    private List<IndoorLike> indoorLikes = new ArrayList<>();
}
