package com.ssafy.sns.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class UserConfig {

    private String test;
    private String test2;
    // 설정들 넣기
}
