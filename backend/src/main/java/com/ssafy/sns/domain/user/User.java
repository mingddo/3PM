package com.ssafy.sns.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter // 비추천 코드
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User {

    // 유저 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 카카오에서 주는 ID
    @Column(name = "kakao_id", nullable = false, unique = true) // length 입력 안하면 기본이 255
    private Long kakaoId;

    // 유저 닉네임
    @Column(name = "user_nickname")
    private String nickname;

    // 유저 소개
    @Column(name = "user_introduce")
    private String introduce;

    // 유저 이미지
    @Column(name = "user_img")
    private String img;

    // 유저 설정 정보
    @Embedded
    private UserConfig userConfig;
}
