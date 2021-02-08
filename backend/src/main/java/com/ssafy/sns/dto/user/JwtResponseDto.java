package com.ssafy.sns.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {

    private String accToken;
    private String refToken;
    private Long id;

    // 유저 정보 추가 예정
}
