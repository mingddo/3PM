package com.ssafy.sns.domain.user;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false) // length 입력 안하면 기본이 255
    private String email;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    @Column(name = "user_bio")
    private String bio;

    @Column(name = "user_img")
    private String img;

    @Embedded
    private UserConfig config;

    @Builder
    public User(String email, String nickname, String bio, String img, UserConfig config) {
        this.email = email;
        this.nickname = nickname;
        this.bio = bio;
        this.img = img;
        this.config = config;
    }
}
