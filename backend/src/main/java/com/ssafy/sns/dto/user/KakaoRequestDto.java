package com.ssafy.sns.dto.user;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoRequestDto {

    private Long kakaoId;
    private String username;
    private String profileImg;
}
