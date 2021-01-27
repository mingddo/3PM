package com.ssafy.sns.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter // 비추천 코드
@NoArgsConstructor // 기본 생성자
@Entity
public class User {

    // 유저 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 유저 email
    @Column(name = "user_email", nullable = false, unique = true) // length 입력 안하면 기본이 255
    private String email;

    // 유저 닉네임
    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    // 유저 이름 ( 추가 )
    @Column(name = "user_name", nullable = false)
    private String name;

    // 유저 소개
    @Column(name = "user_bio")
    private String bio;

    // 유저 이미지
    @Column(name = "user_img")
    private String img;

    // 유저 설정 정보
    @OneToOne
    @JoinColumn(name = "config_id")
    private UserConfig userConfig;

    @Builder
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
