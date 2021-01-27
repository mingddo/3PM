package com.ssafy.sns.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter // 비추천 코드
@NoArgsConstructor // 기본 생성자
@Entity
public class UserConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private Long id;

    // 설정들 넣기
}
